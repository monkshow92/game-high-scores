package com.runescape.gamehighscores.repository;

import com.runescape.gamehighscores.entity.Player;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface PlayerDao extends CrudRepository<Player, Integer> {

    Optional<Player> findById(Integer id);

    List<Player> findByNameContainingIgnoreCase(String name);

}
