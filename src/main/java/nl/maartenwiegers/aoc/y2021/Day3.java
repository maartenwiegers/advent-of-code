package nl.maartenwiegers.aoc.y2021;

import lombok.extern.slf4j.Slf4j;
import nl.maartenwiegers.aoc.commons.FileService;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Day3 {

    private static final String INPUT_FILE_NAME = "input/y2021/03.txt";
    private final int[] countZeroes = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    private final int[] countOnes = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

    public int getPowerConsumption() {
        FileService.getInputAsListString(INPUT_FILE_NAME)
                .forEach(this::countCharacters);
        return getGammaRate() * getEpsilonRate();
    }

    public int getLifeSupportRating() {
        return getOxygenGeneratorRating() * getCo2ScrubberRating();
    }

    private void countCharacters(String input) {
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == '0') {
                countZeroes[i]++;
            } else {
                countOnes[i]++;
            }
        }
    }

    private int getGammaRate() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 12; i++) {
            stringBuilder.append(getMostCommonBitOnPosition(i));
        }
        return Integer.parseInt(stringBuilder.toString(), 2);
    }

    private int getEpsilonRate() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 12; i++) {
            if ("1".equals(getMostCommonBitOnPosition(i))) {
                stringBuilder.append("0");
            } else {
                stringBuilder.append("1");
            }
        }
        return Integer.parseInt(stringBuilder.toString(), 2);
    }

    private String getMostCommonBitOnPosition(int position) {
        if (countOnes[position] >= countZeroes[position]) {
            return "1";
        } else {
            return "0";
        }
    }

    private int getOxygenGeneratorRating() {
        List<String> filteredMeasurements = new ArrayList<>(FileService.getInputAsListString(INPUT_FILE_NAME));

        int i = 0;
        while (filteredMeasurements.size() > 1) {
            int temp = i++;
            int countOnes = (int) filteredMeasurements.stream()
                    .map(measurement -> measurement.charAt(temp))
                    .filter(c -> c == '1')
                    .count();
            if (countOnes < (double) filteredMeasurements.size() / 2) {
                filteredMeasurements.removeIf(measurement -> measurement.charAt(temp) == '1');
            } else {
                filteredMeasurements.removeIf(measurement -> measurement.charAt(temp) == '0');
            }
        }
        return Integer.parseInt(filteredMeasurements.get(0), 2);
    }

    private int getCo2ScrubberRating() {
        List<String> filteredMeasurements = new ArrayList<>(FileService.getInputAsListString(INPUT_FILE_NAME));

        int i = 0;
        while (filteredMeasurements.size() > 1) {
            int temp = i++;
            int countOnes = (int) filteredMeasurements.stream()
                    .map(measurement -> measurement.charAt(temp))
                    .filter(c -> c == '1')
                    .count();
            if (countOnes < (double) filteredMeasurements.size() / 2) {
                filteredMeasurements.removeIf(measurement -> measurement.charAt(temp) == '0');
            } else {
                filteredMeasurements.removeIf(measurement -> measurement.charAt(temp) == '1');
            }
        }
        return Integer.parseInt(filteredMeasurements.get(0), 2);
    }
}
