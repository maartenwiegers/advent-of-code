package nl.maartenwiegers.aoc2021;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day7Test {

    Day7 day7 = new Day7();

    @Test
    public void shouldFindCorrectAnswerForDay7Part1() {
        assertEquals(343605, day7.getFuelConsumption(true));
    }

    @Test
    public void shouldFindCorrectAnswerForDay7Part2() {
        assertEquals(96744904, day7.getFuelConsumption(false));
    }
}
