package com.limbo.shopping.service;

import com.limbo.shopping.dao.ProductDao;
import com.limbo.shopping.dao.impl.ProductDaoImpl;
import com.limbo.shopping.model.Product;
import com.limbo.shopping.dto.ProductSearchFormBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Break.D on 7/13/16.
 */
public class ProductMgr {
    private static ProductDao productDao = new ProductDaoImpl();

    private static ProductMgr productMgr;

    private ProductMgr() {
    }


    public static ProductMgr getInstance() {
        if (productMgr == null) {
            productMgr = new ProductMgr();
        }
        return productMgr;
    }

    public void add(Product p) {
        productDao.save(p);
    }

    public void delete(Product p) {
        productDao.delete(p);
    }

    public void delete(int id) {
        Product p = new Product();
        p.setId(id);
        productDao.delete(p);
    }

    //也可以直接在 Dao 层实现,减少与数据库的连接
    public void delete(String[] idArray) {
        for (String id : idArray) {
            int i = Integer.parseInt(id);
            delete(i);
        }
    }

    public void update(Product p) {
        productDao.update(p);
    }

   /* public List<Product> getProducts() {
        List<Product> products = new ArrayList<>();
        productDao.getProducts(products);
        return products;
    }*/

    public int getProducts(List<Product> products, int pageNum, int pageSize, int lazy) {
        return productDao.getProducts(products, pageNum, pageSize, lazy);
    }

    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        productDao.getAllProducts(products);
        return products;
    }

    public Product loadById(int id) {
        return productDao.loadById(id);
    }

    //根据类别找
    public int find(List<Product> products, int pageNo, int pageSize, int categoryId) {
        String queryStr = " where p.categoryid = " + categoryId;
        return productDao.find(products, pageNo, pageSize, queryStr);
    }

    //根据名字和描述找
    public int find(List<Product> products, int pageNo, int pageSize, String keyword) {
        String queryStr = " where p.name like '%" + keyword + "%' or p.descr like '%" + keyword + "%'";
        return productDao.find(products, pageNo, pageSize, queryStr);
    }

    //高级搜索
    public int find(List<Product> products, int pageNo, int pageSize, ProductSearchFormBean bean) {
        String queryStr = " where 1=1 ";
        if(bean.getCategoryId() != -1) {
            queryStr += " and p.categoryid = " + bean.getCategoryId();
        }
        if(bean.getName() != null && !bean.getName().trim().equals("")) {
            queryStr += " and p.name like '%" + bean.getName() + "%'";
        }
        if(bean.getLowNormalPrice() != 0.0) {
            queryStr += " and p.normalprice >= " + bean.getLowNormalPrice();
        }
        if(bean.getHighNormalPrice() != 0.0) {
            queryStr += " and p.normalprice <= " + bean.getHighNormalPrice();
        }
        if(bean.getLowMemberPrice() != 0.0) {
            queryStr += " and p.memberprice >= " + bean.getLowMemberPrice();
        }
        if(bean.getHighMemberPrice() != 0.0) {
            queryStr += " and p.memberprice <= " + bean.getHighMemberPrice();
        }
        if(bean.getStartDate() != null && !bean.getStartDate().trim().equals("")) {
            queryStr += " and p.pdate >= '" + bean.getStartDate() + " 00:00:00'";
        }
        if(bean.getEndDate() != null && !bean.getEndDate().trim().equals("")) {
            queryStr += " and p.pdate <= '" + bean.getEndDate() + " 00:00:00'";
        }
        return productDao.find(products, pageNo, pageSize, queryStr);
    }
}
