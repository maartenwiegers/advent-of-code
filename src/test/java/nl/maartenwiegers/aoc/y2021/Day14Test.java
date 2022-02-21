package nl.maartenwiegers.aoc.y2021;

import nl.maartenwiegers.aoc.y2021.Day14;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day14Test {

    private final Day14 day14 = new Day14();

    @ParameterizedTest
    @MethodSource("getScoreArguments")
    public void shouldGetCorrectScore(String filename, int numberOfSteps, long expected) {
        assertEquals(expected, day14.getScore(filename, numberOfSteps));
    }

    private static Stream<Arguments> getScoreArguments() {
        return Stream.of(Arguments.of("example", 10, 1588), Arguments.of("puzzleinput", 10, 2740), Arguments.of("example", 40, 2188189693529L), Arguments.of("puzzleinput", 40, 2959788056211L));
    }
}
