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
@RequestMapping(value="/restaurant")
public class RestaurantController {

    String SUCCESS_RESPONSE = "success";

    //@CrossOrigin(origins = "http://3.88.210.26:8080")
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
    //@CrossOrigin(origins = "http://3.88.210.26:8080")
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


    //@CrossOrigin(origins = "http://3.88.210.26:8080")
    @RequestMapping(value = "/categories", method = RequestMethod.GET)
    public ResponseEntity<String> getCategories() {

        String categories = "";
        try
        {
            JSONArray catArray = new JSONArray();

            List<String> catList = Restaurant.getCategories();

            for (String category : catList)
            {
                catArray.put(category);
            }
            categories = catArray.toString();
        }
        catch(Exception e)
        {
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity(categories, HttpStatus.OK);
    }

    //@CrossOrigin(origins = "http://3.88.210.26:8080")
    @RequestMapping(value="add", method = RequestMethod.POST)
    public @ResponseBody boolean addRestaurant(@RequestParam("name") String name,
                                               @RequestParam("categories") String categories,
                                               @RequestParam("street") String street,
                                               @RequestParam("city") String city,
                                               @RequestParam("state") String state,
                                               @RequestParam("zip") String zip) {
        if(!Restaurant.validCategory(categories))
        {
            return false;
        }

        //addRestaurant(String name, Location location, String category)
        Location location1 = new Location(street, city, state, zip);

        String restaurantId = RestaurantContainer.addRestaurant(name, location1, categories);

        return ((restaurantId!=null)&&(!restaurantId.isEmpty()));

    }

    //@CrossOrigin(origins = "http://3.88.210.26:8080")
    @RequestMapping(value="delete", method = RequestMethod.GET)
    public @ResponseBody boolean deleteRestaurant(@RequestParam("restaurantId") String restaurantId) {
        boolean success = RestaurantContainer.removeRestaurant(restaurantId);
        return success;
    }
}
