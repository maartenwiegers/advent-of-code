package nl.maartenwiegers.aoc.y2020;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day1Test {

    private final Day1 day1 = new Day1();

    @ParameterizedTest
    @MethodSource("getPart1Arguments")
    void shouldGetCorrectAnswerPart1(String filename, long expected) {
        assertEquals(expected, day1.getMultiplicationOf2NumbersSum2020(filename));
    }

    @ParameterizedTest
    @MethodSource("getPart2Arguments")
    void shouldGetCorrectAnswerPart2(String filename, long expected) {
        assertEquals(expected, day1.getMultiplicationOf3NumbersSum2020(filename));
    }

    private static Stream<Arguments> getPart1Arguments() {
        return Stream.of(Arguments.of("example", 514579), Arguments.of("puzzleinput", 158916));
    }

    private static Stream<Arguments> getPart2Arguments() {
        return Stream.of(Arguments.of("example", 241861950), Arguments.of("puzzleinput", 165795564));
    }
}
