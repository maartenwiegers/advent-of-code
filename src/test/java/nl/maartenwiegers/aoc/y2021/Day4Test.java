package nl.maartenwiegers.aoc.y2021;

import nl.maartenwiegers.aoc.y2021.Day4;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day4Test {

    private final Day4 day4 = new Day4();

    @Test
    public void shouldFindCorrectAnswerForDay4Part1() {
        assertEquals(49686, day4.solveBingoGetFirstWin(5));
    }

    @Test
    public void shouldFindCorrectAnswerForDay4Part2() {
        assertEquals(26878, day4.solveBingoGetLastWin(5));
    }

}
