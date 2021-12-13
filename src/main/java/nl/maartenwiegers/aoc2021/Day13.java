package nl.maartenwiegers.aoc2021;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import nl.maartenwiegers.aoc2021.commons.FileService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class Day13 {

    private static final String FILE_NAME = "input/13-%s.txt";
    private List<FoldingInstruction> foldingInstructions;
    private HashSet<Point> points;

    @GetMapping("day13/{filename}/{numberOfFolds}")
    public long getCountOfVisibleDots(@PathVariable String filename, @PathVariable int numberOfFolds) {
        initializePoints(filename);
        initializeFoldingInstructions(filename);
        simulateFolding(numberOfFolds);
        return getCountOfVisibleDots();
    }

    @GetMapping("day13/{filename}/{numberOfFolds}/string")
    public String getOutputAsString(@PathVariable String filename, @PathVariable int numberOfFolds) {
        initializePoints(filename);
        initializeFoldingInstructions(filename);
        simulateFolding(numberOfFolds);
        return getOutputAsString();
    }

    private void initializePoints(String filename) {
        points = new HashSet<>();
        FileService.getInputAsListString(String.format(FILE_NAME, filename))
                .stream()
                .filter(line -> line.contains(","))
                .forEach(line -> {
                    int x = Integer.parseInt(line.split(",")[0]);
                    int y = Integer.parseInt(line.split(",")[1]);
                    points.add(new Point(y, x));
                });
        log.info("Initialized grid: {}", points);
    }

    private void initializeFoldingInstructions(String filename) {
        foldingInstructions = new ArrayList<>();
        FileService.getInputAsListString(String.format(FILE_NAME, filename))
                .stream()
                .filter(line -> line.contains("fold along"))
                .forEach(line -> foldingInstructions.add(new FoldingInstruction(line.charAt(11) == 'y' ? FoldDirection.HORIZONTAL : FoldDirection.VERTICAL, Integer.parseInt(line.substring(13)))));
        log.info("Initialized folding instructions: {}", foldingInstructions);
    }

    private void simulateFolding(int numberOfFolds) {
        foldingInstructions.stream()
                .limit(numberOfFolds)
                .forEach(this::simulateFold);
    }

    private void simulateFold(FoldingInstruction foldingInstruction) {
        if (foldingInstruction.direction.equals(FoldDirection.HORIZONTAL)) {
            simulateHorizontalFold(foldingInstruction);
        } else {
            simulateVerticalFold(foldingInstruction);
        }
    }

    private long getCountOfVisibleDots() {
        return points.size();
    }

    private void simulateHorizontalFold(FoldingInstruction foldingInstruction) {
        HashSet<Point> pointsToRemove = new HashSet<>();
        Set<Point> pointsToAdd = points.stream()
                .filter(point -> point.y > foldingInstruction.start)
                .peek(pointsToRemove::add)
                .map(point -> new Point(foldingInstruction.start - (point.y - foldingInstruction.start), point.x))
                .collect(Collectors.toSet());
        points.removeAll(pointsToRemove);
        points.addAll(pointsToAdd);
    }

    private void simulateVerticalFold(FoldingInstruction foldingInstruction) {
        HashSet<Point> pointsToRemove = new HashSet<>();
        Set<Point> pointsToAdd = points.stream()
                .filter(point -> point.x > foldingInstruction.start)
                .peek(pointsToRemove::add)
                .map(point -> new Point(point.y, foldingInstruction.start - (point.x - foldingInstruction.start)))
                .collect(Collectors.toSet());
        points.removeAll(pointsToRemove);
        points.addAll(pointsToAdd);
    }

    private String getOutputAsString() {
        StringBuilder output = new StringBuilder();
        output.append('\n');
        int maxY = points.stream()
                .mapToInt(Point::getY)
                .max()
                .orElse(0);
        int maxX = points.stream()
                .mapToInt(Point::getX)
                .max()
                .orElse(0);
        for (int y = 0; y <= maxY; y++) {
            for (int x = 0; x <= maxX; x++) {
                if (points.contains(new Point(y, x))) {
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

    private enum FoldDirection {
        HORIZONTAL, VERTICAL
    }

    @Data
    @AllArgsConstructor
    private static class FoldingInstruction {
        FoldDirection direction;
        int start;
    }

    @Data
    @AllArgsConstructor
    private static class Point {
        int y;
        int x;
    }
}
