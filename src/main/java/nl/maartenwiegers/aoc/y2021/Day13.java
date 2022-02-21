package nl.maartenwiegers.aoc.y2021;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import nl.maartenwiegers.aoc.commons.Coordinate;
import nl.maartenwiegers.aoc.commons.Direction;
import nl.maartenwiegers.aoc.commons.FileService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class Day13 {

    private static final String FILE_NAME = "input/y2021/13-%s.txt";
    private List<FoldingInstruction> foldingInstructions;
    private HashSet<Coordinate> points;

    public long getCountOfVisibleDots(String filename, int numberOfFolds) {
        initializeCoordinates(filename);
        initializeFoldingInstructions(filename);
        simulateFolding(numberOfFolds);
        return getCountOfVisibleDots();
    }

    public String getOutputAsString(String filename, int numberOfFolds) {
        initializeCoordinates(filename);
        initializeFoldingInstructions(filename);
        simulateFolding(numberOfFolds);
        return getOutputAsString();
    }

    private void initializeCoordinates(String filename) {
        points = new HashSet<>();
        FileService.getInputAsListString(String.format(FILE_NAME, filename))
                .stream()
                .filter(line -> line.contains(","))
                .forEach(line -> {
                    int x = Integer.parseInt(line.split(",")[0]);
                    int y = Integer.parseInt(line.split(",")[1]);
                    points.add(new Coordinate(y, x));
                });
        log.info("Initialized grid: {}", points);
    }

    private void initializeFoldingInstructions(String filename) {
        foldingInstructions = new ArrayList<>();
        FileService.getInputAsListString(String.format(FILE_NAME, filename))
                .stream()
                .filter(line -> line.contains("fold along"))
                .forEach(line -> foldingInstructions.add(new FoldingInstruction(line.charAt(11) == 'y' ? Direction.HORIZONTAL : Direction.VERTICAL, Integer.parseInt(line.substring(13)))));
        log.info("Initialized folding instructions: {}", foldingInstructions);
    }

    private void simulateFolding(int numberOfFolds) {
        foldingInstructions.stream()
                .limit(numberOfFolds)
                .forEach(this::simulateFold);
    }

    private void simulateFold(FoldingInstruction foldingInstruction) {
        if (foldingInstruction.direction.equals(Direction.HORIZONTAL)) {
            simulateHorizontalFold(foldingInstruction);
        } else {
            simulateVerticalFold(foldingInstruction);
        }
    }

    private long getCountOfVisibleDots() {
        return points.size();
    }

    private void simulateHorizontalFold(FoldingInstruction foldingInstruction) {
        HashSet<Coordinate> pointsToRemove = new HashSet<>();
        Set<Coordinate> pointsToAdd = points.stream()
                .filter(point -> point.getY() > foldingInstruction.start)
                .peek(pointsToRemove::add)
                .map(point -> new Coordinate(foldingInstruction.start - (point.getY() - foldingInstruction.start), point.getX()))
                .collect(Collectors.toSet());
        points.removeAll(pointsToRemove);
        points.addAll(pointsToAdd);
    }

    private void simulateVerticalFold(FoldingInstruction foldingInstruction) {
        HashSet<Coordinate> pointsToRemove = new HashSet<>();
        Set<Coordinate> pointsToAdd = points.stream()
                .filter(point -> point.getX() > foldingInstruction.start)
                .peek(pointsToRemove::add)
                .map(point -> new Coordinate(point.getY(), foldingInstruction.start - (point.getX() - foldingInstruction.start)))
                .collect(Collectors.toSet());
        points.removeAll(pointsToRemove);
        points.addAll(pointsToAdd);
    }

    private String getOutputAsString() {
        StringBuilder output = new StringBuilder();
        output.append('\n');
        int maxY = points.stream()
                .mapToInt(Coordinate::getY)
                .max()
                .orElse(0);
        int maxX = points.stream()
                .mapToInt(Coordinate::getX)
                .max()
                .orElse(0);
        for (int y = 0; y <= maxY; y++) {
            for (int x = 0; x <= maxX; x++) {
                if (points.contains(new Coordinate(y, x))) {
                    output.append("#");
                } else {
                    output.append(" ");
                }
            }
            output.append('\n');
        }
        log.info(output.toString());
        return output.toString();
    }

    @Data
    @AllArgsConstructor
    private static class FoldingInstruction {
        Direction direction;
        int start;
    }
}
