package nl.maartenwiegers.aoc.y2021;

import lombok.extern.slf4j.Slf4j;
import nl.maartenwiegers.aoc.commons.FileService;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class Day8 {

    private static final String FILE_NAME = "input/y2021/08-%s.txt";

    public int getCountUsagesOfDigits(String dataset) {
        List<String> lines = FileService.getInputAsListString(String.format(FILE_NAME, dataset));
        AtomicInteger count = new AtomicInteger();
        lines.forEach(line -> {
            log.info("Line : {}", line);
            String outputValue = StringUtils.split(line, '|')[1];
            Arrays.stream(StringUtils.split(outputValue, ' '))
                    .forEach(signal -> {
                        if (signal.length() == 2 // digit 1
                                || signal.length() == 3 // digit 7
                                || signal.length() == 4 // digit 4
                                || signal.length() == 7 // digit 8
                        ) {
                            log.info("    Counted: {}", signal);
                            count.getAndIncrement();
                        }
                    });
        });
        return count.get();
    }

    public int getSumOfOutputs(String dataset) {
        List<String> lines = FileService.getInputAsListString(String.format(FILE_NAME, dataset));
        int sum = 0;
        for (String line : lines) {
            String[] parts = line.split("\\|");
            List<String> uniques = Arrays.stream(parts[0].trim()
                            .split(" "))
                    .map(Day8::getSortedAlphabetically)
                    .toList();
            List<String> output = Arrays.stream(parts[1].trim()
                            .split(" "))
                    .map(Day8::getSortedAlphabetically)
                    .toList();
            List<String> numSegments = Arrays.asList(calcNumSegments(uniques));
            AtomicInteger num = new AtomicInteger();
            output.forEach(value -> {
                num.set(num.get() * 10);
                num.getAndAdd(numSegments.indexOf(value));
            });
            sum = sum + num.get();
        }
        return sum;
    }

    private String[] calcNumSegments(List<String> uniques) {
        List<String> uniqueCopy = new ArrayList<>(uniques);
        String[] numPatterns = new String[10];

        for (String pattern : uniques) {
            switch (pattern.length()) {
                case 2 -> numPatterns[1] = pattern;
                case 3 -> numPatterns[7] = pattern;
                case 4 -> numPatterns[4] = pattern;
                case 7 -> numPatterns[8] = pattern;
                default -> { /* do nothing */}
            }
        }
        uniqueCopy.remove(numPatterns[1]);
        uniqueCopy.remove(numPatterns[7]);
        uniqueCopy.remove(numPatterns[4]);
        uniqueCopy.remove(numPatterns[8]);

        // Of all numbers with 6 segments (0,6,9), only 9 contains all segments of 4
        numPatterns[9] = uniqueCopy.stream()
                .filter(e -> e.length() == 6 && containsAll(e, numPatterns[4]))
                .findFirst()
                .get();
        uniqueCopy.remove(numPatterns[9]);

        // only 0 and 6 have 6 segments. 0 contains all segments of 1, 6 does not
        numPatterns[0] = uniqueCopy.stream()
                .filter(e -> e.length() == 6 && containsAll(e, numPatterns[1]))
                .findFirst()
                .get();
        uniqueCopy.remove(numPatterns[0]);

        // One 6 has a length of 6 segments now.
        numPatterns[6] = uniqueCopy.stream()
                .filter(e -> e.length() == 6)
                .findFirst()
                .get();
        uniqueCopy.remove(numPatterns[6]);

        // Only 2, 3 and 5 left. 3 contains all segments of 1
        numPatterns[3] = uniqueCopy.stream()
                .filter(e -> containsAll(e, numPatterns[1]))
                .findFirst()
                .get();
        uniqueCopy.remove(numPatterns[3]);

        // Only 2 and 5 left. 9 contains all segments of 5.
        numPatterns[5] = uniqueCopy.stream()
                .filter(e -> containsAll(numPatterns[9], e))
                .findFirst()
                .get();
        uniqueCopy.remove(numPatterns[5]);

        // Only 2 is left now
        numPatterns[2] = uniqueCopy.get(0);

        return numPatterns;
    }

    private boolean containsAll(String val1, String val2) {
        for (int i = 0; i < val2.length(); i++) {
            if (!val1.contains("" + val2.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private static String getSortedAlphabetically(String input) {
        char[] charArray = input.toCharArray();
        Arrays.sort(charArray);
        return new String(charArray);
    }


}
