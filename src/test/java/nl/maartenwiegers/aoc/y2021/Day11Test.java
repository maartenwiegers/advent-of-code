package nl.maartenwiegers.aoc.y2021;

import nl.maartenwiegers.aoc.y2021.Day11;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day11Test {

    private final Day11 day11 = new Day11();

    @ParameterizedTest
    @MethodSource("getPart1Arguments")
    public void shouldGetCorrectAnswersForDay11Part1(String filename, int afterSteps, int expected) {
        assertEquals(expected, day11.getCountOfFlashes(filename, afterSteps));
    }

    @ParameterizedTest
    @MethodSource("getPart2Arguments")
    public void shouldGetCorrectAnswersForDay11Part2(String filename, int expected) {
        assertEquals(expected, day11.getWhenAllOctopiFlashSimultaneously(filename));
    }

    private static Stream<Arguments> getPart1Arguments() {
        return Stream.of(Arguments.of("example", 1, 0), Arguments.of("example", 2, 35), Arguments.of("example", 10, 204), Arguments.of("example", 100, 1656), Arguments.of("puzzleinput", 100, 1588));
    }

    private static Stream<Arguments> getPart2Arguments() {
        return Stream.of(Arguments.of("example", 195), Arguments.of("puzzleinput", 517));
    }
}
