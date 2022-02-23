package nl.maartenwiegers.aoc.y2020;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day6Test {

    private final Day6 day6 = new Day6();

    @ParameterizedTest
    @MethodSource("getArguments")
    void shouldGetCorrectAnswers(long expected, String filename) {
        assertEquals(expected, day6.getSumOfCountOfAnswers(filename));
    }

    private static Stream<Arguments> getArguments() {
        return Stream.of(Arguments.of(11, "example"), Arguments.of(6686, "puzzleinput"));
    }
}
