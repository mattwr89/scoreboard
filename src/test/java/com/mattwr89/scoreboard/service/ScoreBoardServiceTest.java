package com.mattwr89.scoreboard.service;

import com.mattwr89.scoreboard.model.Match;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ScoreBoardServiceTest {

    private ScoreBoardService scoreBoard;

    @BeforeEach
    void setUp() {
        scoreBoard = new ScoreBoardService();
    }

    @Test
    void startGame() {
        Match match = scoreBoard.startGame("Germany", "France");
        assertThat(match.getHomeTeam()).isEqualTo("Germany");
        assertThat(match.getAwayTeam()).isEqualTo("France");
        assertThat(match.getHomeScore()).isZero();
        assertThat(match.getAwayScore()).isZero();
    }

    void finishGame() {
        scoreBoard.startGame("Germany", "France");
        scoreBoard.finishGame("Germany", "France");

        assertThat(scoreBoard.getGames()).isEmpty();
    }

    @Test
    void updateScore() {
        Match match = scoreBoard.startGame("Spain", "Brazil");
        scoreBoard.updateScore("Spain", "Brazil", 10, 2);

        Match updated = scoreBoard.getGames().get(0);
        assertThat(updated.getHomeScore()).isEqualTo(10);
        assertThat(updated.getAwayScore()).isEqualTo(2);
    }

    @Test
    void shouldReturnSummarySortedByTotalScoreAndRecency() {
        provideTestGames();
        List<Match> summary = scoreBoard.getSummary();

        assertThat(summary)
                .map(m -> m.getHomeTeam() + "-" + m.getAwayTeam())
                .containsExactly(
                        "Uruguay-Italy",
                        "Spain-Brazil",
                        "Mexico-Canada",
                        "Argentina-Australia",
                        "Germany-France"
                );
    }

    private void provideTestGames() {
        scoreBoard.startGame("Mexico", "Canada");
        scoreBoard.updateScore("Mexico", "Canada", 0, 5);

        scoreBoard.startGame("Spain", "Brazil");
        scoreBoard.updateScore("Spain", "Brazil", 10, 2);

        scoreBoard.startGame("Germany", "France");
        scoreBoard.updateScore("Germany", "France", 2, 2);

        scoreBoard.startGame("Uruguay", "Italy");
        scoreBoard.updateScore("Uruguay", "Italy", 6, 6);

        scoreBoard.startGame("Argentina", "Australia");
        scoreBoard.updateScore("Argentina", "Australia", 3, 1);
    }
}