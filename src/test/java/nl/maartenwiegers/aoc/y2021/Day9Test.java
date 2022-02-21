package nl.maartenwiegers.aoc.y2021;

import nl.maartenwiegers.aoc.y2021.Day9;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day9Test {
    private final Day9 day9 = new Day9();

    private static Stream<Arguments> getPart1Arguments() {
        return Stream.of(
                Arguments.of("example", 15),
                Arguments.of("puzzleinput", 524)
        );
    }

    private static Stream<Arguments> getPart2Arguments() {
        return Stream.of(
                Arguments.of("example", 1134),
                Arguments.of("puzzleinput", 1235430)
        );
    }

    @ParameterizedTest
    @MethodSource("getPart1Arguments")
    public void shouldGetCorrectAnswersForDay9Part1(String filename, int expected){
        assertEquals(expected, day9.getSumOfRiskLevels(filename));
    }

    @ParameterizedTest
    @MethodSource("getPart2Arguments")
    public void shouldGetCorrectAnswersForDay9Part2(String filename, int expected){
        assertEquals(expected, day9.getFactorOfThreeLargestBasins(filename));
    }
}
