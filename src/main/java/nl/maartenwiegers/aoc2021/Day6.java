package nl.maartenwiegers.aoc2021;

import lombok.extern.slf4j.Slf4j;
import nl.maartenwiegers.aoc2021.commons.FileService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

@RestController
@Slf4j
public class Day6 {

    private static final String FILE_NAME = "input/06.txt";
    private final List<Long> fish = new ArrayList<>();

    @GetMapping("day6/part1/{afterDays}")
    public long getCountOfFishAfterDays(@PathVariable int afterDays) {
        fish.clear();
        List<Long> inputs = FileService.getCommaSeparatedInputAsListLong(FILE_NAME);
        fish.addAll(inputs);
        log.info("Initial state: {}", fish);
        for (int i = 1; i <= afterDays; i++) {
            int fishSize = fish.size();
            for(int f = 0; f < fishSize; f++){
                if(fish.get(f) == 0L) {
                    fish.set(f, 6L);
                    fish.add(8L);
                } else {
                    fish.set(f, fish.get(f) - 1);
                }
            }
            log.info("After {} day{}: {}", StringUtils.leftPad(String.valueOf(i), 2), i == 1 ? " " : "s", fish);
        }
        return fish.size();
    }

    static class SubtractOperator implements UnaryOperator<Long> {
        @Override
        public Long apply(Long l) {
            if (l == 0L) {
                return 6L;
            } else {
                return l - 1;
            }
        }
    }
}
