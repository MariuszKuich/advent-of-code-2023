package pl.mariuszk.day_1;

import java.util.Arrays;
import java.util.Map;

public class CalibrationValues {

    private static final Map<String, String> TEXT_DIGITS = Map.ofEntries(
            Map.entry("1", "1"),
            Map.entry("2", "2"),
            Map.entry("3", "3"),
            Map.entry("4", "4"),
            Map.entry("5", "5"),
            Map.entry("6", "6"),
            Map.entry("7", "7"),
            Map.entry("8", "8"),
            Map.entry("9", "9"),
            Map.entry("one", "1"),
            Map.entry("two", "2"),
            Map.entry("three", "3"),
            Map.entry("four", "4"),
            Map.entry("five", "5"),
            Map.entry("six", "6"),
            Map.entry("seven", "7"),
            Map.entry("eight", "8"),
            Map.entry("nine", "9")
    );

    // Part I
    public static int decipherAndSumCalibrationValues(String input) {
        return Arrays.stream(input.split("\n"))
                // get rid of all non-numeric characters
                .map(s -> s.replaceAll("\\D", ""))
                // get first and last digit
                .map(n -> n.charAt(0) + n.substring(n.length() - 1))
                .map(Integer::valueOf)
                .reduce(0, Integer::sum);
    }

    // Part II
    public static int decipherIncludingTextDigitsAndSumCalibrationValues(String input) {
        return Arrays.stream(input.split("\n"))
                .map(CalibrationValues::getCalibrationValue)
                .reduce(0, Integer::sum);
    }

    private static int getCalibrationValue(String line) {
        int minIdx = line.length();
        int maxIdx = -1;
        String minDigit = "";
        String maxDigit = "";
        for (String key : TEXT_DIGITS.keySet()) {
            int firstIdx = line.indexOf(key);
            int lastIdx = line.lastIndexOf(key);
            if (firstIdx == -1) {
                // this key does not exist in the string at all, we can continue to the next key
                continue;
            }
            if (firstIdx < minIdx) {
                minIdx = firstIdx;
                minDigit = key;
            }
            if (lastIdx > maxIdx) {
                maxIdx = lastIdx;
                maxDigit = key;
            }
        }
        return Integer.parseInt(TEXT_DIGITS.get(minDigit) + TEXT_DIGITS.get(maxDigit));
    }
}
