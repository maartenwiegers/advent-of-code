package nl.maartenwiegers.aoc.y2020;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day3Test {

    private static final List<Integer> PART_1_HORIZONTAL_STEPS = List.of(3);
    private static final List<Integer> PART_1_VERTICAL_STEPS = List.of(1);
    private static final List<Integer> PART_2_HORIZONTAL_STEPS = List.of(1, 3, 5, 7, 1);
    private static final List<Integer> PART_2_VERTICAL_STEPS = List.of(1, 1, 1, 1, 2);
    private final Day3 day3 = new Day3();

    @ParameterizedTest
    @MethodSource("getArguments")
    void shouldGetCorrectAnswers(String filename, List<Integer> moveHorizontal, List<Integer> moveVertical,
                                 long expected) {
        assertEquals(expected, day3.getMultiplicationOfTreeEncounters(filename, moveHorizontal, moveVertical));
    }

    private static Stream<Arguments> getArguments() {
        return Stream.of(Arguments.of("example", PART_1_HORIZONTAL_STEPS, PART_1_VERTICAL_STEPS, 7),
                         Arguments.of("puzzleinput", PART_1_HORIZONTAL_STEPS, PART_1_VERTICAL_STEPS, 216),
                         Arguments.of("example", PART_2_HORIZONTAL_STEPS, PART_2_VERTICAL_STEPS, 336L),
                         Arguments.of("puzzleinput", PART_2_HORIZONTAL_STEPS, PART_2_VERTICAL_STEPS, 6708199680L));
    }
}
