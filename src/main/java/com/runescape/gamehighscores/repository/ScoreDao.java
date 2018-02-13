package com.runescape.gamehighscores.repository;

import com.runescape.gamehighscores.entity.Score;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ScoreDao extends CrudRepository<Score, Integer> {

    List<Score> findTop10ByCategoryIdOrderByLevelDescXpDesc(Integer id);

    List<Score> findByPlayerId(Integer id);

    boolean existsByPlayerIdAndCategoryName(Integer id, String categoryName);

}
