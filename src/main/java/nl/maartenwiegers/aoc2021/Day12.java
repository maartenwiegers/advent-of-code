package nl.maartenwiegers.aoc2021;

import lombok.extern.slf4j.Slf4j;
import nl.maartenwiegers.aoc2021.commons.FileService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@RestController
@Slf4j
public class Day12 {

    private static final String FILE_NAME = "input/12-%s.txt";
    private Map<String, Cave> caveMap;

    @GetMapping("day12/{filename}/{allowVisitToSmallCaveTwice}")
    public int getCountOfPaths(@PathVariable String filename, @PathVariable boolean allowVisitToSmallCaveTwice) {
        initializeCaveMap(filename);
        Cave start = caveMap.get("start");
        start.connections.forEach(cave -> cave.connections.removeIf(cave1 -> "start".equals(cave1.name)));
        return getPaths(start, List.of(start), allowVisitToSmallCaveTwice, false);
    }

    private int getPaths(Cave startAt, List<Cave> currentPath, boolean allowVisitToSmallCaveTwice, boolean visitedSmallCaveTwice) {
        List<Cave> path = List.copyOf(currentPath);
        int countRoutes = 0;
        if ("end".equals(startAt.name)) {
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
