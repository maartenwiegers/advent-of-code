package nl.maartenwiegers.aoc2021;

import lombok.extern.slf4j.Slf4j;
import nl.maartenwiegers.aoc2021.commons.FileService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.LongSummaryStatistics;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class Day14 {
    private static final String FILE_NAME = "input/14-%s.txt";
    private final Map<String, String> insertionRules = new HashMap<>();
    private Map<String, Long> pairCount = new HashMap<>();
    private String polymer;

    @GetMapping("day14/score/{filename}/{steps}")
    public long getScore(@PathVariable String filename, @PathVariable int steps) {
        simulatePolymer(filename, steps);
        return getScore();
    }

    private void simulatePolymer(String filename, int steps) {
        initialize(filename);

        Map<String, Long> pairToInsert = new HashMap<>();
        for (int i = 0; i < polymer.length() - 1; i++) {
            pairToInsert.compute(polymer.substring(i, i + 2), (k, v) -> v == null ? 1 : v + 1);
        }
        pairCount = polymer.chars()
                .mapToObj(Character::toString)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        for (int i = 1; i <= steps; i++) {
            Map<String, Long> newPairsToInsert = new HashMap<>(pairToInsert);
            for (Map.Entry<String, Long> e : pairToInsert.entrySet()) {
                String insertion = insertionRules.get(e.getKey());
                newPairsToInsert.compute(e.getKey(), (k, v) -> v - e.getValue());
                BiFunction<String, Long, Long> increase = (k, v) -> e.getValue() + (v == null ? 0 : v);
                newPairsToInsert.compute(e.getKey()
                        .charAt(0) + insertion, increase);
                newPairsToInsert.compute(insertion + e.getKey()
                        .charAt(1), increase);
                pairCount.compute(insertion, increase);
            }
            pairToInsert = newPairsToInsert;
            log.info("Finished step {}", i);
        }
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

    private long getScore() {
        LongSummaryStatistics stats = pairCount.values()
                .stream()
                .mapToLong(l -> l)
                .summaryStatistics();
        return stats.getMax() - stats.getMin();
    }
}
