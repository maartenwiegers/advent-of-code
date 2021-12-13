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
public class Day13 {

    private static final String FILE_NAME = "input/13-%s.txt";
    private int gridSize = 15;
    private char[][] grid;
    private List<FoldingInstruction> foldingInstructions;

    @GetMapping("day13/part1/{filename}/{gridSize}/{numberOfFolds}")
    public int getCountOfVisibleDots(@PathVariable String filename, @PathVariable int gridSize, @PathVariable int numberOfFolds) {
        this.gridSize = gridSize;
        initializeGrid(filename);
        initializeFoldingInstructions(filename);
        simulateFolding(numberOfFolds);
        return getCountOfVisibleDots();
    }

    private void initializeGrid(String filename) {
        grid = new char[gridSize][gridSize];
        FileService.getInputAsListString(String.format(FILE_NAME, filename))
                .stream()
                .filter(line -> line.contains(","))
                .forEach(line -> {
                    int x = Integer.parseInt(line.split(",")[0]);
                    int y = Integer.parseInt(line.split(",")[1]);
                    grid[y][x] = '#';
                });
        log.info("Initialized grid: {}", Arrays.deepToString(grid));
    }

    private void initializeFoldingInstructions(String filename) {
        foldingInstructions = new ArrayList<>();
        FileService.getInputAsListString(String.format(FILE_NAME, filename))
                .stream()
                .filter(line -> line.contains("fold along"))
                .map(line -> line.replace("fold along ", "")
                        .replace("=", ""))
                .forEach(line -> foldingInstructions.add(new FoldingInstruction(line.charAt(0), Integer.parseInt(line.substring(1)))));
        log.info("Initialized folding instructions: {}", foldingInstructions);
    }

    private void simulateFolding(int numberOfFolds) {
        foldingInstructions.stream()
                .limit(numberOfFolds)
                .forEach(this::simulateFold);
    }

    private void simulateFold(FoldingInstruction foldingInstruction) {
        if (foldingInstruction.direction == 'y') {
            simulateHorizontalFold(foldingInstruction);
        } else {
            simulateVerticalFold(foldingInstruction);
        }
    }

    private int getCountOfVisibleDots() {
        StringBuilder output = new StringBuilder();
        int countDots = 0;
        for (int y = 0; y < gridSize; y++) {
            for (int x = 0; x < gridSize; x++) {
                output.append(grid[y][x]);
                if (grid[y][x] == '#') {
                    countDots++;
                }
            }
            output.append('\n');
        }
        log.info(output.toString());
        return countDots;
    }

    private void simulateHorizontalFold(FoldingInstruction foldingInstruction) {
        for (int y = foldingInstruction.start + 1; y < gridSize; y++) {
            for (int x = 0; x < gridSize; x++) {
                if (grid[y][x] == '#') {
                    int newY = gridSize - y - 1;
                    grid[newY][x] = '#';
                    grid[y][x] = '.';
                }
            }
        }
    }

    private void simulateVerticalFold(FoldingInstruction foldingInstruction) {
        for (int x = foldingInstruction.start + 1; x < gridSize; x++) {
            for(int y = 0; y < gridSize; y++) {
                if(grid[y][x] == '#') {
                    int newX = gridSize - x - 1;
                    grid[y][newX] = '#';
                    grid[y][x] = '.';
                }
            }
        }
    }

    @Data
    @AllArgsConstructor
    private static class FoldingInstruction {
        char direction;
        int start;
    }
}
