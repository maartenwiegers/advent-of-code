package nl.maartenwiegers.aoc2021;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;

import static org.mockito.Mockito.doReturn;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day4Test {

    private final Day4 day4 = new Day4();

    @Test
    public void shouldFindCorrectAnswerForrDay4Part1() {
        assertEquals(49686, day4.solveBingo());
    }

}
