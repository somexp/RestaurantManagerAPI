package com.example.RestaurantManager.model;

import org.json.JSONObject;

public class OrderItem {
    private String id;
    private String orderId;
    private String itemId;
    private double price;
    private int quantity;

    public OrderItem(String id, String orderId, String itemId, double price, int quantity)
    {
        this.id = id;
        this.orderId = orderId;
        this.itemId = itemId;
        this.price = price;
        this.quantity = quantity;
    }

    public String getId()
    {
        return id;
    }

    public String getOrderId()
    {
        return orderId;
    }

    public String getItemId()
    {
        return itemId;
    }

    public double getPrice()
    {
        return price;
    }

    public int getQuantity()
    {
        return quantity;
    }

    public JSONObject getItemJSON()
    {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("orderId", orderId);
        jsonObject.put("itemId", itemId);
        jsonObject.put("price", price);
        jsonObject.put("quantity", quantity);
        return jsonObject;
    }
}
