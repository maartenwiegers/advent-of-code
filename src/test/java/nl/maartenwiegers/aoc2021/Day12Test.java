package nl.maartenwiegers.aoc2021;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day12Test {
    private final Day12 day12 = new Day12();

    @ParameterizedTest
    @MethodSource("getPart1Arguments")
    public void shouldGetCorrectAnswersForDay12Part1(String filename, int expected) {
        assertEquals(expected, day12.getCountOfPaths(filename));
    }

    @ParameterizedTest
    @MethodSource("getPart2Arguments")
    public void shouldGetCorrectAnswersForDay12Part2(String filename, int expected) {
        assertEquals(expected, day12.getCountOfPathsVisitSmallTwice(filename));
    }

    private static Stream<Arguments> getPart1Arguments() {
        return Stream.of(Arguments.of("example1", 10), Arguments.of("example2", 19), Arguments.of("example3", 226), Arguments.of("puzzleinput", 4495));
    }

    private static Stream<Arguments> getPart2Arguments() {
        return Stream.of(Arguments.of("example1", 36), Arguments.of("example2", 103), Arguments.of("example3", 3509), Arguments.of("puzzleinput", 131254));
    }
}
