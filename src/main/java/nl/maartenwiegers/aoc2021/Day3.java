package nl.maartenwiegers.aoc2021;

import lombok.extern.slf4j.Slf4j;
import nl.maartenwiegers.aoc2021.commons.FileService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class Day3 {

    private static final String INPUT_FILE_NAME = "input/03.txt";
    private final int[] countZeroes = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    private final int[] countOnes = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

    @GetMapping("day3/part1")
    public int getPowerConsumption() {
        FileService.getInputAsListString(INPUT_FILE_NAME).forEach(this::countCharacters);
        String gammaRateBinary = getGammaBinaryRepresentation();
        String epsilonRateBinary = getEpsilonBinaryRepresentation();
        return Integer.parseInt(gammaRateBinary, 2) * Integer.parseInt(epsilonRateBinary, 2);
    }


    private void countCharacters(String input) {
        for(int i = 0; i < input.length(); i++) {
            if(input.charAt(i) == '0') {
                countZeroes[i]++;
            } else {
                countOnes[i]++;
            }
        }
    }

    private String getGammaBinaryRepresentation() {
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < 12; i++) {
            stringBuilder.append(getMostCommonBitOnPosition(i));
        }
        return stringBuilder.toString();
    }

    private String getEpsilonBinaryRepresentation() {
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < 12; i++) {
            if("1".equals(getMostCommonBitOnPosition(i))) {
                stringBuilder.append("0");
            } else {
                stringBuilder.append("1");
            }
        }
        return stringBuilder.toString();
    }

    private String getMostCommonBitOnPosition(int position) {
        if(countOnes[position] > countZeroes[position]) {
            return "1";
        } else {
            return "0";
        }
    }
}
