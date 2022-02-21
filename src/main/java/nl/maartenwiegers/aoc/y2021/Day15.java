package nl.maartenwiegers.aoc.y2021;

import lombok.extern.slf4j.Slf4j;
import nl.maartenwiegers.aoc.commons.Coordinate;
import nl.maartenwiegers.aoc.commons.FileService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class Day15 {

    private static final String FILE_NAME = "input/y2021/15-%s.txt";
    private final Map<Coordinate, Long> risksToCoordinates = new HashMap<>();
    private final Map<Coordinate, Integer> riskAtCoordinate = new HashMap<>();

    public long getRiskOfShortestPath(String filename, int gridSize) {
        initialize(filename);
        calculate();
        return risksToCoordinates.get(new Coordinate(gridSize - 1, gridSize - 1));
    }

    public long getRiskOfShortestPathOnFullGrid(String filename, int gridSize) {
        initializeFullGrid(filename);
        calculate();
        return risksToCoordinates.get(new Coordinate(gridSize - 1, gridSize - 1));
    }

    private void initialize(String filename) {
        risksToCoordinates.clear();
        List<String> input = FileService.getInputAsListString(String.format(FILE_NAME, filename));
        setRisksToCoordinates(input);
    }

    private void initializeFullGrid(String filename) {
        risksToCoordinates.clear();
        List<String> input = FileService.getInputAsListString(String.format(FILE_NAME, filename));
        List<String> newLines = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            for (String line : input) {
                int finalI = i;
                newLines.add(line.chars()
                        .map(c -> {
                            int n = c + finalI;
                            if (n > '9') {
                                n -= 9;
                            }
                            return n;
                        })
                        .mapToObj(Character::toString)
                        .collect(Collectors.joining()));
            }
        }
        List<String> newInput = new ArrayList<>();
        for (String line : newLines) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 5; i++) {
                int finalI = i;
                sb.append(line.chars()
                        .map(c -> {
                            int n = c + finalI;
                            if (n > '9') {
                                n -= 9;
                            }
                            return n;
                        })
                        .mapToObj(Character::toString)
                        .collect(Collectors.joining()));
            }
            newInput.add(sb.toString());
        }
        setRisksToCoordinates(newInput);
    }

    private void setRisksToCoordinates(List<String> input) {
        for (int y = 0; y < input.size(); y++) {
            String line = input.get(y);
            for (int x = 0; x < line.length(); x++) {
                int risk = line.charAt(x) - 48;
                Coordinate coordinate = new Coordinate(y, x);
                risksToCoordinates.put(coordinate, Long.MAX_VALUE);
                riskAtCoordinate.put(coordinate, risk);
            }
        }
        log.info("Initialized risksToCoordinates: {}", risksToCoordinates);
        log.info("Initialized riskAtCoordinate: {}", riskAtCoordinate);
    }

    private void calculate() {
        Coordinate startAt = new Coordinate(0, 0);
        risksToCoordinates.put(startAt, 0L);
        calculateShortestPath(startAt);
    }

    private void calculateShortestPath(Coordinate start) {
        Set<Coordinate> todoSet = new HashSet<>();
        Set<Coordinate> unvisited = new HashSet<>(risksToCoordinates.keySet());

        Queue<Coordinate> todo = new PriorityQueue<>(Comparator.comparingLong(risksToCoordinates::get));
        todo.add(start);

        while (!todo.isEmpty()) {
            Coordinate current = todo.poll();
            todoSet.remove(current);
            Set<Coordinate> adjacent = current.getHorizontalVerticalAdjacent()
                    .stream()
                    .filter(unvisited::contains)
                    .collect(Collectors.toSet());
            for (Coordinate c : adjacent) {
                long currentRisk = risksToCoordinates.get(c);
                long newRisk = risksToCoordinates.get(current) + riskAtCoordinate.get(c);

                if (newRisk < currentRisk) {
                    risksToCoordinates.put(c, newRisk);
                }
            }
            adjacent.stream()
                    .filter(todoSet::add)
                    .forEach(todo::offer);
            unvisited.remove(current);
        }
    }
}
