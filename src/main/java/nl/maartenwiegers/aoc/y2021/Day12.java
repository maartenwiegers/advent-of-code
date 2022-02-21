package nl.maartenwiegers.aoc.y2021;

import lombok.extern.slf4j.Slf4j;
import nl.maartenwiegers.aoc.commons.FileService;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@Slf4j
public class Day12 {

    private static final String FILE_NAME = "input/y2021/12-%s.txt";
    private static final String START = "start";
    private static final String END = "end";
    private Map<String, Cave> caveMap;

    public int getCountOfPaths(String filename, boolean allowVisitToSmallCaveTwice) {
        initializeCaveMap(filename);
        Cave start = caveMap.get(START);
        start.connections.forEach(cave -> cave.connections.removeIf(cave1 -> START.equals(cave1.name)));
        return getPaths(start, List.of(start), allowVisitToSmallCaveTwice, false);
    }

    private int getPaths(Cave startAt, List<Cave> currentPath, boolean allowVisitToSmallCaveTwice, boolean visitedSmallCaveTwice) {
        List<Cave> path = List.copyOf(currentPath);
        int countRoutes = 0;
        if (END.equals(startAt.name)) {
            return 1;
        } else {
            for (Cave adjacentCave : startAt.connections) {
                if (adjacentCave.large) {
                    List<Cave> newPath = new ArrayList<>(path);
                    newPath.add(adjacentCave);
                    countRoutes += getPaths(adjacentCave, newPath, allowVisitToSmallCaveTwice, visitedSmallCaveTwice);
                } else {
                    if (!currentPath.contains(adjacentCave)) {
                        List<Cave> newPath = new ArrayList<>(path);
                        newPath.add(adjacentCave);
                        countRoutes += getPaths(adjacentCave, newPath, allowVisitToSmallCaveTwice, visitedSmallCaveTwice);
                    } else {
                        if (allowVisitToSmallCaveTwice && !visitedSmallCaveTwice) {
                            List<Cave> newPath = new ArrayList<>(path);
                            newPath.add(adjacentCave);
                            countRoutes += getPaths(adjacentCave, newPath, true, true);
                        }
                    }
                }
            }
        }

        return countRoutes;
    }

    private void initializeCaveMap(String filename) {
        caveMap = new HashMap<>();
        List<String> inputList = FileService.getInputAsListString(String.format(FILE_NAME, filename));
        inputList.stream()
                .map(s -> s.split("-"))
                .forEach(strings -> {
                    Cave cave1 = caveMap.computeIfAbsent(strings[0], Cave::new);
                    Cave cave2 = caveMap.computeIfAbsent(strings[1], Cave::new);
                    cave1.connections.add(cave2);
                    cave2.connections.add(cave1);
                });
    }

    private static class Cave {
        String name;
        boolean large;
        Set<Cave> connections;

        public Cave(String name) {
            this.name = name;
            this.large = StringUtils.isAllUpperCase(name);
            this.connections = new HashSet<>();
        }

        @Override
        public int hashCode() {
            return Objects.hash(name);
        }
    }
}
