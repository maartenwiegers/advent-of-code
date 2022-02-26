package nl.maartenwiegers.aoc.y2020;

import lombok.extern.slf4j.Slf4j;
import nl.maartenwiegers.aoc.commons.FileService;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.IntStream;

@Slf4j
public class Day6 {

    private static final String FILE_NAME = "input/y2020/06-%s.txt";
    private final List<String> answers = new ArrayList<>();

    public long getSumOfCountOfAnswers(String filename) {
        FileService.getMultiLineInputAsListString(String.format(FILE_NAME, filename))
                .forEach(line -> answers.add(line.replace("\r\n", "")));
        log.info("Answers initialized: {}", answers);
        AtomicLong count = new AtomicLong();
        answers.forEach(answer -> count.getAndAdd(answer.chars().distinct().count()));
        return count.get();
    }

    public long getSumOfCountOfYesAnswers(String filename) {
        answers.addAll(FileService.getMultiLineInputAsListString(String.format(FILE_NAME, filename)));
        log.info("Answers initialized: {}", answers);
        AtomicLong count = new AtomicLong();
        for (String answer : answers) {
            String[] lines = StringUtils.split(answer);
            IntStream.rangeClosed('a', 'z').forEach(c -> {
                if (Arrays.stream(lines).allMatch(line -> StringUtils.contains(line, c))) {
                    count.getAndIncrement();
                }
            });
        }
        return count.get();
    }
}
