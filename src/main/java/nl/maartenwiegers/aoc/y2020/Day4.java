package nl.maartenwiegers.aoc.y2020;

import lombok.Builder;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import nl.maartenwiegers.aoc.commons.FileService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

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
            return passports.stream().filter(Passport::isValidForPart2).count();
        }
        return passports.stream().filter(Passport::isValid).count();
    }

    @SneakyThrows
    private void initializePassports(String filename) {
        FileService.getMultiLineInputAsListString(String.format(FILE_NAME, filename)).forEach(line -> {
            String[] fields = StringUtils.split(line);
            Map<String, String> fieldsMap = new HashMap<>();
            Arrays.stream(fields).forEach(
                    field -> fieldsMap.put(StringUtils.split(field, ":")[0], StringUtils.split(field, ":")[1]));
            passports.add(
                    Passport.builder().byr(fieldsMap.get("byr")).iyr(fieldsMap.get("iyr")).eyr(fieldsMap.get("eyr"))
                            .hgt(fieldsMap.get("hgt")).hcl(fieldsMap.get("hcl")).ecl(fieldsMap.get("ecl"))
                            .pid(fieldsMap.get("pid")).cid(fieldsMap.get("cid")).build());
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
            try {
                Assert.notNull(byr, String.format("Passport %s not valid, byr null", this));
                int birthYear = Integer.parseInt(byr);
                Assert.isTrue(birthYear >= 1920 && birthYear <= 2002,
                              String.format("Passport %s not valid, byr out-of-range", this));

                Assert.notNull(iyr, String.format("Passport %s not valid, iyr null", this));
                int issueYear = Integer.parseInt(iyr);
                Assert.isTrue(issueYear >= 2010 && issueYear <= 2020,
                              String.format("Passport %s not valid, iyr out-of-range", this));

                Assert.notNull(eyr, String.format("Passport %s not valid, eyr null", this));
                int expirationYear = Integer.parseInt(eyr);
                Assert.isTrue(expirationYear >= 2020 && expirationYear <= 2030,
                              String.format("Passport %s not valid, eyr out-of-range", this));

                Assert.isTrue(StringUtils.endsWith(hgt, "cm") || StringUtils.endsWith(hgt, "in"),
                              String.format("Passport %s not valid, hgt incorrect unit", this));

                int height = Integer.parseInt(hgt.replaceAll("\\D+", ""));
                if (StringUtils.endsWith(hgt, "cm")) {
                    Assert.isTrue(height >= 150 && height <= 193,
                                  String.format("Passport %s not valid, hgt out-of-range", this));
                } else if (StringUtils.endsWith(hgt, "in")) {
                    Assert.isTrue(height >= 59 && height <= 76,
                                  String.format("Passport %s not valid, hgt out-of-range", this));
                }

                Assert.notNull(hcl, String.format("Passport %s not valid, hcl null", this));
                Assert.isTrue(hcl.startsWith("#"),
                              String.format("Passport %s not valid, hcl does not start with #", this));
                Assert.isTrue(hcl.substring(1).matches("[0-9a-f]{6}"),
                              String.format("Passport %s not valid, hcl does not contain correct chars #", this));

                Assert.notNull(ecl, String.format("Passport %s not valid, ecl null", this));
                Assert.isTrue(List.of("amb", "blu", "brn", "gry", "grn", "hzl", "oth").contains(ecl),
                              String.format("Passport %s not valid, ecl invalid value", this));

                Assert.notNull(pid, String.format("Passport %s not valid, pid null", this));
                Assert.isTrue(pid.matches("[0-9]{9}"), String.format("Passport %s not valid, pid invalid value", this));
                return true;
            } catch (IllegalArgumentException iae) {
                log.error(iae.getMessage());
                return false;
            }
        }
    }
}