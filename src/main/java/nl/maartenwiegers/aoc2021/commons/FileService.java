package nl.maartenwiegers.aoc2021.commons;

import lombok.NonNull;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
        return Files.readAllLines(FileService.getPath(filename))
                .stream()
                .toList();
    }

    @SneakyThrows
    public static List<Integer> getInputAsListInteger(@NonNull String filename) {
        return Files.readAllLines(FileService.getPath(filename))
                .stream()
                .map(Integer::valueOf)
                .toList();
    }
}
