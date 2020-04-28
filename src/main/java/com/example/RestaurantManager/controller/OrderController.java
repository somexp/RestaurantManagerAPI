package com.example.RestaurantManager.controller;

import com.example.RestaurantManager.model.*;
import com.example.RestaurantManager.services.OrderService;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value="/order")
public class OrderController {

    @RequestMapping(value="send", method = RequestMethod.POST)
    public ResponseEntity<String> sendOrder(@RequestParam("orders") String orders) {

        try {
            JSONParser parser = new JSONParser();
            org.json.simple.JSONObject orderObject = new JSONObject();
            org.json.simple.JSONArray itemArray = new JSONArray();


            String id = "";
            String restaurantId = "";
            double baseAmount = 0.0;
            double tax = 0.0;
            double total = 0.0;
            String status = "";
            String deliveryAddress = "";
            String deliveryInstructions = "";

            Map<String, OrderItem> orderItems = new HashMap<>();

            try {
                orderObject = (org.json.simple.JSONObject) parser.parse(orders);
            }
            catch (Exception e)
            {
                e.printStackTrace();
                return new ResponseEntity("Failed to parse order: " + orders, HttpStatus.BAD_REQUEST);
            }

            String currentField = "id";
            try
            {
                id = orderObject.get("id").toString();
                currentField = "restaurantId";
                restaurantId = orderObject.get("restaurantId").toString();
                currentField = "baseAmount";
                baseAmount = getDoubleFromObject(orderObject.get("baseAmount"));
                currentField = "tax";
                tax = getDoubleFromObject(orderObject.get("tax"));
                currentField = "amount";
                total = getDoubleFromObject(orderObject.get("amount"));
                currentField = "status";
                status = orderObject.get("status").toString();
                currentField = "deliveryAddr";
                deliveryAddress = orderObject.get("deliveryAddr").toString();
                currentField = "deliveryInst";
                deliveryInstructions = orderObject.get("deliveryInst").toString();
            }
            catch (Exception e)
            {
                e.printStackTrace();
                return new ResponseEntity("An expected field, " + currentField + ", may be missing or in an unexpected format." +
                        " Failed to parse order. An expected field may be missing: " + orders, HttpStatus.BAD_REQUEST);
            }


            try {
                itemArray = (org.json.simple.JSONArray) orderObject.get("orderMenuItems");
            }
            catch (Exception e)
            {
                e.printStackTrace();
                return new ResponseEntity("Failed to get items out of: " + orders, HttpStatus.INTERNAL_SERVER_ERROR);
            }

            Order orderObj = new Order(id, restaurantId, baseAmount, tax, total, status, deliveryAddress, deliveryInstructions);

            try {
                for (int i = 0; i < itemArray.size(); i++)
                {
                    org.json.simple.JSONObject itemObject = (org.json.simple.JSONObject) itemArray.get(i);

                    String itemId = (String) itemObject.get("id");
                    double price = getDoubleFromObject(itemObject.get("price"));
                    int quantity = getIntegerFromObject(itemObject.get("qty"));

                    OrderItem orderItem = new OrderItem("0", id, itemId, price, quantity);
                    orderObj.addOrderItem(String.valueOf(i), orderItem);
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
                return new ResponseEntity("Failed to get items out of: " + orders, HttpStatus.INTERNAL_SERVER_ERROR);
            }

            if (OrderService.addOrder(orderObj))
            {
                return new ResponseEntity(orders, HttpStatus.OK);
            }
            else
            {
                return new ResponseEntity<>("Unable to process order: " + orders, HttpStatus.INTERNAL_SERVER_ERROR);
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
            return new ResponseEntity<>("An issue occured.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    //@CrossOrigin(origins = "http://3.88.210.26:8080")
    @RequestMapping(value = "orders", method = RequestMethod.GET)
    public ResponseEntity<String> getOrders() {

        try {
            org.json.JSONArray orderArray = OrderService.getOrdersJSON();
            String ordersString = orderArray.toString();
            System.out.println(ordersString);
            return new ResponseEntity<>(ordersString, HttpStatus.OK);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return new ResponseEntity<>("An issue occurred.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private double getDoubleFromObject(Object obj)
    {
        try
        {
            double d = (Double) obj;
            return d;
        }
        catch (ClassCastException cce1)
        {
            try {
                Long l = (Long) obj;
                double d = l.doubleValue();
                return d;
            }
            catch (ClassCastException cce2)
            {
                Long l = (Long) obj;
                double d = Double.valueOf(l);
                return d;
            }
        }
    }


    private int getIntegerFromObject(Object obj)
    {
        try
        {
            int d = (Integer) obj;
            return d;
        }
        catch (ClassCastException cce1)
        {
            Long l = (Long) obj;
            int d = l.intValue();
            return d;
        }
    }

}
