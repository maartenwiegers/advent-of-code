package nl.maartenwiegers.aoc.y2020;

import lombok.Builder;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import nl.maartenwiegers.aoc.commons.FileService;
import org.apache.commons.lang3.StringUtils;

import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class Day4 {

    private static final String FILE_NAME = "input/y2020/04-%s.txt";
    private final List<Passport> passports = new ArrayList<>();

    public long getCountValidPassports(String filename, boolean isPart2) {
        initializePassports(filename);
        if (isPart2) {
            return passports.stream()
                    .filter(Passport::isValidForPart2)
                    .count();
        }
        return passports.stream()
                .filter(Passport::isValid)
                .count();
    }

    @SneakyThrows
    private void initializePassports(String filename) {
        String input = Files.readString(FileService.getPath(String.format(FILE_NAME, filename)));
        Arrays.stream(StringUtils.splitByWholeSeparatorPreserveAllTokens(input, "\r\n\r\n"))
                .forEach(line -> {
                    String[] fields = StringUtils.split(line);
                    Map<String, String> fieldsMap = new HashMap<>();
                    Arrays.stream(fields)
                            .forEach(field -> fieldsMap.put(StringUtils.split(field, ":")[0],
                                                            StringUtils.split(field, ":")[1]));
                    passports.add(Passport.builder()
                                          .byr(fieldsMap.get("byr"))
                                          .iyr(fieldsMap.get("iyr"))
                                          .eyr(fieldsMap.get("eyr"))
                                          .hgt(fieldsMap.get("hgt"))
                                          .hcl(fieldsMap.get("hcl"))
                                          .ecl(fieldsMap.get("ecl"))
                                          .pid(fieldsMap.get("pid"))
                                          .cid(fieldsMap.get("cid"))
                                          .build());
                });
        log.info("Passports initialized: {}", passports);
    }

    @Builder
    @Data
    static class Passport {
        String byr;
        String iyr;
        String eyr;
        String hgt;
        String hcl;
        String ecl;
        String pid;
        String cid;

        boolean isValid() {
            return byr != null && iyr != null && eyr != null && hgt != null && hcl != null && ecl != null && pid != null;
        }

        boolean isValidForPart2() {
            if (byr == null) {
                return false;
            }
            int birthYear = Integer.parseInt(byr);
            if (birthYear < 1920 || birthYear > 2002) {
                return false;
            }

            if (iyr == null) {
                return false;
            }
            int issueYear = Integer.parseInt(iyr);
            if (issueYear < 2010 || issueYear > 2020) {
                return false;
            }

            if (eyr == null) {
                return false;
            }
            int expirationYear = Integer.parseInt(eyr);
            if (expirationYear < 2020 || expirationYear > 2030) {
                return false;
            }

            if (StringUtils.endsWith(hgt, "cm")) {
                int height = Integer.parseInt(hgt.replaceAll("\\D+", ""));
                if (height < 150 || height > 193) {
                    return false;
                }
            } else if (StringUtils.endsWith(hgt, "in")) {
                int height = Integer.parseInt(hgt.replaceAll("\\D+", ""));
                if (height < 59 || height > 76) {
                    return false;
                }
            } else {
                return false;
            }

            if(hcl == null) {
                return false;
            }
            if (!hcl.startsWith("#")) {
                return false;
            }
            if (!hcl.substring(1)
                    .matches("[0-9][a-f]{6}")) {
                return false;
            }

            if (!List.of("amb", "blu", "brn", "gry", "grn", "hzl", "oth")
                    .contains(ecl)) {
                return false;
            }

            return pid.matches("[0-9]{9}");
        }
    }
}
