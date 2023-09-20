package model;

import java.util.List;

public class Product {
    private String proName;
    private int proId;
    private double costPrice;
    private double sellPrice;
    private int supId;


    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public int getProId() {
        return proId;
    }

    public void setProId(int proId) {
        this.proId = proId;
    }

    public double getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(double costPrice) {
        this.costPrice = costPrice;
    }

    public double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public int getSupId() {
        return supId;
    }

    public void setSupId(int supId) {
        this.supId = supId;
    }


//    public int getProCurrentStock() {
//        return proCurrentStock;
//    }
//
//    public void setProCurrentStock(int proCurrentStock) {
//        this.proCurrentStock = proCurrentStock;
//    }


}
