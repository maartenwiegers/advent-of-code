package nl.maartenwiegers.aoc.y2021;

import lombok.extern.slf4j.Slf4j;
import nl.maartenwiegers.aoc.commons.FileService;
import org.apache.commons.lang3.StringUtils;

import java.math.BigInteger;
import java.util.Arrays;

@Slf4j
public class Day6 {

    private static final String FILE_NAME = "input/y2021/06.txt";
    private final BigInteger[] fish = new BigInteger[9];

    public BigInteger getCountOfFishAfterDays(int afterDays) {
        for (int i = 0; i < 9; i++) {
            fish[i] = BigInteger.ZERO;
        }
        FileService.getCommaSeparatedInputAsListInteger(FILE_NAME)
                .forEach(input -> fish[input] = fish[input].add(BigInteger.ONE));
        log.info("Initial state: {}", Arrays.toString(fish));
        for (int i = 1; i <= afterDays; i++) {
            BigInteger births = fish[0];
            System.arraycopy(fish, 1, fish, 0, 8);
            fish[6] = fish[6].add(births);
            fish[8] = births;
            log.info("After {} day{}: {}", StringUtils.leftPad(String.valueOf(i), 2), i == 1 ? " " : "s", Arrays.toString(fish));
        }
        return Arrays.stream(fish)
                .reduce(BigInteger::add)
                .get();
    }
}
