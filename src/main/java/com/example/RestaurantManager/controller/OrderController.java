package com.example.RestaurantManager.controller;

import com.example.RestaurantManager.model.Location;
import com.example.RestaurantManager.model.Restaurant;
import com.example.RestaurantManager.model.RestaurantContainer;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(value="/order")
public class OrderController {

    @RequestMapping(value="send", method = RequestMethod.POST)
    public @ResponseBody String sendOrders(@RequestParam("orders") String orders) {

        return orders;
    }

}
