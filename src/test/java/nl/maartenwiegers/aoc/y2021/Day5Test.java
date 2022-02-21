package nl.maartenwiegers.aoc.y2021;

import nl.maartenwiegers.aoc.y2021.Day5;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day5Test {

    private final Day5 day5 = new Day5();

    @Test
    public void shouldFindCorrectAnswerForDay5Part1() {
        assertEquals(5294, day5.getCountIntersectionsWithoutVertical(1000));
    }

    @Test
    public void shouldFindCorrectAnswerForDay5Part2() {
        assertEquals(21812, day5.getCountIntersectionsWithVertical(1000));
    }
}
