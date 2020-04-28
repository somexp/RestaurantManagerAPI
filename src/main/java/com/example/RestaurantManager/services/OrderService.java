package com.example.RestaurantManager.services;

import com.example.RestaurantManager.database.OrderDBConnection;
import com.example.RestaurantManager.model.Order;
import com.example.RestaurantManager.model.OrderItem;
import org.json.JSONObject;
import org.json.JSONArray;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderService {

    public static boolean addOrder(Order order)
    {

        String orderId = order.getId();
        String restaurantId = order.getRestaurantId();
        int orderIdInt = Integer.valueOf(orderId);
        int restaurantIdInt = Integer.valueOf(restaurantId);
        double baseAmount = order.getBaseAmount();
        double tax = order.getTax();
        double total = order.getTotal();
        String status = order.getStatus();
        String deliveryAddress = order.getDeliveryAddress();
        String deliveryInstructions = order.getDeliveryInstructions();

        Map<String, OrderItem> orderItems = order.getOrderItems();

        System.out.println("HAve " + orderItems.size() + " order items.");
        OrderDBConnection orderDBConnection = new OrderDBConnection();
        if (!orderDBConnection.addOrder( orderIdInt, restaurantIdInt, baseAmount, tax,
                total, status, deliveryAddress, deliveryInstructions))
        {
            return false;
        }

        for (String key : orderItems.keySet())
        {
            OrderItem orderItem = orderItems.get(key);
            String itemId = orderItem.getItemId();
            int itemIdInt = Integer.valueOf(itemId);
            double price = orderItem.getPrice();
            int quantity = orderItem.getQuantity();
            if (orderDBConnection.addOrderItem(orderIdInt, itemIdInt, price, quantity) < 0)
            {
                return false;
            }
        }
        return true;
    }

    public static JSONArray getOrdersJSON()
    {

        try {
            OrderDBConnection orderDBConnection = new OrderDBConnection();

            List<Order> orders = orderDBConnection.getOrders();

            System.out.println("Length of array: " + orders.size());

            org.json.JSONArray orderArray = new org.json.JSONArray();

            for (Order order: orders)
            {
                org.json.JSONObject orderObj = order.getOrderJSON();
                orderArray.put(orderObj);
            }
            return orderArray;
        }
        catch(SQLException sqle)
        {
            sqle.printStackTrace();
            return null;
        }
    }
}
