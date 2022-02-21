package nl.maartenwiegers.aoc.y2020;

import lombok.extern.slf4j.Slf4j;
import nl.maartenwiegers.aoc.commons.FileService;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Day1 {

    private static final String FILE_NAME = "input/y2020/01-%s.txt";
    private List<Integer> entries = new ArrayList<>();

    public long getMultiplicationOf2NumbersSum2020(String filename) {
        entries = FileService.getInputAsListInteger(String.format(FILE_NAME, filename));
        return calculateMultiplicationOf2Entries();
    }

    public long getMultiplicationOf3NumbersSum2020(String filename) {
        entries = FileService.getInputAsListInteger(String.format(FILE_NAME, filename));
        return calculateMultiplicationOf3Entries();
    }

    private long calculateMultiplicationOf2Entries() {
        long a;
        long b;

        for (int i = 0; i < entries.size() - 1; i++) {
            a = entries.get(i);
            for (int j = i; j < entries.size() - 1; j++) {
                b = entries.get(j + 1);
                log.info("Working with a {} and b {}", a, b);
                if (a + b == 2020) {
                    return a * b;
                }
            }
        }

        return 0L;
    }

    private long calculateMultiplicationOf3Entries() {
        long a;
        long b;
        long c;

        for (int i = 0; i < entries.size() - 1; i++) {
            a = entries.get(i);
            for (int j = i; j < entries.size() - 1; j++) {
                b = entries.get(j + 1);
                for (int k = j; k < entries.size() - 1; k++) {
                    c = entries.get(k + 1);
                    log.info("Working with a {} and b {} and c {}", a, b, c);
                    if (a + b + c == 2020) {
                        return a * b * c;
                    }
                }
            }
        }

        return 0L;
    }
}
