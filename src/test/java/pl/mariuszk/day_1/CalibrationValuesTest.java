package pl.mariuszk.day_1;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

class CalibrationValuesTest {

    // Part I
    @Test
    void shouldRetrieveSumOfCalibrationValues_smallSample() {
        // given
        String input = """
                1abc2
                pqr3stu8vwx
                a1b2c3d4e5f
                treb7uchet
                """;

        // when
        int result = CalibrationValues.decipherAndSumCalibrationValues(input);

        // then
        assertThat(result).isEqualTo(142);
    }

    @Test
    void shouldRetrieveSumOfCalibrationValues_bigTextFile() throws IOException {
        // given
        String input = Files.readString(Paths.get("src", "test", "resources", "day_1", "calibration_input.txt"));

        // when
        int result = CalibrationValues.decipherAndSumCalibrationValues(input);

        // then
        assertThat(result).isEqualTo(55172);
    }

    // Part II
    @Test
    void shouldRetrieveSumOfCalibrationValues_digitsSpelledOutWithLetters_smallSample() {
        // given
        String input = """
                8twohprsxmz47
                qzjggk1one
                sevenineeightwo
                two1nine
                eightwothree
                abcone2threexyz
                xtwone3four
                4nineeightseven2
                zoneight234
                7pqrstsixteen
                """;

        // when
        int result = CalibrationValues.decipherIncludingTextDigitsAndSumCalibrationValues(input);

        // then
        assertThat(result).isEqualTo(451);
    }

    @Test
    void shouldRetrieveSumOfCalibrationValues_digitsSpelledOutWithLetters_bigTextFile() throws IOException {
        // given
        String input = Files.readString(Paths.get("src", "test", "resources", "day_1", "calibration_input.txt"));

        // when
        int result = CalibrationValues.decipherIncludingTextDigitsAndSumCalibrationValues(input);

        // then
        assertThat(result).isEqualTo(54925);
    }
}