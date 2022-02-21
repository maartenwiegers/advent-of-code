package nl.maartenwiegers.aoc.y2021;

import lombok.SneakyThrows;
import nl.maartenwiegers.aoc.commons.FileService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class Day1 {

    private static final String INPUT_FILE_NAME = "input/y2021/01-01.txt";

    @GetMapping("day1/part1")
    @SneakyThrows
    public int getCountMeasurementsHigherThanPrevious() {
        AtomicInteger measurementsHigherThanPrevious = new AtomicInteger();
        AtomicInteger previous = new AtomicInteger();
        List<Integer> measurements = FileService.getInputAsListInteger(INPUT_FILE_NAME);
        measurements.forEach(measurement -> {
            int current = measurement;
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
        List<Integer> measurements = FileService.getInputAsListInteger(INPUT_FILE_NAME);
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
