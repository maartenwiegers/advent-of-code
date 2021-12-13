package nl.maartenwiegers.aoc2021.commons;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CoordinateWithDepth extends Coordinate{
    int depth;

    public CoordinateWithDepth(int y, int x, int depth) {
        super(y, x);
        this.depth = depth;
    }
}
