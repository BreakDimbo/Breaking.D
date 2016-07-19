package com.limbo.shopping.dao;

import com.limbo.shopping.model.Category;

import java.util.List;

/**
 * Created by Break.D on 7/11/16.
 */
public interface CategoryDao {
    void save(Category c);

    void delete(int id, int pid);

    void update(Category c);

    void getCategories(List<Category> categories, int id);

    void saveChild(int pid, int grade, String name, String descr);

    Category loadById(int id);

}
