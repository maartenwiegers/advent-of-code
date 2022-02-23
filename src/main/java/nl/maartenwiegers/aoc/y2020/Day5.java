package nl.maartenwiegers.aoc.y2020;

import lombok.extern.slf4j.Slf4j;
import nl.maartenwiegers.aoc.commons.FileService;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Slf4j
public class Day5 {

    private static final String FILE_NAME = "input/y2020/05-%s.txt";

    public int getHighestSeatId(String filename) {
        return FileService.getInputAsListString(String.format(FILE_NAME, filename))
                .stream()
                .mapToInt(this::getSeatId)
                .max()
                .orElse(0);
    }

    public int getMySeatId(String filename) {
        List<Integer> seatIdList = FileService.getInputAsListString(String.format(FILE_NAME, filename))
                .stream()
                .mapToInt(this::getSeatId)
                .boxed()
                .sorted()
                .toList();

        return IntStream.range(seatIdList.get(0), seatIdList.get(seatIdList.size() - 1))
                .filter(i -> !seatIdList.contains(i + 1))
                .findFirst()
                .orElse(0) + 1;
    }

    private int getSeatId(String input) {
        List<Integer> rowList = new ArrayList<>();
        IntStream.rangeClosed(0, 127)
                .forEach(rowList::add);

        for (int i = 0; i < 7; i++) {
            String frontOrBack = input.substring(i, i + 1);
            if (frontOrBack.equals("B")) {
                rowList.subList(0, rowList.size() / 2)
                        .clear();
            } else {
                rowList.subList(rowList.size() / 2, rowList.size())
                        .clear();
            }
        }
        Assert.isTrue(rowList.size() == 1, String.format("Error: more than one row left for input [%s]", input));
        int row = rowList.get(0);

        List<Integer> columnList = new ArrayList<>();
        IntStream.rangeClosed(0, 7)
                .forEach(columnList::add);

        for (int i = 7; i < 10; i++) {
            String leftOrRight = input.substring(i, i + 1);
            if (leftOrRight.equals("R")) {
                columnList.subList(0, columnList.size() / 2)
                        .clear();
            } else {
                columnList.subList(columnList.size() / 2, columnList.size())
                        .clear();
            }
        }
        Assert.isTrue(columnList.size() == 1, String.format("Error: more than one column left for input [%s]", input));
        int column = columnList.get(0);

        return (row * 8) + column;
    }
}
