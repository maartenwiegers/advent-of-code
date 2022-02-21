package nl.maartenwiegers.aoc.y2021;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day15Test {

    private final Day15 day15 = new Day15();

    @ParameterizedTest
    @MethodSource("getPart1Arguments")
    public void shouldGetCorrectAnswerPart1(String filename, int gridSize, long expected) {
        assertEquals(expected, day15.getRiskOfShortestPath(filename, gridSize));
    }

    @ParameterizedTest
    @MethodSource("getPart2Arguments")
    public void shouldGetCorrectAnswerPart2(String filename, int gridSize, long expected) {
        assertEquals(expected, day15.getRiskOfShortestPathOnFullGrid(filename, gridSize));
    }

    private static Stream<Arguments> getPart1Arguments() {
        return Stream.of(Arguments.of("example", 10, 40L), Arguments.of("puzzleinput", 100, 441L));
    }

    private static Stream<Arguments> getPart2Arguments() {
        return Stream.of(Arguments.of("example", 50, 315L), Arguments.of("puzzleinput", 500, 2849L));
    }
}
