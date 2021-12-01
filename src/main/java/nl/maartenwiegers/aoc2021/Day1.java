package nl.maartenwiegers.aoc2021;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import nl.maartenwiegers.aoc2021.commons.FileService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Files;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

@RestController
@Slf4j
public class Day1 {

    @GetMapping("day1")
    @SneakyThrows
    public int getCountMeasurementsHigherThanPrevious() {
        AtomicInteger measurementsHigherThanPrevious = new AtomicInteger();
        AtomicInteger previous = new AtomicInteger();
        Stream<String> inputFileStream =  Files.lines(FileService.getPath("input/01-01.txt"));
        inputFileStream.forEach(line -> {
            int current = Integer.parseInt(line);
            if (current > previous.get()) {
                measurementsHigherThanPrevious.getAndIncrement();
            }
            previous.set(current);
        });
        return measurementsHigherThanPrevious.get() -1;
    }
}
