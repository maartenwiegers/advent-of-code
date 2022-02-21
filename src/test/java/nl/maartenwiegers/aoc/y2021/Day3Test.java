package nl.maartenwiegers.aoc.y2021;

import nl.maartenwiegers.aoc.y2021.Day3;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day3Test {

    private final Day3 day3 = new Day3();

    @Test
    public void shouldFindCorrectAnswerForDay3Part1() {
        assertEquals(4174964, day3.getPowerConsumption());
    }

    @Test
    public void shouldFindCorrectAnswerForDay3Part2() {
        assertEquals(4474944, day3.getLifeSupportRating());
    }
}
