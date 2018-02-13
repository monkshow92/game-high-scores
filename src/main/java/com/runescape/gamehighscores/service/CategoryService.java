package com.runescape.gamehighscores.service;

import com.runescape.gamehighscores.exception.CategoryNotFoundException;
import com.runescape.gamehighscores.exception.OverallCategoryCannotBeDeletedException;
import com.runescape.gamehighscores.repository.CategoryDao;
import com.runescape.gamehighscores.entity.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {

    Logger logger = LoggerFactory.getLogger(CategoryService.class);

    @Autowired
    CategoryDao categoryDao;

    public List<Category> getAllCategories() {
        logger.info("Getting all categories");
        List<Category> categories = new ArrayList<>();
        categoryDao.findAll().
                forEach(categories::add);
        return categories;
    }

    public Category getCategoryById(Integer id) {
        logger.info("Getting category with id: " + id);
        return categoryDao.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    public Category insertCategory(Category category) {
        category = categoryDao.save(category);
        logger.info("Inserted category with id: " + category.getId());
        return category;
    }

    @ResponseStatus(value = HttpStatus.OK)
    public void updateCategory(Category category, Integer id) {
        Category tempCategory = categoryDao.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));
        tempCategory.setName(category.getName());
        categoryDao.save(tempCategory);
        logger.info("Updated category with id: " + id);
    }

    @ResponseStatus(value = HttpStatus.OK)
    public void deleteCategory(Integer id) {
        if (!categoryDao.exists(id)) {
            throw new CategoryNotFoundException(id);
        }
        if (id == 1) {
            throw new OverallCategoryCannotBeDeletedException();
        }
        categoryDao.delete(id);
        logger.info("Deleted category with id: " + id);
    }

}
