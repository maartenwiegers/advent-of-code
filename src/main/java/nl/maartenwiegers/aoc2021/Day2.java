package nl.maartenwiegers.aoc2021;

import lombok.extern.slf4j.Slf4j;
import nl.maartenwiegers.aoc2021.commons.FileService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@Slf4j
public class Day2 {

    private static final String INPUT_FILE_NAME = "input/02.txt";

    private static String getDirection(String movement) {
        return StringUtils.split(movement, " ")[0];
    }

    private static int getDistance(String movement) {
        return Integer.parseInt(StringUtils.split(movement, " ")[1]);
    }

    private static List<Movement> getMovements() {
        List<Movement> movements = new ArrayList<>();
        FileService.getInputAsListString(INPUT_FILE_NAME).forEach(movement -> movements.add(new Movement(getDirection(movement), getDistance(movement))));
        return movements;
    }

    @GetMapping("day2/part1")
    public int getMultiplicationOfFinalHorizontalPositionAndDepth() {
        AtomicInteger currentHorizontalPosition = new AtomicInteger();
        AtomicInteger currentDepth = new AtomicInteger();
        getMovements().forEach(movement -> {
            log.info(movement.toString());
            switch (movement.direction) {
                case "down" -> currentDepth.getAndAdd(movement.distanceOrAim);
                case "up" -> currentDepth.getAndAdd(movement.distanceOrAim * -1);
                case "forward" -> currentHorizontalPosition.getAndAdd(movement.distanceOrAim);
            }
            log.warn("currentDepth: {}", currentDepth.get());
            log.warn("currentHorizontalPosition: {}", currentHorizontalPosition.get());
        });
        return currentDepth.get() * currentHorizontalPosition.get();
    }

    @GetMapping("day2/part2")
    public int getMultiplicationOfFinalHorizontalPositionAndDepthCalculatedWithAim() {
        AtomicInteger currentHorizontalPosition = new AtomicInteger();
        AtomicInteger currentDepth = new AtomicInteger();
        AtomicInteger currentAim = new AtomicInteger();
        getMovements().forEach(movement -> {
            log.info(movement.toString());
            switch (movement.direction) {
                case "down" -> currentAim.getAndAdd(movement.distanceOrAim);
                case "up" -> currentAim.getAndAdd(movement.distanceOrAim * -1);
                case "forward" -> {
                    currentHorizontalPosition.getAndAdd(movement.distanceOrAim);
                    currentDepth.getAndAdd(currentAim.get() * movement.distanceOrAim);
                }
            }
            log.warn("currentDepth: {}", currentDepth.get());
            log.warn("currentHorizontalPosition: {}", currentHorizontalPosition.get());
            log.warn("currentAim: {}", currentAim.get());
        });
        return currentDepth.get() * currentHorizontalPosition.get();
    }

    private record Movement(String direction, int distanceOrAim) {

    }
}
