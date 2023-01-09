package com.example.restaurantconfirmation;

import java.util.ArrayList;

public class OrdersModel
{
    private String orderGate, totalPrice, status, orderDate, orderID, userID;

    public OrdersModel()
    {
    }

    public OrdersModel(String orderGate, String time, String totalPrice, String status, String orderID, String userID)
    {
        this.orderGate = orderGate;
        this.totalPrice = totalPrice;
        this.status = status;
        this.orderDate = time;
        this.orderID = orderID;
        this.userID = userID;
    }

    public OrdersModel(String orderGate, String time, String totalPrice, String status, ArrayList<OrdersModel> ordersModels)
    {
        this.orderGate = orderGate;
        this.totalPrice = totalPrice;
        this.status = status;
        this.orderDate = time;
    }

    public String getOrderGate() {
        return orderGate;
    }

    public String getStatus() {
        return status;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public String getOrderID() {
        return orderID;
    }

    public String getUserID() {
        return userID;
    }

    public void setOrderGate(String orderGate) {
        this.orderGate = orderGate;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
