package com.limbo.shopping.model;

import com.limbo.shopping.service.OrderMgr;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Break.D on 7/10/16.
 */
public class User {

    private String username;
    private String password;
    private int id;
    private Date rdate;
    private String phone;
    private String addr;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getRdate() {
        return rdate;
    }

    public void setRdate(Date rdate) {
        this.rdate = rdate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public int buy(Cart c) {
        SalesOrder so = new SalesOrder();
        so.setUser(this);
        so.setAddr(this.getAddr());
        so.setStatus(0);
        so.setODate(new Date());
        List<SalesItem> salesItems = new ArrayList<>();
        List<CartItem> cartItems = c.getItems();
        for (int i = 0; i < cartItems.size(); i++) {
            SalesItem si = new SalesItem();
            CartItem ci = cartItems.get(i);
            si.setProduct(ci.getProduct());
            si.setCount(ci.getCount());
            si.setUnitPrice(ci.getProduct().getMemberPrice());
            salesItems.add(si);
        }
        so.setItems(salesItems);
        return OrderMgr.getInstance().add(so);
    }
}

