package nl.maartenwiegers.aoc.commons;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Coordinate {
    int y;
    int x;

    public Set<Coordinate> getHorizontalVerticalAdjacent() {
        return new HashSet<>(Arrays.asList(new Coordinate(y, x - 1), new Coordinate(y, x + 1), new Coordinate(y - 1, x), new Coordinate(y + 1, x)));
    }
}
