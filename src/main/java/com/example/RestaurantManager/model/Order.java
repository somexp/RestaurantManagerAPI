package com.example.RestaurantManager.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Order {
    private String id;
    private String restaurantId;
    private double baseAmount;
    private double tax;
    private double total;
    private String status;
    private String deliveryAddress;
    private String deliveryInstructions;
    private Map<String, OrderItem> orderItems;

    public Order(String id, String restaurantId, double baseAmount,
                 double tax, double total, String status,
                 String deliveryAddress, String deliveryInstructions)
    {
        this.id = id;
        this.restaurantId =restaurantId;
        this.baseAmount = baseAmount;
        this.tax = tax;
        this.total = total;
        this.status = status;
        this.deliveryAddress = deliveryAddress;
        this.deliveryInstructions = deliveryInstructions;
        orderItems = new HashMap<>();
    }

    public String getId()
    {
        return id;
    }

    public String getRestaurantId()
    {
        return restaurantId;
    }

    public double getBaseAmount()
    {
        return baseAmount;
    }

    public double getTax()
    {
        return tax;
    }

    public double getTotal()
    {
        return total;
    }

    public String getStatus()
    {
        return status;
    }

    public String getDeliveryAddress()
    {
        return deliveryAddress;
    }

    public String getDeliveryInstructions()
    {
        return deliveryInstructions;
    }

    public void addOrderItem(String id, OrderItem item)
    {
        orderItems.put(id, item);
    }

    public Map<String, OrderItem> getOrderItems()
    {
        return orderItems;
    }

    public JSONObject getOrderJSON()
    {
        JSONObject orderObject = new JSONObject();
        JSONArray itemArray = new JSONArray();

        for (String key: orderItems.keySet())
        {
            OrderItem orderItem = orderItems.get(key);
            itemArray.put(orderItem);
        }
        orderObject.put("id", id);
        orderObject.put("restaurantId", restaurantId);
        orderObject.put("baseAmount", baseAmount);
        orderObject.put("tax", tax);
        orderObject.put("total", total);
        orderObject.put("status", status);
        orderObject.put("deliveryAddress", deliveryAddress);
        orderObject.put("deliveryInstructions", deliveryInstructions);
        orderObject.put("items", orderItems);
        return orderObject;
    }
}
