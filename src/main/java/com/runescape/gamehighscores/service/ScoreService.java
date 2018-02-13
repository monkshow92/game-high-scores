package com.runescape.gamehighscores.service;

import com.runescape.gamehighscores.entity.Player;
import com.runescape.gamehighscores.entity.Score;
import com.runescape.gamehighscores.exception.CategoryNotFoundException;
import com.runescape.gamehighscores.exception.PlayerNotFoundException;
import com.runescape.gamehighscores.exception.ScoreExistsException;
import com.runescape.gamehighscores.exception.ScoreNotFoundException;
import com.runescape.gamehighscores.repository.CategoryDao;
import com.runescape.gamehighscores.repository.PlayerDao;
import com.runescape.gamehighscores.repository.ScoreDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.function.Predicate;

@Service
public class ScoreService {

    Logger logger = LoggerFactory.getLogger(ScoreService.class);

    @Autowired
    ScoreDao scoreDao;

    @Autowired
    PlayerDao playerDao;

    @Autowired
    CategoryDao categoryDao;

    public List<Score> getScoresByCategoryId(Integer id) {
        logger.info("Getting scores for category with id: " + id);
        if (!categoryDao.exists(id)) {
            throw new CategoryNotFoundException(id);
        }
        return scoreDao.findTop10ByCategoryIdOrderByLevelDescXpDesc(id);
    }

    public List<Score> getAllScoresByPlayerId(Integer id) {
        logger.info("Getting scores for player with id: " + id);
        if (!playerDao.exists(id)) {
            throw new PlayerNotFoundException(id);
        }
        return scoreDao.findByPlayerId(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    public Score insertPlayerScore(Score score, Integer playerId) {
        if (!playerDao.exists(playerId)) {
            throw new PlayerNotFoundException(playerId);
        }
        if (scoreDao.existsByPlayerIdAndCategoryName(playerId, score.getCategory().getName())) {
            throw new ScoreExistsException( playerId, score.getCategory().getName(), true);
        }
        Player player = playerDao.findOne(playerId);
        score.setPlayer(player);
        score = scoreDao.save(score);
        logger.info("Inserted score for player with id: " + playerId);

        updatePlayerOverallScore(playerId);
        return score;
    }

    public void updatePlayerScore(Score score, Integer playerId, Integer scoreId) {
        if (!scoreDao.exists(scoreId)) {
            throw new ScoreNotFoundException(scoreId);
        }
        if (!playerDao.exists(playerId)) {
            throw new PlayerNotFoundException(playerId);
        }
        Player player = playerDao.findOne(playerId);
        Score updatedScore = scoreDao.findOne(scoreId);
        updatedScore.setPlayer(player);
        updatedScore.setCategory(score.getCategory());
        updatedScore.setLevel(score.getLevel());
        updatedScore.setXp(score.getXp());
        scoreDao.save(updatedScore);
        logger.info("Inserted score for player with id: " + playerId);

        updatePlayerOverallScore(playerId);
    }

    public void deletePlayerScore(Integer scoreId, Integer playerId) {
        if (!scoreDao.exists(scoreId)) {
            throw new ScoreNotFoundException(scoreId);
        }
        if (!playerDao.exists(playerId)) {
            throw new PlayerNotFoundException(playerId);
        }
        scoreDao.delete(scoreId);
        logger.info("Inserted score with id: " + scoreId + " for player with id: " + playerId);

        updatePlayerOverallScore(playerId);
    }

    private void updatePlayerOverallScore(Integer playerId) {
        List<Score> playerScores = getAllScoresByPlayerId(playerId);
        Predicate<Score> belongsToOverallCategory = Score::belongsToOverallCategory;
        Score overallScore = playerScores.stream()
                .filter(belongsToOverallCategory)
                .findAny()
                .orElseThrow(() -> new ScoreExistsException(playerId, "Overall", false));
        Integer totalLevel = playerScores.stream()
                .filter(belongsToOverallCategory.negate())
                .mapToInt(Score::getLevel)
                .sum();
        Long totalXp = playerScores.stream()
                .filter(belongsToOverallCategory.negate())
                .mapToLong(Score::getXp)
                .sum();
        overallScore.setLevel(totalLevel);
        overallScore.setXp(totalXp);
        scoreDao.save(overallScore);

        logger.info("Updated Overall score for player with id: " + playerId);
    }

}
