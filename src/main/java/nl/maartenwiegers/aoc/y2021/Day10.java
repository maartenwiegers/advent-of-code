package nl.maartenwiegers.aoc.y2021;

import lombok.extern.slf4j.Slf4j;
import nl.maartenwiegers.aoc.commons.FileService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Slf4j
public class Day10 {

    private static final String FILE_NAME = "input/y2021/10-%s.txt";
    private static final List<Character> OPENING_CHARS = List.of('(', '[', '{', '<');
    private static final Map<Character, Character> PAIRS = Map.of('(', ')', '[', ']', '{', '}', '<', '>');
    private static final Map<Character, Integer> POINTS = Map.of(')', 3, ']', 57, '}', 1197, '>', 25137);
    private static final Map<Character, Integer> POINTS_PART_2 = Map.of(')', 1, ']', 2, '}', 3, '>', 4);

    public int getScore(String filename) {
        return FileService.getInputAsListString(String.format(FILE_NAME, filename))
                .stream()
                .map(this::getCleanString)
                .map(this::getScoreForLine)
                .mapToInt(Integer::intValue)
                .sum();
    }

    public long getAutocompleteScore(String filename) {
        List<Long> autocompleteScores = FileService.getInputAsListString(String.format(FILE_NAME, filename))
                .stream()
                .map(this::getCleanString)
                .filter(line -> getScoreForLine(line) == 0)
                .map(this::getAutocompleteScoreForLine)
                .sorted()
                .toList();
        return autocompleteScores.get(autocompleteScores.size() / 2);
    }

    private int getScoreForLine(String line) {
        for (int i = 0; i < line.length() - 1; i++) {
            char current = line.charAt(i);
            char next = line.charAt(i + 1);
            if (!getNextCharactersExpected(current).contains(next) && i < line.length() - 1) {
                return POINTS.get(next);
            }
        }
        return 0;
    }

    private long getAutocompleteScoreForLine(String line) {
        long score = 0;
        List<Character> chars = new ArrayList<>(line.chars()
                .mapToObj(e -> (char) e)
                .toList());
        Collections.reverse(chars);
        for (Character aChar : chars) {
            char closingChar = PAIRS.get(aChar);
            score = score * 5;
            score = score + POINTS_PART_2.get(closingChar);
        }
        return score;
    }

    private String getCleanString(String input) {
        int previousLength;
        do {
            previousLength = input.length();
            input = input.replace("()", "")
                    .replace("[]", "")
                    .replace("{}", "")
                    .replace("<>", "");
        } while (input.length() != previousLength);
        return input;
    }

    private List<Character> getNextCharactersExpected(char current) {
        List<Character> temp = new ArrayList<>();
        temp.add(PAIRS.get(current));
        temp.addAll(OPENING_CHARS);
        return temp;
    }
}
