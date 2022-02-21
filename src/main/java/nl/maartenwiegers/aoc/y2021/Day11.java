package nl.maartenwiegers.aoc.y2021;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import nl.maartenwiegers.aoc.commons.FileService;

import java.util.List;

@Slf4j
public class Day11 {

    private static final String FILE_NAME = "input/y2021/11-%s.txt";
    private Octopus[][] octopi = new Octopus[10][10];

    public int getCountOfFlashes(String filename, int afterSteps) {
        initializeOctopi(filename);
        int countOfFlashes = 0;
        for (int i = 0; i < afterSteps; i++) {
            countOfFlashes += getCountOfFlashesForStep();
        }
        return countOfFlashes;
    }

    public int getWhenAllOctopiFlashSimultaneously(String filename) {
        initializeOctopi(filename);
        int countOfOctopi = octopi.length * octopi[0].length;
        int countOfFlashes = 0;
        int countStep = 0;
        while (countOfFlashes != countOfOctopi) {
            countOfFlashes = getCountOfFlashesForStep();
            countStep++;
        }
        return countStep;
    }

    private int getCountOfFlashesForStep() {
        for (int y = 0; y < octopi.length; y++) {
            for (int x = 0; x < octopi[y].length; x++) {
                setIncreasedEnergy(y, x);
            }
        }
        int countOfFlashes = 0;
        for (Octopus[] line : octopi) {
            for (Octopus octopus : line) {
                if (octopus.hasFlashedThisStep) {
                    countOfFlashes++;
                    octopus.setEnergy(0);
                }
                octopus.setHasFlashedThisStep(false);
            }
        }
        return countOfFlashes;
    }

    private void setIncreasedEnergy(int y, int x) {
        try {
            Octopus octopus = octopi[y][x];
            octopus.energy++;
            if (octopus.energy > 9 && !octopus.hasFlashedThisStep) {
                setFlash(y, x);
            }
        } catch (ArrayIndexOutOfBoundsException ignored) {

        }
    }

    private void setFlash(int y, int x) {
        octopi[y][x].setHasFlashedThisStep(true);
        setIncreasedEnergy(y + 1, x + 1);
        setIncreasedEnergy(y + 1, x);
        setIncreasedEnergy(y + 1, x - 1);
        setIncreasedEnergy(y, x + 1);
        setIncreasedEnergy(y, x - 1);
        setIncreasedEnergy(y - 1, x + 1);
        setIncreasedEnergy(y - 1, x);
        setIncreasedEnergy(y - 1, x - 1);
    }

    private void initializeOctopi(String filename) {
        octopi = new Octopus[10][10];
        List<String> lines = FileService.getInputAsListString(String.format(FILE_NAME, filename));
        for (int y = 0; y < lines.size(); y++) {
            String line = lines.get(y);
            for (int x = 0; x < line.length(); x++) {
                octopi[y][x] = new Octopus(line.charAt(x) - 48, false);
            }
        }
    }

    @Data
    @AllArgsConstructor
    private static class Octopus {
        int energy;
        boolean hasFlashedThisStep;
    }
}
