package nl.maartenwiegers.aoc2021.commons;

import org.springframework.stereotype.Service;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@Service
public class FileService {

    public static Path getPath(String filename) throws URISyntaxException {
        return Paths.get(Objects.requireNonNull(FileService.class.getClassLoader().getResource(filename)).toURI());
    }
}
