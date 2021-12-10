package nl.maartenwiegers.aoc2021;

import lombok.extern.slf4j.Slf4j;
import nl.maartenwiegers.aoc2021.commons.FileService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class Day10 {

    private static final String FILE_NAME = "input/10-%s.txt";
    private static final List<Character> OPENING_CHARS = List.of('(', '[', '{', '<');
    private static final Map<Character, Character> PAIRS = Map.of('(', ')', '[', ']', '{', '}', '<', '>');
    private static final Map<Character, Integer> POINTS = Map.of(')', 3, ']', 57, '}', 1197, '>', 25137);

    @GetMapping("day10/part1/{filename}")
    public int getScore(@PathVariable String filename) {
        List<String> lines = new ArrayList<>(FileService.getInputAsListString(String.format(FILE_NAME, filename)));
        return lines
                .stream()
                .map(this::getCleanString)
                .map(this::getScoreForLine)
                .mapToInt(Integer::intValue)
                .sum();
    }

    private int getScoreForLine(String line) {
        log.debug("Working line {}", line);
        for (int i = 0; i < line.length() - 1; i++) {
            char current = line.charAt(i);
            log.debug("Current char: {}", current);
            char next = line.charAt(i + 1);
            log.debug("Next char: {}", next);
            log.debug("Next chars expected: {}", getNextCharactersExpected(current));
            if (!getNextCharactersExpected(current).contains(next) && i < line.length() - 1) {
                log.info(String.valueOf(POINTS.get(next)));
                log.info("Found illegal character {} at {}", next, i);
                return POINTS.get(next);
            }
        }
        return 0;
    }

    private String getCleanString(String input) {
        log.debug("Cleaning string {}", input);
        int previousLength;
        do {
            previousLength = input.length();
            input = input.replace("()", "").replace("[]", "").replace("{}", "").replace("<>", "");
        } while (input.length() != previousLength);
        log.debug("Cleaned: {}", input);
        return input;
    }

    private List<Character> getNextCharactersExpected(char current) {
        List<Character> temp = new ArrayList<>();
        temp.add(PAIRS.get(current));
        temp.addAll(OPENING_CHARS);
        return temp;
    }
}
