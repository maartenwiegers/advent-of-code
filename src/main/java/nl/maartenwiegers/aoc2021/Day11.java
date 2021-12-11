package nl.maartenwiegers.aoc2021;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import nl.maartenwiegers.aoc2021.commons.FileService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class Day11 {

    private static final String FILE_NAME = "input/11-%s.txt";
    private Octopus[][] octopi = new Octopus[10][10];

    @GetMapping("day11/par1/{filename}/{afterSteps}")
    public long getCountOfFlashes(@PathVariable String filename, @PathVariable int afterSteps) {
        octopi = new Octopus[10][10];
        List<String> lines = FileService.getInputAsListString(String.format(FILE_NAME, filename));
        for (int y = 0; y < lines.size(); y++) {
            String line = lines.get(y);
            for (int x = 0; x < line.length(); x++) {
                octopi[y][x] = new Octopus(line.charAt(x) - 48, false);
            }
        }
        long countOfFlashes = 0;
        for (int i = 0; i < afterSteps; i++) {
            countOfFlashes += getCountOfFlashesForStep();
        }
        return countOfFlashes;
    }

    private long getCountOfFlashesForStep() {
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
        if (x >= 0 && y >= 0 && y < octopi.length && x < octopi[y].length) {
            Octopus octopus = octopi[y][x];
            octopus.energy++;
            if (octopus.energy > 9 && !octopus.hasFlashedThisStep) {
                setFlash(y, x);
            }
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

    @Data
    @AllArgsConstructor
    private class Octopus {
        int energy;
        boolean hasFlashedThisStep;
    }
}
