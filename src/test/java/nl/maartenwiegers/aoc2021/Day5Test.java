package nl.maartenwiegers.aoc2021;

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

    @Test
    public void shouldFindCorrectAnswerForDay5Part1GridSize6400() {
        assertEquals(6530681, day5.getCountIntersectionsWithoutVertical(6400));
    }

    @Test
    public void shouldFindCorrectAnswerForDay5Part2GridSize6400() {
        assertEquals(9039258, day5.getCountIntersectionsWithVertical(6400));
    }

    @Test
    public void shouldFindCorrectAnswerForDay5Part1GridSize10000() {
        assertEquals(30405812, day5.getCountIntersectionsWithoutVertical(10000));
    }

    @Test
    public void shouldFindCorrectAnswerForDay5Part2GridSize10000() {
        assertEquals(39512010, day5.getCountIntersectionsWithVertical(10000));
    }
}
