package nl.maartenwiegers.aoc2021;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import nl.maartenwiegers.aoc2021.commons.FileService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Files;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@Slf4j
public class Day1 {

    private static final String INPUT_FILE_NAME = "input/01-01.txt";

    @GetMapping("day1/part1")
    @SneakyThrows
    public int getCountMeasurementsHigherThanPrevious() {
        AtomicInteger measurementsHigherThanPrevious = new AtomicInteger();
        AtomicInteger previous = new AtomicInteger();
        Stream<String> inputFileStream = Files.lines(FileService.getPath(INPUT_FILE_NAME));
        inputFileStream.forEach(line -> {
            int current = Integer.parseInt(line);
            if (current > previous.get()) {
                measurementsHigherThanPrevious.getAndIncrement();
            }
            previous.set(current);
        });
        return measurementsHigherThanPrevious.get() - 1;
    }

    @GetMapping("day1/part2")
    @SneakyThrows
    public int getCountSumsHigherThanPrevious() {
        AtomicInteger measurementsHigherThanPrevious = new AtomicInteger();
        AtomicInteger previous = new AtomicInteger();
        List<Integer> measurements = Files.lines(FileService.getPath(INPUT_FILE_NAME)).map(Integer::valueOf).toList();
        for (int i = 0; i < measurements.size() - 2; i++) {
            int sum = measurements.get(i) + measurements.get(i + 1) + measurements.get(i + 2);
            if (sum > previous.get()) {
                measurementsHigherThanPrevious.getAndIncrement();
            }
            previous.set(sum);
        }
        return measurementsHigherThanPrevious.get() - 1;
    }
}
