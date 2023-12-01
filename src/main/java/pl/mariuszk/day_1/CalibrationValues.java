package pl.mariuszk.day_1;

import java.util.Arrays;

public class CalibrationValues {

    public static int decipherAndSumCalibrationValues(String input) {
        return Arrays.stream(input.split("\n"))
                // get rid of all non-numeric characters
                .map(s -> s.replaceAll("\\D", ""))
                // get first and last digit
                .map(n -> n.charAt(0) + n.substring(n.length() - 1))
                .map(Integer::valueOf)
                .reduce(0, Integer::sum);
    }
}
