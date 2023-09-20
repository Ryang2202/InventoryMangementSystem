package model;

import java.sql.Date;

public class Order {
    private int orderId;
    private Date orderDate;
    private int orderQuantity;
    private double sellPrice;
    private double orderTotal;
    private int proId;
    private int cusId;
    Product product;

//    public Order(int orderId, String orderDate, int cusId, int proId, int orderQuantity, double orderTotal) {
//    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }


    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public int getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(int orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    public double getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(double orderTotal) {
        this.orderTotal = orderTotal;
    }
    public int getProId() {
        return proId;
    }

    public void setProId(int proId) {
        this.proId = proId;
    }

    public int getCusId() {
        return cusId;
    }

    public void setCusId(int cusId) {
        this.cusId = cusId;
    }

    public double calculateOrderTotal(Product product, int orderQuantity) {
        double sellPrice = product.getSellPrice();
        double orderTotal = sellPrice * orderQuantity;
        return orderTotal;
    }

    public double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(double sellPrice) {
        this.sellPrice = sellPrice;
    }
}


