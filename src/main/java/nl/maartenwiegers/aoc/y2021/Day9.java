package nl.maartenwiegers.aoc.y2021;

import lombok.extern.slf4j.Slf4j;
import nl.maartenwiegers.aoc.commons.CoordinateWithDepth;
import nl.maartenwiegers.aoc.commons.FileService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

@Slf4j
public class Day9 {

    private static final String FILE_NAME = "input/y2021/09-%s.txt";
    private int gridWidth = 10;
    private int gridHeight = 5;

    private int[][] grid;
    private List<CoordinateWithDepth> lowPointsCoordinates;

    public int getSumOfRiskLevels(String filename) {
        setGrid(filename);
        setLowPoints();
        return lowPointsCoordinates.stream()
                .map(CoordinateWithDepth::getDepth)
                .mapToInt(Integer::intValue)
                .sum() + lowPointsCoordinates.size();
    }

    public int getFactorOfThreeLargestBasins(String filename) {
        setGrid(filename);
        setLowPoints();
        return getBasinCalculation();
    }

    private void setGrid(String filename) {
        if ("puzzleinput".equals(filename)) {
            gridWidth = 100;
            gridHeight = 100;
        }
        grid = new int[gridHeight][gridWidth];
        List<String> input = FileService.getInputAsListString(String.format(FILE_NAME, filename));
        for (int y = 0; y < input.size(); y++) {
            for (int x = 0; x < gridWidth; x++) {
                grid[y][x] = Integer.parseInt(input.get(y)
                        .substring(x, x + 1));
            }
        }
        log.debug("Initialized grid: {}", Arrays.deepToString(grid));
    }

    private void setLowPoints() {
        lowPointsCoordinates = new ArrayList<>();
        for (int y = 0; y < gridHeight; y++) {
            for (int x = 0; x < gridWidth; x++) {
                int depth = grid[y][x];
                log.debug("y {}, x {} has depth {}", y, x, depth);
                if (getAdjacentPoints(y, x).stream()
                        .allMatch(d -> d.getDepth() > depth)) {
                    log.info("y {}, x {} has been counted as low point", y, x);
                    lowPointsCoordinates.add(new CoordinateWithDepth(y, x, depth));
                }
            }
        }
        log.debug("Initialized lowPointsCoordinates {}", lowPointsCoordinates);
    }

    private List<CoordinateWithDepth> getAdjacentPoints(int y, int x) {
        List<CoordinateWithDepth> adjacentPoints = new ArrayList<>();
        if (x > 0) {
            adjacentPoints.add(new CoordinateWithDepth(y, x - 1, grid[y][x - 1]));
        }
        if (x < gridWidth - 1) {
            adjacentPoints.add(new CoordinateWithDepth(y, x + 1, grid[y][x + 1]));
        }
        if (y > 0) {
            adjacentPoints.add(new CoordinateWithDepth(y - 1, x, grid[y - 1][x]));
        }
        if (y < gridHeight - 1) {
            adjacentPoints.add(new CoordinateWithDepth(y + 1, x, grid[y + 1][x]));
        }
        return adjacentPoints;
    }

    private int getBasinCalculation() {
        List<Integer> basinSizes = new ArrayList<>();
        lowPointsCoordinates.forEach(lp -> basinSizes.add(getBasinSize(new HashSet<>(Collections.singleton(lp)))));
        basinSizes.sort(Integer::compareTo);
        Collections.reverse(basinSizes);
        log.info("Found basin sizes: {}", basinSizes);
        return basinSizes.stream()
                .limit(3)
                .reduce(1, Math::multiplyExact);
    }

    private int getBasinSize(HashSet<CoordinateWithDepth> basin) {
        List<CoordinateWithDepth> coordinates;
        do {
            coordinates = basin.stream()
                    .flatMap(b -> getAdjacentPoints(b.getY(), b.getX()).stream())
                    .filter(p -> p.getDepth() < 9)
                    .toList();
        } while (basin.addAll(coordinates));
        return basin.size();
    }
}
