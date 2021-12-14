package nl.maartenwiegers.aoc2021;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import nl.maartenwiegers.aoc2021.commons.FileService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
public class Day14 {
    private static final String FILE_NAME = "input/14-%s.txt";
    private final ArrayList<String> polymer = new ArrayList<>();
    private final ArrayList<InsertionRule> insertionRules = new ArrayList<>();

    @GetMapping("day14/polymer/{filename}/{steps}")
    public String getPolymerAfterSteps(@PathVariable String filename, @PathVariable int steps) {
        simulatePolymer(filename, steps);
        String finalPolymer = String.join("", polymer);
        log.info("Final state of the polymer: {}", finalPolymer);
        return finalPolymer;
    }

    @GetMapping("day14/score/{filename}/{steps}")
    public long getScore(@PathVariable String filename, @PathVariable int steps) {
        simulatePolymer(filename, steps);
        return getScore();
    }

    private void simulatePolymer(String filename, int steps) {
        initialize(filename);
        for (int i = 1; i <= steps; i++) {
            handleInsertion();
            log.info("Finished step {}", i);
        }
    }

    private void initialize(String filename) {
        polymer.clear();
        insertionRules.clear();
        List<String> inputs = FileService.getInputAsListString(String.format(FILE_NAME, filename));
        String template = inputs.get(0);
        for (char c : template.toCharArray()) {
            polymer.add(String.valueOf(c));
        }
        inputs.stream()
                .skip(2)
                .forEach(line -> insertionRules.add(new InsertionRule(StringUtils.split(line, " -> ")[0], StringUtils.split(line, " -> ")[1])));
        log.info("Initialized polymer: {}", polymer);
        log.info("Initialized insertions: {}", insertionRules);
    }

    private void handleInsertion() {
        int end = polymer.size();
        for (int i = 0; i < end - 1; i++) {
            String first = polymer.get(i);
            String second = polymer.get(i + 1);
            String insert = insertionRules.stream()
                    .filter(r -> r.getPair()
                            .equals(StringUtils.join(first, second)))
                    .findFirst()
                    .orElseThrow()
                    .getInsert();
            polymer.add(i + 1, insert);
            i++;
            end++;
        }
    }

    private long getScore() {
        List<String> distinctCharacters = polymer.stream()
                .distinct()
                .toList();
        List<Long> characterCounts = new ArrayList<>();
        for (String dc : distinctCharacters) {
            characterCounts.add(polymer.stream()
                    .filter(p -> p.equals(dc))
                    .count());
        }
        long max = characterCounts.stream()
                .mapToLong(Long::longValue)
                .max()
                .orElse(0);
        long min = characterCounts.stream()
                .mapToLong(Long::longValue)
                .min()
                .orElse(0);
        return max - min;
    }

    @Data
    @AllArgsConstructor
    private static class InsertionRule {
        String pair;
        String insert;
    }
}
