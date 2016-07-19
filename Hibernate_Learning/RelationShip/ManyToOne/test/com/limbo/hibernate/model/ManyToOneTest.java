package com.limbo.hibernate.model;

import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by main on 7/2/16.
 */
public class ManyToOneTest {

    @Test
    public void many2OneBifk() {
        Configuration cfg = new Configuration();
        cfg.configure();
        new SchemaExport(cfg).create(true, true);
    }

}