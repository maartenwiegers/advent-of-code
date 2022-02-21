package nl.maartenwiegers.aoc.y2021;

import nl.maartenwiegers.aoc.y2021.Day2;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day2Test {

    private final Day2 day2 = new Day2();

    @Test
    public void shouldFindCorrectAnswerForDay2Part1() {
        assertEquals(1938402, day2.getMultiplicationOfFinalHorizontalPositionAndDepth());
    }

    @Test
    public void shouldFindCorrectAnswerForDay2Part2() {
        assertEquals(1947878632, day2.getMultiplicationOfFinalHorizontalPositionAndDepthCalculatedWithAim());
    }
}
