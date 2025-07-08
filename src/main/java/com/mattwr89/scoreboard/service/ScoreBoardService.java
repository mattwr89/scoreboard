package com.mattwr89.scoreboard.service;

import com.mattwr89.scoreboard.model.Match;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class ScoreBoardService {
    private final List<Match> matches = new ArrayList<>();

    public Match startGame(String homeTeam, String awayTeam) {
        Match match = new Match(homeTeam, awayTeam);
        matches.add(match);
        return match;
    }
    public void finishGame(String homeTeam, String awayTeam) {
        findGame(homeTeam, awayTeam).ifPresent(matches::remove);
    }

    public void updateScore(String homeTeam, String awayTeam, int homeScore, int awayScore) {
        Optional<Match> gameOpt = findGame(homeTeam, awayTeam);
        gameOpt.ifPresent(match -> {
            match.setHomeScore(homeScore);
            match.setAwayScore(awayScore);
        });
    }
    public List<Match> getGames() {
        return new ArrayList<>(matches);
    }
    public List<Match> getSummary() {
        return matches.stream()
                .sorted(Comparator
                        .comparingInt(Match::getTotalScore).reversed()
                        .thenComparing(Match::getStartTime, Comparator.reverseOrder())
                )
                .toList();

    }
    private Optional<Match> findGame(String homeTeam, String awayTeam) {
        return matches.stream()
                .filter(match -> match.getHomeTeam().equals(homeTeam) && match.getAwayTeam().equals(awayTeam))
                .findFirst();
    }
}
