package nl.maartenwiegers.aoc.y2020;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import nl.maartenwiegers.aoc.commons.FileService;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Day2 {

    private static final String FILE_NAME = "input/y2020/02-%s.txt";
    private final List<Password> passwords = new ArrayList<>();

    public long getCountOfValidPasswords(int part, String filename) {
        FileService.getInputAsListString(String.format(FILE_NAME, filename))
                .forEach(line -> {
                    String[] fields = StringUtils.split(line, "- :");
                    passwords.add(Password.builder()
                            .minimumOccurrence(Integer.parseInt(fields[0]))
                            .maximumOccurrence(Integer.parseInt(fields[1]))
                            .requiredCharacter(fields[2])
                            .password(fields[3])
                            .build());
                });
        log.info("Passwords: {}", passwords);
        return getCountOfValidPasswords(part);
    }

    private long getCountOfValidPasswords(int part) {
        if (part == 2) {
            return passwords.stream()
                    .filter(Password::isValidPasswordForPart2)
                    .peek(p -> log.info("Valid password: {}", p))
                    .count();
        }
        return passwords.stream()
                .filter(Password::isValidPasswordForPart1)
                .peek(p -> log.info("Valid password: {}", p))
                .count();
    }

    @Data
    @Builder
    static class Password {
        int minimumOccurrence;
        int maximumOccurrence;
        String requiredCharacter;
        String password;

        boolean isValidPasswordForPart1() {
            return StringUtils.countMatches(password, requiredCharacter) >= minimumOccurrence && StringUtils.countMatches(password, requiredCharacter) <= maximumOccurrence;
        }

        boolean isValidPasswordForPart2() {
            return isMatchOnFirstCharacter() ^ isMatchOnSecondCharacter();
        }

        boolean isMatchOnFirstCharacter() {
            return StringUtils.equals(password.subSequence(minimumOccurrence - 1, minimumOccurrence), requiredCharacter);
        }

        boolean isMatchOnSecondCharacter() {
            return StringUtils.equals(password.subSequence(maximumOccurrence - 1, maximumOccurrence), requiredCharacter);
        }

    }
}
