package com.limbo.shopping.dao;

import com.limbo.shopping.model.Product;

import java.util.List;

/**
 * Created by Break.D on 7/13/16.
 */
public interface ProductDao {
    void save(Product p);

    void delete(Product p);

    void update(Product p);

    /*void getProducts(List<Product> products);*/

    int getProducts(List<Product> products, int pageNum, int pageSize, int lazy);

    Product loadById(int id);

    int find(List<Product> products, int pageNo, int pageSize, String queryStr);

    void getAllProducts(List<Product> products);
}
