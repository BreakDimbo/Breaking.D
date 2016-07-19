package com.limbo.shopping.dao.impl;

import com.limbo.shopping.service.CategoryMgr;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Break.D on 7/12/16.
 */
public class CategoryImplTest {
    @Test
    public void delete() throws Exception {
        CategoryMgr.getInstance().delete(13, 5);
    }

}