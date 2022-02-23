package nl.maartenwiegers.aoc.y2020;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day4Test {

    private final Day4 day4 = new Day4();

    @ParameterizedTest
    @MethodSource("getArguments")
    void shouldGetCorrectAnswers(String filename, long expected, boolean isPart2) {
        assertEquals(expected, day4.getCountValidPassports(filename, isPart2));
    }

    private static Stream<Arguments> getArguments() {
        return Stream.of(Arguments.of("example", 2, false), Arguments.of("puzzleinput", 250, false),
                         Arguments.of("puzzleinput", 158, true));
    }
}
