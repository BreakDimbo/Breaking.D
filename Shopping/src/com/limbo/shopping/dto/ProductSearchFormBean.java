package com.limbo.shopping.dto;

/**
 * Created by Break.D on 7/13/16.
 */
public class ProductSearchFormBean {
    private int categoryId;

    private String name;

    private double lowNormalPrice;

    private double highNormalPrice;

    private double lowMemberPrice;

    private double highMemberPrice;

    private String startDate;

    private String endDate;

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLowNormalPrice() {
        return lowNormalPrice;
    }

    public void setLowNormalPrice(double lowNormalPrice) {
        this.lowNormalPrice = lowNormalPrice;
    }

    public double getHighNormalPrice() {
        return highNormalPrice;
    }

    public void setHighNormalPrice(double highNormalPrice) {
        this.highNormalPrice = highNormalPrice;
    }

    public double getLowMemberPrice() {
        return lowMemberPrice;
    }

    public void setLowMemberPrice(double lowMemberPrice) {
        this.lowMemberPrice = lowMemberPrice;
    }

    public double getHighMemberPrice() {
        return highMemberPrice;
    }

    public void setHighMemberPrice(double highMemberPrice) {
        this.highMemberPrice = highMemberPrice;
    }

    public String getEndDate() {
        if(this.endDate == null) return "";
        else return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStartDate() {
        if(this.startDate == null) return "";
        else return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
}
