package nl.maartenwiegers.aoc.y2021;

import lombok.extern.slf4j.Slf4j;
import nl.maartenwiegers.aoc.commons.FileService;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.LongSummaryStatistics;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
public class Day14 {
    private static final String FILE_NAME = "input/y2021/14-%s.txt";
    private final Map<String, String> insertionRules = new HashMap<>();
    private Map<String, Long> characterCounts = new HashMap<>();
    private String polymer;

    public long getScore(String filename, int steps) {
        initialize(filename);
        simulatePolymer(steps);
        return getScore();
    }

    private void initialize(String filename) {
        insertionRules.clear();
        List<String> inputs = FileService.getInputAsListString(String.format(FILE_NAME, filename));
        polymer = inputs.get(0);
        inputs.stream()
                .skip(2)
                .forEach(line -> insertionRules.put(StringUtils.split(line, " -> ")[0], StringUtils.split(line, " -> ")[1]));
        log.info("Initialized polymer: {}", polymer);
        log.info("Initialized insertions: {}", insertionRules);
    }

    private void simulatePolymer(int steps) {
        Map<String, Long> currentPairs = new HashMap<>();
        for (int i = 0; i < polymer.length() - 1; i++) {
            currentPairs.compute(polymer.substring(i, i + 2), (k, v) -> v == null ? 1 : v + 1);
        }
        characterCounts = polymer.chars()
                .mapToObj(Character::toString)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        for (int i = 1; i <= steps; i++) {
            Map<String, Long> newPairs = new HashMap<>(currentPairs);
            for (Entry<String, Long> currentPair : currentPairs.entrySet()) {
                String insertion = insertionRules.get(currentPair.getKey());
                newPairs.compute(currentPair.getKey(), (k, v) -> v - currentPair.getValue());
                BiFunction<String, Long, Long> increase = (k, v) -> currentPair.getValue() + (v == null ? 0 : v);
                newPairs.compute(currentPair.getKey()
                        .charAt(0) + insertion, increase);
                newPairs.compute(insertion + currentPair.getKey()
                        .charAt(1), increase);
                characterCounts.compute(insertion, increase);
            }
            currentPairs = newPairs;
            log.info("Finished step {}", i);
        }
    }

    private long getScore() {
        LongSummaryStatistics stats = characterCounts.values()
                .stream()
                .mapToLong(l -> l)
                .summaryStatistics();
        return stats.getMax() - stats.getMin();
    }
}
