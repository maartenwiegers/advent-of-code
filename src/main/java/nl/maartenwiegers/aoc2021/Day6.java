package nl.maartenwiegers.aoc2021;

import lombok.extern.slf4j.Slf4j;
import nl.maartenwiegers.aoc2021.commons.FileService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@Slf4j
public class Day6 {

    private static final String FILE_NAME = "input/06.txt";
    private final long[] fish = new long[9];

    @GetMapping("day6/{afterDays}")
    public long getCountOfFishAfterDays(@PathVariable int afterDays) {
        FileService.getCommaSeparatedInputAsListInteger(FILE_NAME).forEach(input -> fish[input]++);
        log.info("Initial state: {}", fish);
        for (int i = 1; i <= afterDays; i++) {
            long births = fish[0];
            System.arraycopy(fish, 1, fish, 0, 8);
            fish[6] += births;
            fish[8] = births;
            log.info("After {} day{}: {}", StringUtils.leftPad(String.valueOf(i), 2), i == 1 ? " " : "s", fish);
        }
        return Arrays.stream(fish).sum();
    }
}
