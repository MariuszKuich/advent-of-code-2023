package pl.mariuszk.day_2;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

// I do this for Matcher.find() since I know each line is formatted in a strictly defined way
@SuppressWarnings("ResultOfMethodCallIgnored")
public class CubeConundrum {

    private static final int RED_CUBES = 12;
    private static final int GREEN_CUBES = 13;
    private static final int BLUE_CUBES = 14;

    // Part I
    public int sumPossibleGamesIds(String input) {
        int possibleGamesIdsSum = 0;

        for (String game : input.split("\n")) {
            int gameId = extractGameId(game);

            if (gameIsPossible(game)) {
                possibleGamesIdsSum += gameId;
            }
        }

        return possibleGamesIdsSum;
    }

    private int extractGameId(String line) {
        Pattern gamePattern = Pattern.compile("Game (\\d+):");
        Matcher gameMatcher = gamePattern.matcher(line);
        gameMatcher.find();
        return Integer.parseInt(gameMatcher.group(1));
    }

    private boolean gameIsPossible(String game) {
        for (String set : game.split(";")) {
            if (gameIsImpossibleForColor("red", set, RED_CUBES)
                    || gameIsImpossibleForColor("green", set, GREEN_CUBES)
                    || gameIsImpossibleForColor("blue", set, BLUE_CUBES)) {
                return false;
            }
        }
        return true;
    }

    private boolean gameIsImpossibleForColor(String color, String set, int totalColorCount) {
        int cubesCount = getNumberOfCubes(color, set);
        return cubesCount > totalColorCount;
    }

    private int getNumberOfCubes(String color, String set) {
        Pattern colorPattern = Pattern.compile("(\\d+) " + color);
        Matcher colorMatcher = colorPattern.matcher(set);
        if (colorMatcher.find()) {
            return Integer.parseInt(colorMatcher.group(1));
        }
        return 0;
    }

    // Part II
    public long sumPowersOfMinimalCubesSets(String input) {
        long powersSum = 0L;

        for (String game : input.split("\n")) {
            long minRedCubesCount = getFewestNumberOfCubesOfColor("red", game);
            long minGreenCubesCount = getFewestNumberOfCubesOfColor("green", game);
            long minBlueCubesCount = getFewestNumberOfCubesOfColor("blue", game);

            powersSum += minRedCubesCount * minGreenCubesCount * minBlueCubesCount;
        }

        return powersSum;
    }

    private long getFewestNumberOfCubesOfColor(String color, String game) {
        Pattern colorPattern = Pattern.compile("(\\d+) " + color);
        Matcher colorMatcher = colorPattern.matcher(game);
        int minCount = 0;
        while (colorMatcher.find()) {
            int cubesCount = Integer.parseInt(colorMatcher.group(1));
            if (cubesCount > minCount) {
                minCount = cubesCount;
            }
        }
        return minCount;
    }
}
