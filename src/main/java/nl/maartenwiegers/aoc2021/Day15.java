package nl.maartenwiegers.aoc2021;

import lombok.extern.slf4j.Slf4j;
import nl.maartenwiegers.aoc2021.commons.Coordinate;
import nl.maartenwiegers.aoc2021.commons.FileService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class Day15 {

    private static final String FILE_NAME = "input/15-%s.txt";
    private final Map<Coordinate, Long> risksToCoordinates = new HashMap<>();
    private final Map<Coordinate, Integer> riskAtCoordinate = new HashMap<>();

    @GetMapping("day15/part1/{filename}/{gridSize}")
    public long getRiskOfShortestPath(@PathVariable String filename, @PathVariable int gridSize) {
        initialize(filename);
        Coordinate startAt = new Coordinate(0, 0);
        risksToCoordinates.put(startAt, 0L);
        Coordinate destination = new Coordinate(gridSize - 1, gridSize - 1);
        calculateShortestPath(startAt);
        return risksToCoordinates.get(destination);
    }

    private void initialize(String filename) {
        risksToCoordinates.clear();
        List<String> input = FileService.getInputAsListString(String.format(FILE_NAME, filename));
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
