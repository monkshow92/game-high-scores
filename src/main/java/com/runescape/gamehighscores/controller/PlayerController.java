package com.runescape.gamehighscores.controller;

import com.runescape.gamehighscores.custommapping.PlayerComparison;
import com.runescape.gamehighscores.entity.Player;
import com.runescape.gamehighscores.entity.Score;
import com.runescape.gamehighscores.service.CategoryService;
import com.runescape.gamehighscores.service.PlayerService;
import com.runescape.gamehighscores.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/players")
public class PlayerController {

    @Autowired
    PlayerService playerService;

    @Autowired
    ScoreService scoreService;

    @Autowired
    CategoryService categoryService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Player> getAllPlayers() {
        return playerService.getAllPlayers();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public Player getPlayerById(@PathVariable(value = "id") Integer id) {
        return playerService.getPlayerById(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Player insertPlayer(@RequestBody Player player) {
        return playerService.insertPlayer(player);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}")
    public void updatePlayer(@RequestBody Player player, @PathVariable(value = "id") Integer id) {
        playerService.updatePlayer(player, id);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public void deletePlayer(@PathVariable(value = "id") Integer id) {
        playerService.deletePlayer(id);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}/scores")
    public List<Score> getAllScoresByPlayerId(@PathVariable(value = "id") Integer id) {
        return scoreService.getAllScoresByPlayerId(id);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{id}/scores")
    public Score insertPlayerScore(@PathVariable(value = "id") Integer id, @RequestBody Score score) {
        return scoreService.insertPlayerScore(score, id);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}/scores/{scoreId}")
    public void updatePlayerScore(@PathVariable(value = "id") Integer id, @RequestBody Score score,
                                   @PathVariable(value = "scoreId") Integer scoreId) {
        scoreService.updatePlayerScore(score, id, scoreId);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}/scores/{scoreId}")
    public void deletePlayerScore(@PathVariable(value = "id") Integer id, @PathVariable(value = "scoreId") Integer scoreId) {
        scoreService.deletePlayerScore(scoreId, id);
    }

    // OPTIONAL: Have the ability to compare two players
    @RequestMapping(method = RequestMethod.GET, value = "/{id1}/compare/{id2}")
    public List<PlayerComparison> compareToPlayer(@PathVariable(value = "id1") Integer id1, @PathVariable(value = "id2") Integer id2) {
        return playerService.compareToPlayer(id1, id2);
    }

    // OPTIONAL: Have the ability to do a search by player name
    @RequestMapping(method = RequestMethod.GET, value = "/search/{name}")
    public List<Player> searchPlayerByName(@PathVariable(value = "name") String name) {
        return playerService.searchPlayerByName(name);
    }

}
