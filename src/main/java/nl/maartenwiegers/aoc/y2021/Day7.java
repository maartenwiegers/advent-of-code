package nl.maartenwiegers.aoc.y2021;

import nl.maartenwiegers.aoc.commons.FileService;

import java.util.Arrays;
import java.util.OptionalInt;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class Day7 {

    private static final String FILE_NAME = "input/y2021/07.txt";
    private static final int ARRAY_SIZE = 2000;
    private int[] crabsAtDistance = new int[ARRAY_SIZE];
    private int[] fuelCostsPerDistance = new int[ARRAY_SIZE];

    public int getFuelConsumption(boolean part1) {
        crabsAtDistance = new int[ARRAY_SIZE];
        fuelCostsPerDistance = new int[ARRAY_SIZE];
        FileService.getCommaSeparatedInputAsListInteger(FILE_NAME)
                .forEach(input -> crabsAtDistance[input]++);
        for (int startDistance = 0; startDistance < ARRAY_SIZE; startDistance++) {
            for (int destinationDistance = 0; destinationDistance < ARRAY_SIZE; destinationDistance++) {
                int distance = Math.abs(startDistance - destinationDistance);
                if (crabsAtDistance[startDistance] > 0) {
                    if (part1) {
                        fuelCostsPerDistance[destinationDistance] += getNormalFuelConsumption(crabsAtDistance[startDistance], distance);
                    } else {
                        fuelCostsPerDistance[destinationDistance] += getExponentialFuelConsumption(crabsAtDistance[startDistance], distance);
                    }
                }
            }
        }
        return getLowestFuelCost().orElse(0);
    }

    private int getNormalFuelConsumption(int countOfCrabs, int distance) {
        return countOfCrabs * distance;
    }

    private int getExponentialFuelConsumption(int countOfCrabs, int distance) {
        AtomicInteger fuel = new AtomicInteger();
        IntStream.rangeClosed(0, distance)
                .forEach(fuel::getAndAdd);
        return countOfCrabs * fuel.get();
    }

    private OptionalInt getLowestFuelCost() {
        return Arrays.stream(fuelCostsPerDistance)
                .min();
    }
}
