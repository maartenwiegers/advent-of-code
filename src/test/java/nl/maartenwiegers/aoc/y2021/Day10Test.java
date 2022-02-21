package nl.maartenwiegers.aoc.y2021;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day10Test {

    private final Day10 day10 = new Day10();

    private static Stream<Arguments> getPart1Arguments() {
        return Stream.of(Arguments.of("example", 26397), Arguments.of("puzzleinput", 168417));
    }

    private static Stream<Arguments> getPart2Arguments() {
        return Stream.of(Arguments.of("example", 288957L), Arguments.of("puzzleinput", 2802519786L));
    }

    @ParameterizedTest
    @MethodSource("getPart1Arguments")
    public void shouldGetCorrectAnswersForDay10Part1(String filename, int expected) {
        assertEquals(expected, day10.getScore(filename));
    }

    @ParameterizedTest
    @MethodSource("getPart2Arguments")
    public void shouldGetCorrectAnswersForDay10Part2(String filename, long expected) {
        assertEquals(expected, day10.getAutocompleteScore(filename));
    }
}
