package nl.maartenwiegers.aoc2021;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import nl.maartenwiegers.aoc2021.commons.FileService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@Slf4j
public class Day9 {

    private static final String FILE_NAME = "input/09-%s.txt";
    private int gridWidth = 10;
    private int gridHeight = 5;

    private int[][] grid;
    private List<Coordinate> lowPointsCoordinates;

    @GetMapping("day9/part1/{filename}")
    public int getSumOfRiskLevels(@PathVariable String filename) {
        setGrid(filename);
        setLowPoints();
        return lowPointsCoordinates.stream().map(Coordinate::getDepth).mapToInt(Integer::intValue).sum() + lowPointsCoordinates.size();
    }

    @GetMapping("day9/part2/{filename}")
    public int getFactorOfThreeLargestBasins(@PathVariable String filename) {
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
                grid[y][x] = Integer.parseInt(input.get(y).substring(x, x + 1));
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
                if (getAdjacentPoints(y, x).stream().allMatch(d -> d.depth > depth)) {
                    log.info("y {}, x {} has been counted as low point", y, x);
                    lowPointsCoordinates.add(new Coordinate(y, x, depth));
                }
            }
        }
        log.debug("Initialized lowPointsCoordinates {}", lowPointsCoordinates);
    }

    private List<Coordinate> getAdjacentPoints(int y, int x) {
        List<Coordinate> adjacentPoints = new ArrayList<>();
        if (x > 0) {
            adjacentPoints.add(new Coordinate(y, x - 1, grid[y][x - 1]));
        }
        if (x < gridWidth - 1) {
            adjacentPoints.add(new Coordinate(y, x + 1, grid[y][x + 1]));
        }
        if (y > 0) {
            adjacentPoints.add(new Coordinate(y - 1, x, grid[y - 1][x]));
        }
        if (y < gridHeight - 1) {
            adjacentPoints.add(new Coordinate(y + 1, x, grid[y + 1][x]));
        }
        log.debug("y {}, x {} has adjacent points {}", y, x, adjacentPoints);
        return adjacentPoints;
    }

    private int getBasinCalculation() {

        return 0;
    }

    @Data
    @AllArgsConstructor
    private static class Coordinate {
        int y;
        int x;
        int depth;
    }
}