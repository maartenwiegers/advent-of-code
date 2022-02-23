package nl.maartenwiegers.aoc.y2020;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import nl.maartenwiegers.aoc.commons.FileService;
import org.apache.commons.lang3.StringUtils;

import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
public class Day6 {

    private static final String FILE_NAME = "input/y2020/06-%s.txt";
    private final List<String> answers = new ArrayList<>();

    public long getSumOfCountOfAnswers(String filename) {
        initializeAnswers(filename);
        AtomicLong count = new AtomicLong();
        answers.forEach(answer -> count.getAndAdd(answer.chars()
                                                          .distinct()
                                                          .count()));
        return count.get();
    }

    @SneakyThrows
    private void initializeAnswers(String filename) {
        String input = Files.readString(FileService.getPath(String.format(FILE_NAME, filename)));
        Arrays.stream(StringUtils.splitByWholeSeparatorPreserveAllTokens(input, "\r\n\r\n"))
                .forEach(line -> answers.add(line.replace("\r\n", "")));
        log.info("Answers initialized: {}", answers);
    }
}
