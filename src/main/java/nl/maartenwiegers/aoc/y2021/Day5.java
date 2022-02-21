package nl.maartenwiegers.aoc.y2021;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import nl.maartenwiegers.aoc.commons.Direction;
import nl.maartenwiegers.aoc.commons.FileService;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class Day5 {

    private static final String FILE_NAME = "input/y2021/05-%s.txt";
    private final List<Line> lines = new ArrayList<>();
    private int gridSize = 1000;
    private int[][] grid;

    public int getCountIntersectionsWithoutVertical(int gridSize) {
        this.gridSize = gridSize;
        setLines(true);
        drawLinesOnGrid();
        return getCountIntersections();
    }

    public int getCountIntersectionsWithVertical(int gridSize) {
        this.gridSize = gridSize;
        setLines(false);
        drawLinesOnGrid();
        return getCountIntersections();
    }

    private void setLines(boolean ignoreDiagonals) {
        lines.clear();
        FileService.getInputAsListString(String.format(FILE_NAME, gridSize))
                .forEach(input -> {
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
        grid = new int[gridSize][gridSize];
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
        int x = line.xStart;
        int y = line.yStart;
        int deltaX = x < line.xEnd ? 1 : -1;
        int deltaY = y < line.yEnd ? 1 : -1;

        grid[y][x]++;
        while (x != line.xEnd) {
            grid[y][x]++;
            x += deltaX;
            y += deltaY;
        }
    }

    private int getCountIntersections() {
        return Arrays.stream(grid)
                .mapToInt(row -> (int) Arrays.stream(row)
                        .filter(count -> count > 1)
                        .count())
                .sum();
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