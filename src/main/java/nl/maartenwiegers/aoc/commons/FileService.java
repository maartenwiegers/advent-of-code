package nl.maartenwiegers.aoc.commons;

import lombok.NonNull;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class FileService {

    @SneakyThrows
    public static Path getPath(@NonNull String filename) {
        return Paths.get(Objects.requireNonNull(FileService.class.getClassLoader().getResource(filename)).toURI());
    }

    @SneakyThrows
    public static List<String> getInputAsListString(@NonNull String filename) {
        return Files.readAllLines(FileService.getPath(filename)).stream().toList();
    }

    @SneakyThrows
    public static List<Integer> getInputAsListInteger(@NonNull String filename) {
        return Files.readAllLines(FileService.getPath(filename)).stream().map(Integer::valueOf).toList();
    }

    @SneakyThrows
    public static List<Integer> getCommaSeparatedInputAsListInteger(@NonNull String filename) {
        return Arrays.stream(Files.readString(FileService.getPath(filename)).split(",")).map(Integer::valueOf).toList();
    }

    @SneakyThrows
    public static List<String> getMultiLineInputAsListString(@NonNull String filename) {
        return Arrays.stream(
                        StringUtils.splitByWholeSeparatorPreserveAllTokens(Files.readString(getPath(filename)), "\r\n\r\n"))
                .toList();
    }
}
