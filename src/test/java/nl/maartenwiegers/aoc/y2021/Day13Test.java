package nl.maartenwiegers.aoc.y2021;

import nl.maartenwiegers.aoc.y2021.Day13;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day13Test {

    private final Day13 day13 = new Day13();

    @ParameterizedTest
    @MethodSource("getPart1Arguments")
    public void shouldGetCorrectAnswersForDay13Part1(String filename, int numberOfFolds, int expected) {
        assertEquals(expected, day13.getCountOfVisibleDots(filename, numberOfFolds));
    }

    @ParameterizedTest
    @MethodSource("getPart2Arguments")
    public void shouldGetCorrectAnswersForDay13Part2(String filename, int numberOfFolds, int expected) {
        assertEquals(expected, day13.getCountOfVisibleDots(filename, numberOfFolds));
    }

    @Test
    public void shouldGetStringOutput() {
        String expected = """
                #  # ####   ## #  #   ## ###   ##    ##
                #  # #       # #  #    # #  # #  #    #
                #### ###     # ####    # #  # #       #
                #  # #       # #  #    # ###  #       #
                #  # #    #  # #  # #  # # #  #  # #  #
                #  # ####  ##  #  #  ##  #  #  ##   ##
                """;
        assertEquals(expected.trim(), day13.getOutputAsString("puzzleinput", 12).trim());
    }

    private static Stream<Arguments> getPart1Arguments() {
        return Stream.of(Arguments.of("example", 1, 17), Arguments.of("example", 2, 16), Arguments.of("puzzleinput", 1, 647));
    }

    private static Stream<Arguments> getPart2Arguments() {
        return Stream.of(Arguments.of("example", 2, 16), Arguments.of("puzzleinput", 1, 647), Arguments.of("puzzleinput", 12, 93));
    }
}
