package com.runescape.gamehighscores.repository;

import com.runescape.gamehighscores.entity.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CategoryDao extends CrudRepository<Category, Integer> {

    Optional<Category> findByName(String name);

    Optional<Category> findById(Integer id);

}
