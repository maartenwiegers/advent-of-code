package nl.maartenwiegers.aoc.y2021;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day8Test {

    private final Day8 day8 = new Day8();

    private static Stream<Arguments> getPart1Arguments() {
        return Stream.of(
                Arguments.of("example", 26),
                Arguments.of("puzzleinput", 416)
        );
    }

    private static Stream<Arguments> getPart2Arguments() {
        return Stream.of(
                Arguments.of("example", 61229),
                Arguments.of("puzzleinput", 1043697)
        );
    }

    @ParameterizedTest
    @MethodSource("getPart1Arguments")
    public void shouldGetCorrectAnswerForDay8Part1(String dataset, int expected) {
        assertEquals(expected, day8.getCountUsagesOfDigits(dataset));
    }

    @ParameterizedTest
    @MethodSource("getPart2Arguments")
    public void shouldGetCorrectAnswerForDay8Part2(String dataset, int expected) {
        assertEquals(expected, day8.getSumOfOutputs(dataset));
    }

}
