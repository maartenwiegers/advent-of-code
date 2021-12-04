package nl.maartenwiegers.aoc2021;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day4Test {

    private final Day4 day4 = new Day4();

    @Test
    public void shouldFindCorrectAnswerForDay4Part1() {
        assertEquals(49686, day4.solveBingoGetFirstWin());
    }

    @Test
    public void shouldFindCorrectAnswerForDay4Part2() {
        assertEquals(26878, day4.solveBingoGetLastWin());
    }

}
