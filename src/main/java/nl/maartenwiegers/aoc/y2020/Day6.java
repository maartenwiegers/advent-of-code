package nl.maartenwiegers.aoc.y2020;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import nl.maartenwiegers.aoc.commons.FileService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
public class Day6 {

    private static final String FILE_NAME = "input/y2020/06-%s.txt";
    private final List<String> answers = new ArrayList<>();

    public long getSumOfCountOfAnswers(String filename) {
        initializeAnswers(filename);
        AtomicLong count = new AtomicLong();
        answers.forEach(answer -> count.getAndAdd(answer.chars().distinct().count()));
        return count.get();
    }

    @SneakyThrows
    private void initializeAnswers(String filename) {
        FileService.getMultiLineInputAsListString(String.format(FILE_NAME, filename))
                .forEach(line -> answers.add(line.replace("\r\n", "")));
        log.info("Answers initialized: {}", answers);
    }
}
