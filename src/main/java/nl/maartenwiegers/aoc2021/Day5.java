package nl.maartenwiegers.aoc2021;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import nl.maartenwiegers.aoc2021.commons.FileService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@Slf4j
public class Day5 {

    private static final String FILE_NAME = "input/05.txt";
    private static final int GRID_SIZE = 1000;
    private final List<Line> lines = new ArrayList<>();
    private int[][] grid;

    @GetMapping("day5/part1")
    public int getCountIntersectionsWithoutVertical() {
        setLines(true);
        drawLinesOnGrid();
        return getCountIntersections();
    }

    @GetMapping("day5/part2")
    public int getCountIntersectionsWithVertical() {
        setLines(false);
        drawLinesOnGrid();
        return getCountIntersections();
    }

    private void setLines(boolean ignoreDiagonals) {
        lines.clear();
        FileService.getInputAsListString(FILE_NAME).forEach(input -> {
            String start = StringUtils.split(input, "->")[0].trim();
            String end = StringUtils.split(input, "->")[1].trim();
            lines.add(new Line(start.split(",")[0], start.split(",")[1], end.split(",")[0], end.split(",")[1]));
        });

        if (ignoreDiagonals) {
            lines.removeIf(line -> Direction.DIAGONAL.equals(line.getDirection()));
        }
        log.info("Created {} lines: {}", lines.size(), lines);
    }

    private void drawLinesOnGrid() {
        grid = new int[GRID_SIZE][GRID_SIZE];
        lines.forEach(this::drawLine);
    }

    private void drawLine(Line line) {
        switch (line.getDirection()) {
            case HORIZONTAL -> drawHorizontalLine(line);
            case VERTICAL -> drawVerticalLine(line);
            case DIAGONAL -> drawDiagonalLine(line);
            default -> {
            }
        }
    }

    private void drawHorizontalLine(Line line) {
        int start = Math.min(line.xStart, line.xEnd);
        int end = Math.max(line.xStart, line.xEnd);
        for (int i = start; i <= end; i++) {
            grid[line.yStart][i]++;
        }
    }

    private void drawVerticalLine(Line line) {
        int start = Math.min(line.yStart, line.yEnd);
        int end = Math.max(line.yStart, line.yEnd);
        for (int i = start; i <= end; i++) {
            grid[i][line.xStart]++;
        }
    }

    private void drawDiagonalLine(Line line) {
        log.info("Drawing diagonal line {}", line);
        int x = line.xStart;
        int y = line.yStart;
        int deltaX = x < line.xEnd ? 1 : -1;
        int deltaY = y < line.yEnd ? 1 : -1;

        while(x != line.xEnd){
            grid[y][x]++;
            x += deltaX;
            y += deltaY;
        }
        grid[y][x]++;
    }

    private int getCountIntersections() {
        for (int i = 0; i < GRID_SIZE; i++) {
            log.info(Arrays.toString(grid[i]).replace("0", ".").replace(", ", ""));
        }
        return Arrays.stream(grid).mapToInt(row -> (int) Arrays.stream(row).filter(count -> count > 1).count()).sum();
    }

    public enum Direction {
        HORIZONTAL,
        VERTICAL,
        DIAGONAL
    }

    @Data
    @AllArgsConstructor
    public static class Line {
        int xStart;
        int yStart;
        int xEnd;
        int yEnd;

        public Line(String... coordinates) {
            xStart = Integer.parseInt(coordinates[0]);
            yStart = Integer.parseInt(coordinates[1]);
            xEnd = Integer.parseInt(coordinates[2]);
            yEnd = Integer.parseInt(coordinates[3]);
        }

        public Direction getDirection() {
            if (xStart == xEnd && yStart != yEnd) {
                return Direction.VERTICAL;
            } else if (yStart == yEnd && xStart != xEnd) {
                return Direction.HORIZONTAL;
            } else if (Math.abs(xEnd - xStart) == Math.abs(yEnd - yStart)) {
                return Direction.DIAGONAL;
            } else {
                return null;
            }
        }
    }
}