package com.example.RestaurantManager.controller;

import com.example.RestaurantManager.model.Location;
import com.example.RestaurantManager.model.Restaurant;
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

    @RequestMapping(value = "/{restaurantId}", method = RequestMethod.GET)
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


    @PostMapping("/addRestaurant")
    public String processForm(String name, String location, String category) {

        if(!validLocation(location))
        {
            return "Location data not valid";
        }

        if(!Restaurant.validCategory(category))
        {
            return "Category not valid";
        }

        //addRestaurant(String name, Location location, Restaurant.Category category)
        String[] splt = location.split(" ");
        int number = Integer.valueOf(splt[0]);
        String street = splt[1];
        Location location1 = new Location(number, street);

        String restaurantId = RestaurantContainer.addRestaurant(name, location1, category);

        return "restaurantId";
    }

    private boolean validLocation(String location)
    {
        String[] splt = location.split(" ");
        try {
            int number = Integer.valueOf(splt[0]);
        }
        catch(Exception e)
        {
            return false;
        }

        String street = splt[1];
        return Location.validStreet(street);

    }
}
