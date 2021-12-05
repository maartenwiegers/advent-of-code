package nl.maartenwiegers.aoc2021;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day5Test {

    private final Day5 day5 = new Day5();

    @Test
    public void shouldFindCorrectAnswerForDay5Part1() {
        assertEquals(5294, day5.getCountIntersectionsWithoutVertical());
    }
}
