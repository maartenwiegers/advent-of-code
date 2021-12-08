package nl.maartenwiegers.aoc2021;

import lombok.extern.slf4j.Slf4j;
import nl.maartenwiegers.aoc2021.commons.FileService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@Slf4j
public class Day8 {

    private static final String FILE_NAME = "input/08-%s.txt";

    @GetMapping("day8/part1/{dataset}")
    public int getCountUsagesOfDigits(@PathVariable String dataset) {
        List<String> lines = FileService.getInputAsListString(String.format(FILE_NAME, dataset));
        AtomicInteger count = new AtomicInteger();
        lines.forEach(line -> {
            log.info("Line : {}", line);
            String outputValue = StringUtils.split(line, '|')[1];
            Arrays.stream(StringUtils.split(outputValue, ' ')).forEach(signal -> {
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

    @GetMapping("day8/part2/{dataset}")
    public int getSumOfOutputs(@PathVariable String dataset) {
        List<String> lines = FileService.getInputAsListString(String.format(FILE_NAME, dataset));
        AtomicInteger sum = new AtomicInteger();
        lines.forEach(line -> {
            log.info("Line: {}", line);
        });
        return sum.get();
    }
}
