package nl.maartenwiegers.aoc.y2020;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day5Test {

    private final Day5 day5 = new Day5();

    @ParameterizedTest
    @MethodSource("getArguments")
    void shouldGetCorrectAnswers(long expected, String filename) {
        assertEquals(expected, day5.getHighestSeatId(filename));
    }

    @Test
    void shouldFindMySeatId() {
        assertEquals(559, day5.getMySeatId("puzzleinput"));
    }

    private static Stream<Arguments> getArguments() {
        return Stream.of(Arguments.of(820, "example"), Arguments.of(818, "puzzleinput"));
    }
}
