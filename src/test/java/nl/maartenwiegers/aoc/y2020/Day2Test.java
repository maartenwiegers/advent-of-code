package nl.maartenwiegers.aoc.y2020;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day2Test {

    private final Day2 day2 = new Day2();

    @ParameterizedTest
    @MethodSource("getPart1Arguments")
    void shouldGetCorrectAnswerPart1(String filename, long expected) {
        assertEquals(expected, day2.getCountOfValidPasswords(1, filename));
    }

    @ParameterizedTest
    @MethodSource("getPart2Arguments")
    void shouldGetCorrectAnswerPart2(String filename, long expected) {
        assertEquals(expected, day2.getCountOfValidPasswords(2, filename));
    }

    private static Stream<Arguments> getPart1Arguments() {
        return Stream.of(Arguments.of("example", 2), Arguments.of("puzzleinput", 660));
    }

    private static Stream<Arguments> getPart2Arguments() {
        return Stream.of(Arguments.of("example", 1), Arguments.of("puzzleinput", 530));
    }
}
