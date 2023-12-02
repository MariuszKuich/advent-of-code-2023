package pl.mariuszk.day_2;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

class CubeConundrumTest {

    // Part I
    @Test
    void shouldSumPossibleGamesIds_12red_13green_14_blue_smallSample() {
        // given
        String input = """
                Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
                Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
                Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
                Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
                Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green
                """;

        // when
        int result = new CubeConundrum().sumPossibleGamesIds(input);

        // then
        Assertions.assertThat(result).isEqualTo(8);
    }

    @Test
    void shouldSumPossibleGamesIds_12red_13green_14_blue_bigTextFile() throws IOException {
        // given
        String input = Files.readString(Paths.get("src", "test", "resources", "day_2", "games_record.txt"));

        // when
        int result = new CubeConundrum().sumPossibleGamesIds(input);

        // then
        Assertions.assertThat(result).isEqualTo(3099);
    }
}