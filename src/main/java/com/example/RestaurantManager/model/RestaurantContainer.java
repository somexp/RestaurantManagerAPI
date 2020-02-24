package com.example.RestaurantManager.model;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONObject;

public class RestaurantContainer {

    private static Map<String, Restaurant> restaurantMap = new HashMap<>();

    public static String addRestaurant(String name, Location location, Restaurant.Category category)
    {
        Restaurant restaurant = new Restaurant(name, location, category);

        String restaurantId = UUID.randomUUID().toString();
        restaurantMap.put(restaurantId, restaurant);
        return restaurantId;
    }

    public static String getRestaurantsJSON()
    {
        JSONObject it = new JSONObject();
        for (String key : restaurantMap.keySet())
        {
            Restaurant restaurant = restaurantMap.get(key);
            JSONObject restaurantObj = new JSONObject(restaurant.getRestaurantJSON());
            it.put(key, restaurantObj);
        }
        return it.toString();
    }

    public static Map<String, Restaurant> getRestaurantMap()
    {
        return restaurantMap;
    }

    public Restaurant getRestaurant(String uuid)
    {
        return restaurantMap.get(uuid);
    }

    public boolean removeItem(String uuid)
    {
        if (!restaurantMap.containsKey(uuid))
        {
            return false;
        }

        restaurantMap.remove(uuid);

        return true;
    }

    public String removeRestaurant(Restaurant restaurant)
    {
        for(String key : restaurantMap.keySet())
        {
            if (restaurant.equals(restaurantMap.get(key)))
            {
                restaurantMap.remove(key);
                return key;
            }
        }

        return "Not Found";
    }



}
