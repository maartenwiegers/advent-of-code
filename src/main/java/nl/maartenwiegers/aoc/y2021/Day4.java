package nl.maartenwiegers.aoc.y2021;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import nl.maartenwiegers.aoc.commons.FileService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Day4 {

    private static final String BINGO_CARDS_FILE_NAME = "input/y2021/04-bingocards-%s.txt";
    private static final String DRAW_NUMBERS_FILE_NAME = "input/y2021/04-drawnumbers-%s.txt";
    protected int gridSize;
    private List<Integer> numbersToDraw = new ArrayList<>();
    private List<BingoCard> bingoCards = new ArrayList<>();
    private int lastNumberDrawn;
    private BingoCard winningBingoCard;

    public int solveBingoGetFirstWin(@PathVariable int gridSize) {
        log.warn("Playing part 1 with grid size of {} ", gridSize);
        this.gridSize = gridSize;
        winningBingoCard = null;
        setNumbersToDraw();
        setBingoCards();
        return getPlayBingoResult(false);
    }

    public int solveBingoGetLastWin(@PathVariable int gridSize) {
        log.warn("Playing part 2 with grid size of {} ", gridSize);
        this.gridSize = gridSize;
        winningBingoCard = null;
        setNumbersToDraw();
        setBingoCards();
        return getPlayBingoResult(true);
    }

    private void setNumbersToDraw() {
        numbersToDraw = new ArrayList<>(getNumbersToDrawFromInput());
        log.info("Populated {} numbers to draw: {}", numbersToDraw.size(), numbersToDraw);
    }

    private void setBingoCards() {
        bingoCards = new ArrayList<>(getBingoCardsFromInput());
        log.info("Created {} bingo cards", bingoCards.size());
    }

    private List<Integer> getNumbersToDrawFromInput() {
        return FileService.getCommaSeparatedInputAsListInteger(String.format(DRAW_NUMBERS_FILE_NAME, gridSize));
    }

    private List<BingoCard> getBingoCardsFromInput() {
        List<BingoCard> createdCards = new ArrayList<>();
        List<String> inputs = new ArrayList<>(FileService.getInputAsListString(String.format(BINGO_CARDS_FILE_NAME, gridSize)));
        inputs.removeIf(StringUtils::isBlank);
        for (int i = 0; i < inputs.size() - gridSize; i = i + gridSize) {
            List<String> rowsForThisCard = new ArrayList<>();
            inputs.stream()
                    .skip(i)
                    .limit(gridSize)
                    .forEachOrdered(rowsForThisCard::add);
            createdCards.add(getBingoCardFromInput(i, rowsForThisCard));
        }
        return createdCards;
    }

    private BingoCard getBingoCardFromInput(int id, List<String> rows) {
        List<BingoCardNumber> newBingoCardNumbers = new ArrayList<>();
        for (int i = 0; i < rows.size(); i++) {
            String[] columns = StringUtils.split(rows.get(i));
            for (int j = 0; j < columns.length; j++) {
                newBingoCardNumbers.add(new BingoCardNumber(i, j, Integer.parseInt(columns[j]), false));
            }
        }
        return new BingoCard(id, newBingoCardNumbers, gridSize);
    }

    private int getPlayBingoResult(boolean keepPlaying) {
        while ((!keepPlaying && winningBingoCard == null) || (keepPlaying && areBingoCardsWithoutWinLeft())) {
            int drawnNumber = numbersToDraw.get(0);
            log.info("Drawn {}, count of numbers left: {}", drawnNumber, numbersToDraw.size());
            lastNumberDrawn = drawnNumber;
            markDrawnNumberOnBingoCards(drawnNumber);
            checkForWinningCards();
            numbersToDraw.remove(0);
        }
        assert winningBingoCard != null;
        int answer = lastNumberDrawn * winningBingoCard.getSumOfUnmarkedNumbers();
        log.info("Answer: {}", answer);
        return answer;
    }

    private void markDrawnNumberOnBingoCards(int drawnNumber) {
        bingoCards.forEach(bingoCard -> bingoCard.markDrawnNumber(drawnNumber));
    }

    private void checkForWinningCards() {
        for (BingoCard bingoCard : bingoCards) {
            if (bingoCard.hasWin()) {
                winningBingoCard = bingoCard;
                log.info("Winning card: {}", winningBingoCard);
            }
        }
        bingoCards.removeIf(BingoCard::hasWin);
    }

    private boolean areBingoCardsWithoutWinLeft() {
        return bingoCards.stream()
                .anyMatch(bingoCard -> !bingoCard.hasWin());
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
        int id;
        List<BingoCardNumber> bingoCardNumbers;
        private int gridSize;

        public int getSumOfUnmarkedNumbers() {
            return bingoCardNumbers.stream()
                    .filter(bingoCardNumber -> !bingoCardNumber.isMarked())
                    .mapToInt(BingoCardNumber::getNumber)
                    .sum();
        }

        public boolean hasWin() {
            if (bingoCardNumbers.stream()
                    .noneMatch(BingoCardNumber::isMarked)) {
                return false;
            }

            for (int i = 0; i < gridSize; i++) {
                int finalI = i;
                if (bingoCardNumbers.stream()
                        .filter(bingoCardNumber -> bingoCardNumber.getRow() == finalI)
                        .allMatch(BingoCardNumber::isMarked)) {
                    return true;
                }
            }
            for (int i = 0; i < gridSize; i++) {
                int finalI = i;
                if (bingoCardNumbers.stream()
                        .filter(bingoCardNumber -> bingoCardNumber.getColumn() == finalI)
                        .allMatch(BingoCardNumber::isMarked)) {
                    return true;
                }
            }
            return false;
        }

        public void markDrawnNumber(int drawnNumber) {
            bingoCardNumbers.stream()
                    .filter(bingoCardNumber -> bingoCardNumber.number == drawnNumber)
                    .forEach(bingoCardNumber -> bingoCardNumber.setMarked(true));
        }
    }
}
