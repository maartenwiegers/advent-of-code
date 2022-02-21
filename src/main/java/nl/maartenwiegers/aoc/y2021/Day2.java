package nl.maartenwiegers.aoc.y2021;

import lombok.extern.slf4j.Slf4j;
import nl.maartenwiegers.aoc.commons.FileService;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class Day2 {

    private static final String INPUT_FILE_NAME = "input/y2021/02.txt";

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

    private List<Movement> getMovements() {
        List<Movement> movements = new ArrayList<>();
        FileService.getInputAsListString(INPUT_FILE_NAME)
                .forEach(movement -> movements.add(new Movement(getDirection(movement), getDistance(movement))));
        return movements;
    }

    private String getDirection(String movement) {
        return StringUtils.split(movement, " ")[0];
    }

    private int getDistance(String movement) {
        return Integer.parseInt(StringUtils.split(movement, " ")[1]);
    }

    private record Movement(String direction, int distanceOrAim) {

    }
}
