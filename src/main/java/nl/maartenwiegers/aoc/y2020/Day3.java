package nl.maartenwiegers.aoc.y2020;

import lombok.extern.slf4j.Slf4j;
import nl.maartenwiegers.aoc.commons.Coordinate;
import nl.maartenwiegers.aoc.commons.FileService;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Day3 {

    private static final String FILE_NAME = "input/y2020/03-%s.txt";
    private final List<Coordinate> coordinatesWithTree = new ArrayList<>();
    private int gridHeight;

    public long getMultiplicationOfTreeEncounters(String filename, List<Integer> horizontalSteps,
                                                  List<Integer> verticalSteps) {
        List<Long> treesHit = new ArrayList<>();
        for (int i = 0; i < horizontalSteps.size(); i++) {
            initializeTrees(filename, horizontalSteps.get(i), verticalSteps.get(i));
            treesHit.add(getCountTreeEncounters(horizontalSteps.get(i), verticalSteps.get(i)));
        }
        log.info("Trees hit: {}", treesHit);
        return treesHit.stream()
                .reduce(1L, Math::multiplyExact);
    }

    private void initializeTrees(String filename, int moveHorizontal, int moveVertical) {
        List<String> lines = FileService.getInputAsListString(String.format(FILE_NAME, filename));
        int gridWidth = lines.get(0)
                .length();
        gridHeight = lines.size();
        for (int base = 0; base < moveHorizontal * moveVertical * gridHeight; base += gridWidth) {
            for (int y = 0; y < gridHeight; y++) {
                String line = lines.get(y);
                for (int x = 0; x < gridWidth; x++) {
                    if (StringUtils.substring(line, x, x + 1)
                            .equals("#")) {
                        coordinatesWithTree.add(new Coordinate(y, base + x));
                        log.info("Tree at y={} x={}", y, base + x);
                    }
                }
            }
        }
    }

    private long getCountTreeEncounters(int moveHorizontal, int moveVertical) {
        Coordinate currentPosition = new Coordinate(0, 0);
        long count = 0;

        while (currentPosition.getY() <= gridHeight) {
            log.info("Current position: {}", currentPosition);
            if (coordinatesWithTree.contains(currentPosition)) {
                count++;
            }
            currentPosition = new Coordinate(currentPosition.getY() + moveVertical,
                                             currentPosition.getX() + moveHorizontal);
        }

        return count;
    }
}
