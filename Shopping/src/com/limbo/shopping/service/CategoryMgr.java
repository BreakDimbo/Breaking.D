package com.limbo.shopping.service;

import com.limbo.shopping.dao.CategoryDao;
import com.limbo.shopping.dao.impl.CategoryImpl;
import com.limbo.shopping.model.Category;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Break.D on 7/11/16.
 */
public class CategoryMgr {
    private static CategoryDao categoryDao = new CategoryImpl();
    private static CategoryMgr categoryMgr;

    private CategoryMgr() {
        super();
    }

    public static CategoryMgr getInstance() {
        if (categoryMgr == null) {
            categoryMgr = new CategoryMgr();
        }
        return categoryMgr;
    }

    public void add(Category c) {
        categoryDao.save(c);
    }

    public void addTopCategory(String name, String descr) {
        Category category = new Category();
        category.setId(-1);
        category.setName(name);
        category.setDescr(descr);
        category.setLeaf(true);
        category.setPid(0);
        category.setGrade(1);

        add(category);

    }

    public void addChildCategory(int pid, int grade, String name, String descr) {
        categoryDao.saveChild(pid, grade, name, descr);
    }

    public void delete(int id, int pid) {
        categoryDao.delete(id, pid);
    }

    public void update(Category c) {
        categoryDao.update(c);
    }


    public List<Category> getCategories() {
        List<Category> categories = new ArrayList<>();
        categoryDao.getCategories(categories, 0);
        return categories;
    }

    public List<Category> getTopCategories(List<Category> categories) {
        List<Category> topCategories = new ArrayList<>();
        for (Category c : categories) {
            if (c.getPid() == 0) {
                topCategories.add(c);
            }
        }
        return topCategories;
    }

    public List<Category> getChildren(List<Category> categories, int pid, int grade) {
        List<Category> children = new ArrayList<>();
        for (Category c : categories) {
            if (c.getPid() == pid && c.getGrade() == grade) {
                children.add(c);
            }
        }

        return children;
    }

    public Category loadById(int id) {
        return categoryDao.loadById(id);
    }
}
