package nl.maartenwiegers.aoc2021;

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

    // Large datasets from https://the-tk.com/project/aoc2021-bigboys.html

    @Test
    public void shouldFindCorrectAnswerForDay4Part1WithGrid15() {
        assertEquals(22010880, day4.solveBingoGetFirstWin(15));
    }

    @Test
    public void shouldFindCorrectAnswerForDay4Part2WithGrid15() {
        assertEquals(5371020, day4.solveBingoGetLastWin(15));
    }

    @Test
    public void shouldFindCorrectAnswerForDay4Part1WithGrid30() {
        assertEquals(1527513658, day4.solveBingoGetFirstWin(30));
    }

    @Test
    public void shouldFindCorrectAnswerForDay4Part2WithGrid30() {
        assertEquals(199953180, day4.solveBingoGetLastWin(30));
    }

}
