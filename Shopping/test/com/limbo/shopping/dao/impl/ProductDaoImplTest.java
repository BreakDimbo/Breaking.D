package com.limbo.shopping.dao.impl;

import com.limbo.shopping.model.Product;
import com.limbo.shopping.service.ProductMgr;
import org.junit.Test;

import java.util.Date;
import java.util.List;

/**
 * Created by Break.D on 7/13/16.
 */
public class ProductDaoImplTest {
    @Test
    public void save() throws Exception {
        Product product = new Product();
        product.setName("奥迪上衣");
        product.setCategoryId(1);
        product.setDescr("是是是");
        product.setMemberPrice(30.2);
        product.setNormalPrice(60);
        product.setPdate(new Date());
        ProductMgr.getInstance().add(product);

    }

    @Test
    public void delete() throws Exception {

    }

    @Test
    public void update() throws Exception {

    }


    /*public void getProducts() throws Exception {
        List<Product> products = ProductMgr.getInstance().getProducts();
        for(Product p : products) {
            System.out.println(p.getName());
            System.out.println(p.getCategory().getName());
        }
    }*/

}