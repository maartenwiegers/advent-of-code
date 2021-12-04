package nl.maartenwiegers.aoc2021;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import nl.maartenwiegers.aoc2021.commons.FileService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
public class Day4 {

    private static final String BINGO_CARDS_FILE_NAME = "input/04-bingocards.txt";
    private static final String DRAW_NUMBERS_FILE_NAME = "input/04-drawnumbers.txt";
    private final int cardsWon = 0;
    private List<Integer> numbersToDraw = new ArrayList<>();
    private List<BingoCard> bingoCards = new ArrayList<>();
    private int lastNumberDrawn;
    private BingoCard winningBingoCard;

    @GetMapping("day4/part1")
    public int solveBingoGetFirstWin() {
        setNumbersToDraw();
        setBingoCards();
        return getPlayBingoResult();
    }

    @GetMapping("day4/part2")
    public int solveBingoGetLastWin() {
        setNumbersToDraw();
        setBingoCards();
        return getKeepPlayingBingoResult();
    }

    private void setNumbersToDraw() {
        numbersToDraw = new ArrayList<>(getNumbersToDrawFromInput());
    }

    private void setBingoCards() {
        bingoCards = new ArrayList<>(getBingoCardsFromInput());
        log.info("Created {} bingo cards", bingoCards.size());
    }

    private List<Integer> getNumbersToDrawFromInput() {
        return FileService.getCommaSeparatedInputAsListInteger(DRAW_NUMBERS_FILE_NAME);
    }

    private List<BingoCard> getBingoCardsFromInput() {
        List<BingoCard> createdCards = new ArrayList<>();
        List<String> inputs = new ArrayList<>(FileService.getInputAsListString(BINGO_CARDS_FILE_NAME));
        inputs.removeIf(StringUtils::isBlank);
        for (int i = 0; i < inputs.size() - 5; i = i + 5) {
            createdCards.add(getBingoCardFromInput(List.of(inputs.get(i), inputs.get(i + 1), inputs.get(i + 2), inputs.get(i + 3), inputs.get(i + 4))));
        }
        return createdCards;
    }

    private BingoCard getBingoCardFromInput(List<String> rows) {
        List<BingoCardNumber> newBingoCardNumbers = new ArrayList<>();
        for (int i = 0; i < rows.size(); i++) {
            String[] columns = StringUtils.split(rows.get(i));
            for (int j = 0; j < columns.length; j++) {
                newBingoCardNumbers.add(new BingoCardNumber(i, j, Integer.parseInt(columns[j]), false));
            }
        }
        return new BingoCard(newBingoCardNumbers);
    }

    private int getPlayBingoResult() {
        while (winningBingoCard == null && numbersToDraw.size() >= 1) {
            int drawnNumber = numbersToDraw.get(0);
            log.info("Drawn {}", drawnNumber);
            lastNumberDrawn = drawnNumber;
            markDrawnNumberOnBingoCards(drawnNumber);
            checkForWinningCards();
            numbersToDraw.remove(0);
        }
        assert winningBingoCard != null;
        log.info("Winning card: {}", winningBingoCard);
        return lastNumberDrawn * winningBingoCard.getSumOfUnmarkedNumbers();
    }

    private int getKeepPlayingBingoResult() {
        while (bingoCards.stream().anyMatch(bingoCard -> !bingoCard.hasBingo())) {
            int drawnNumber = numbersToDraw.get(0);
            log.info("Drawn {}", drawnNumber);
            lastNumberDrawn = drawnNumber;
            markDrawnNumberOnBingoCards(drawnNumber);
            checkForWinningCards();
            numbersToDraw.remove(0);
        }
        return lastNumberDrawn * winningBingoCard.getSumOfUnmarkedNumbers();
    }

    private void markDrawnNumberOnBingoCards(int drawnNumber) {
        bingoCards.forEach(bingoCard -> bingoCard.markDrawnNumber(drawnNumber));
    }

    private void checkForWinningCards() {
        for (BingoCard bingoCard : bingoCards) {
            if (bingoCard.hasBingo()) {
                winningBingoCard = bingoCard;
            }
        }
        bingoCards.removeIf(BingoCard::hasBingo);
    }

    @Data
    @AllArgsConstructor
    public static class BingoCardNumber {
        int row;
        int column;
        int number;
        boolean marked;
    }

    @Data
    @AllArgsConstructor
    public static class BingoCard {
        List<BingoCardNumber> bingoCardNumbers;

        public int getSumOfUnmarkedNumbers() {
            return bingoCardNumbers.stream()
                    .filter(bingoCardNumber -> !bingoCardNumber.isMarked())
                    .mapToInt(BingoCardNumber::getNumber)
                    .sum();
        }

        public boolean hasBingo() {
            for (int i = 0; i < 5; i++) {
                int finalI = i;
                if (bingoCardNumbers.stream().filter(BingoCardNumber::isMarked).filter(bingoCardNumber -> bingoCardNumber.getRow() == finalI).count() == 5) {
                    return true;
                }
            }
            for (int i = 0; i < 5; i++) {
                int finalI = i;
                if (bingoCardNumbers.stream().filter(BingoCardNumber::isMarked).filter(bingoCardNumber -> bingoCardNumber.getColumn() == finalI).count() == 5) {
                    return true;
                }
            }
            return false;
        }

        public void markDrawnNumber(int drawnNumber) {
            bingoCardNumbers
                    .stream()
                    .filter(bingoCardNumber -> bingoCardNumber.number == drawnNumber)
                    .forEach(bingoCardNumber -> bingoCardNumber.setMarked(true));
        }
    }
}
