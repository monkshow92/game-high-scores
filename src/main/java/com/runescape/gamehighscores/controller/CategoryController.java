package com.runescape.gamehighscores.controller;

import com.runescape.gamehighscores.entity.Category;
import com.runescape.gamehighscores.entity.Score;
import com.runescape.gamehighscores.service.CategoryService;
import com.runescape.gamehighscores.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @Autowired
    ScoreService scoreService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public Category getCategoryById(@PathVariable(value = "id") Integer id) {
        return categoryService.getCategoryById(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Category insertCategory(@RequestBody Category category) {
        return categoryService.insertCategory(category);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}")
    public void updateCategory(@RequestBody Category category, @PathVariable(value = "id") Integer id) {
        categoryService.updateCategory(category, id);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public void deleteCategory(@PathVariable(value = "id") Integer id) {
        categoryService.deleteCategory(id);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}/scores")
    public List<Score> getScoresByCategoryId(@PathVariable(value = "id") Integer id) {
        return scoreService.getScoresByCategoryId(id);
    }

}
