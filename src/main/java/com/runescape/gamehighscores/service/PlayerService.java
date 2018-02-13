package com.runescape.gamehighscores.service;

import com.runescape.gamehighscores.custommapping.PlayerComparison;
import com.runescape.gamehighscores.entity.Category;
import com.runescape.gamehighscores.entity.Score;
import com.runescape.gamehighscores.exception.PlayerNotFoundException;
import com.runescape.gamehighscores.exception.ScoreNotFoundException;
import com.runescape.gamehighscores.repository.CategoryDao;
import com.runescape.gamehighscores.repository.PlayerDao;
import com.runescape.gamehighscores.entity.Player;
import com.runescape.gamehighscores.repository.ScoreDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlayerService {

    Logger logger = LoggerFactory.getLogger(PlayerService.class);

    @Autowired
    PlayerDao playerDao;

    @Autowired
    CategoryDao categoryDao;

    @Autowired
    ScoreDao scoreDao;

    public List<Player> getAllPlayers() {
        logger.info("Getting all players");
        List<Player> players = new ArrayList<>();
        playerDao.findAll()
                .forEach(players::add);
        return players;
    }

    public Player getPlayerById(Integer id) {
        logger.info("Getting player with id: " + id);
        return playerDao.findById(id)
                .orElseThrow(() -> new PlayerNotFoundException(id));
    }

    @ResponseStatus(HttpStatus.CREATED)
    public Player insertPlayer(Player playerNew) {
        playerNew = playerDao.save(playerNew);
        logger.info("Inserted player with id: " + playerNew.getId());

        Optional<Category> overallCategory = categoryDao.findByName("Overall");
        Player finalPlayerNew = playerNew;
        Score overallScore = new Score(overallCategory.orElseThrow(
                () -> new PlayerNotFoundException(finalPlayerNew.getId())
        ), playerNew, 0, 0L);
        scoreDao.save(overallScore);
        logger.info("Also inserted overall score for player");
        return playerNew;
    }

    @ResponseStatus(HttpStatus.OK)
    public void updatePlayer(Player player, Integer id) {
        if (!playerDao.exists(id)) {
            throw new PlayerNotFoundException(id);
        }
        Player tempPlayer = playerDao.findOne(id);
        tempPlayer.setName(player.getName());
        playerDao.save(tempPlayer);
        logger.info("Updated player with id: " + id);
    }

    @ResponseStatus(HttpStatus.OK)
    public void deletePlayer(Integer id) {
        playerDao.delete(id);
        logger.info("Deleted player with id: " + id);
    }

    @ResponseStatus(HttpStatus.OK)
    public List<PlayerComparison> compareToPlayer(Integer id1, Integer id2) {
        if (!playerDao.exists(id1)) {
            throw new PlayerNotFoundException(id1);
        }if (!playerDao.exists(id2)) {
            throw new PlayerNotFoundException(id2);
        }
        List<Score> scores1 = scoreDao.findByPlayerId(id1);
        List<Score> scores2 = scoreDao.findByPlayerId(id2);
        List<PlayerComparison> playersComparisons = scores1.stream()
                .filter(s1 -> scores2.stream()
                        .anyMatch(s2 -> s2.belongsToSameCategory(s1)))
                .map(s1 -> {
                    Score s2 = scores2.stream()
                            .filter(s3 -> s3.belongsToSameCategory(s1))
                            .findFirst()
                            .get();
                    return new PlayerComparison(s1, s2);
                }).collect(Collectors.toList());
        return playersComparisons;
    }

    @ResponseStatus(HttpStatus.OK)
    public List<Player> searchPlayerByName(String name) {
        return playerDao.findByNameContainingIgnoreCase(name);
    }

}
