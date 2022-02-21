package nl.maartenwiegers.aoc.y2021;

import nl.maartenwiegers.aoc.y2021.Day12;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day12Test {
    private final Day12 day12 = new Day12();

    @ParameterizedTest
    @MethodSource("getPart1Arguments")
    public void shouldGetCorrectAnswersForDay12Part1(String filename, boolean allowVisitToSmallCaveTwice, int expected) {
        assertEquals(expected, day12.getCountOfPaths(filename, allowVisitToSmallCaveTwice));
    }

    @ParameterizedTest
    @MethodSource("getPart2Arguments")
    public void shouldGetCorrectAnswersForDay12Part2(String filename, boolean allowVisitToSmallCaveTwice, int expected) {
        assertEquals(expected, day12.getCountOfPaths(filename, allowVisitToSmallCaveTwice));
    }

    private static Stream<Arguments> getPart1Arguments() {
        return Stream.of(Arguments.of("example1", false, 10), Arguments.of("example2", false, 19), Arguments.of("example3", false, 226), Arguments.of("puzzleinput", false, 4495));
    }

    private static Stream<Arguments> getPart2Arguments() {
        return Stream.of(Arguments.of("example1", true, 36), Arguments.of("example2", true, 103), Arguments.of("example3", true, 3509), Arguments.of("puzzleinput", true, 131254));
    }
}
