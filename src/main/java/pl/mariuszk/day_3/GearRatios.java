package pl.mariuszk.day_3;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GearRatios {

    // Part I
    public int sumPartNumbers(String schematic) {
        int partNumbersSum = 0;
        String[] lines = schematic.split("\n");

        for (int i = 0; i < lines.length; i++) {
            String currLine = lines[i];
            Pattern numberPattern = Pattern.compile("(\\d+)");
            Matcher numberMatcher = numberPattern.matcher(currLine);

            while (numberMatcher.find()) {
                String number = numberMatcher.group(1);
                int startIdx = currLine.indexOf(number);

                if (numberAdjacentToSymbol(lines, currLine, number, startIdx, i)) {
                    partNumbersSum += Integer.parseInt(number);
                }

                // one number can appear multiple times in one line, so for determining startIdx properly
                // I must delete currently processed number from the line
                currLine = currLine.replaceFirst(number, ".".repeat(number.length()));
                lines[i] = currLine;
            }
        }

        return partNumbersSum;
    }

    private boolean numberAdjacentToSymbol(String[] lines, String currLine, String number, int startIdx, int lineIdx) {
        return symbolAtIdx(currLine, startIdx - 1)
                || symbolAtIdx(currLine, startIdx + number.length())
                || symbolInIdxRangeOnPreviousOrNextLine(lines, lineIdx, number, startIdx);
    }

    private boolean symbolAtIdx(String line, int idx) {
        if (idx < 0 || idx >= line.length()) {
            return false;
        }
        // check if character at index is not a digit or a dot
        return !String.valueOf(line.charAt(idx)).matches("\\.|\\d");
    }

    private boolean symbolInIdxRangeOnPreviousOrNextLine(String[] lines, int currLineIdx, String number,
                                                         int numberStartIdx) {
        int fromIdx = numberStartIdx - 1;
        int toIdx = numberStartIdx + number.length();
        return symbolInIdxRangeOnLine(lines, currLineIdx - 1, fromIdx, toIdx)
                || symbolInIdxRangeOnLine(lines, currLineIdx + 1, fromIdx, toIdx);
    }

    private boolean symbolInIdxRangeOnLine(String[] lines, int lineIdx, int fromIdx, int toIdx) {
        if (lineIdx < 0 || lineIdx >= lines.length) {
            return false;
        }
        String line = lines[lineIdx];
        fromIdx = Math.max(fromIdx, 0);
        toIdx = Math.min(toIdx, line.length() - 1);
        // endIndex in the substring() method is exclusive, so I add 1 to it
        String rangeToCheck = line.substring(fromIdx, toIdx + 1);
        rangeToCheck = rangeToCheck.replaceAll("\\d+", "");
        rangeToCheck = rangeToCheck.replace(".", "");
        return !rangeToCheck.isEmpty();
    }

    // Part II
    public long sumGearRatios(String input) {
        long gearRatiosSum = 0L;
        String[] lines = input.split("\n");

        for (int i = 0; i < lines.length; i++) {
            String currLine = lines[i];

            while (currLine.contains("*")) {
                int gearIdx = currLine.indexOf("*");

                List<Long> adjacentNumbers = findAdjacentNumbers(lines, i, gearIdx);
                if (adjacentNumbers.size() == 2) {
                    gearRatiosSum += adjacentNumbers.get(0) * adjacentNumbers.get(1);
                }

                // necessary for finding next gear with String.indexOf() method properly
                currLine = currLine.replaceFirst("\\*", ".");
            }
        }

        return gearRatiosSum;
    }

    private List<Long> findAdjacentNumbers(String[] lines, int lineIdx, int gearIdx) {
        List<Long> numbersInTheSameLine = findAdjacentNumbersInTheSameLine(lines[lineIdx], gearIdx);
        List<Long> numbersInLineAbove = findAdjacentNumbersInDifferentLine(lines, lineIdx - 1, gearIdx);
        List<Long> numbersInLineBelow = findAdjacentNumbersInDifferentLine(lines, lineIdx + 1, gearIdx);

        return Stream.of(numbersInTheSameLine, numbersInLineAbove, numbersInLineBelow)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    private List<Long> findAdjacentNumbersInTheSameLine(String line, int gearIdx) {
        List<Long> numbers = new ArrayList<>();
        Matcher numberMatcher = createNumberMatcher(line);

        while (numberMatcher.find()) {
            String number = numberMatcher.group(1);
            int numberIdx = line.indexOf(number);

            if (numberIsAdjacentSameLine(gearIdx, numberIdx, number)) {
                numbers.add(Long.parseLong(number));
            }

            // necessary for finding next number with String.indexOf() method properly
            line = line.replaceFirst(number, ".".repeat(number.length()));
        }

        return numbers;
    }

    private static Matcher createNumberMatcher(String text) {
        Pattern numberPattern = Pattern.compile("(\\d+)");
        return numberPattern.matcher(text);
    }

    private static boolean numberIsAdjacentSameLine(int gearIdx, int numberIdx, String number) {
        return numberIdx + number.length() == gearIdx || numberIdx == gearIdx + 1;
    }

    private List<Long> findAdjacentNumbersInDifferentLine(String[] lines, int lineIdx, int gearIdx) {
        if (lineIdx < 0 || lineIdx >= lines.length) {
            return List.of();
        }

        List<Long> numbers = new ArrayList<>();
        String line = lines[lineIdx];
        Matcher numberMatcher = createNumberMatcher(line);

        while (numberMatcher.find()) {
            String number = numberMatcher.group(1);
            int numberIdx = line.indexOf(number);

            if (numberIsAdjacentDifferentLine(gearIdx, numberIdx, number)) {
                numbers.add(Long.parseLong(number));
            }

            // necessary for finding next number with String.indexOf() method properly
            line = line.replaceFirst(number, ".".repeat(number.length()));
        }

        return numbers;
    }

    private static boolean numberIsAdjacentDifferentLine(int gearIdx, int numberIdx, String number) {
        return gearIdx >= numberIdx - 1 && gearIdx <= numberIdx + number.length();
    }
}
