package nl.maartenwiegers.aoc.y2021;

import nl.maartenwiegers.aoc.y2021.Day1;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day1Test {

    private final Day1 day1 = new Day1();

    @Test
    public void shouldFindCorrectAnswerForDay1Part1() {
        assertEquals(1390, day1.getCountMeasurementsHigherThanPrevious());
    }

    @Test
    public void shouldFindCorrectAnswerForDay1Part2() {
        assertEquals(1457, day1.getCountSumsHigherThanPrevious());
    }
}
