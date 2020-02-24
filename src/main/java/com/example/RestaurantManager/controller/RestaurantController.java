package com.example.RestaurantManager.controller;

import com.example.RestaurantManager.model.RestaurantContainer;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value="/restaurant")
public class RestaurantController {

    String SUCCESS_RESPONSE = "success";


    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseEntity<String> listRestaurants() {

        String restaurants = "";

        try
        {
            JSONObject restaurantsJSON = RestaurantContainer.getRestaurantsJSON();
            restaurants = restaurantsJSON.toString();
        }
        catch(Exception e)
        {
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity(restaurants, HttpStatus.OK);
    }

    @RequestMapping(value = "/{restaurantId}}", method = RequestMethod.GET)
    public ResponseEntity<String> getRestaurant(@PathVariable("restaurantId") String restaurantId) {

        String restaurant = "";

        try
        {
            JSONObject restaurantJSON = RestaurantContainer.getRestaurantJSON(restaurantId);
            restaurant = restaurantJSON.toString();
        }
        catch(Exception e)
        {
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity(restaurant, HttpStatus.OK);
    }
}
