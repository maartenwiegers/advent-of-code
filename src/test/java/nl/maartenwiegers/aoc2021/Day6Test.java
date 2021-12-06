package nl.maartenwiegers.aoc2021;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day6Test {

    private final Day6 day6 = new Day6();

    @Test
    public void shouldFindCorrectAnswerForDay6Part1() {
        assertEquals(386536, day6.getCountOfFishAfterDays(80));
    }

    @Test
    public void shouldFindCorrectAnswerForDay6Part2() {
        assertEquals(386536, day6.getCountOfFishAfterDays(256));
    }
}
