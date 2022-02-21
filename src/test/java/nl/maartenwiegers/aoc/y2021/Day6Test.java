package nl.maartenwiegers.aoc.y2021;

import nl.maartenwiegers.aoc.y2021.Day6;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day6Test {

    private final Day6 day6 = new Day6();

    @Test
    public void shouldFindCorrectAnswerForDay6Part1() {
        assertEquals(BigInteger.valueOf(386536), day6.getCountOfFishAfterDays(80));
    }

    @Test
    public void shouldFindCorrectAnswerForDay6Part2() {
        assertEquals(BigInteger.valueOf(1732821262171L), day6.getCountOfFishAfterDays(256));
    }
}
