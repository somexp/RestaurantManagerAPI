package com.example.RestaurantManager.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONObject;

public class RestaurantContainer {

    private static Map<String, Restaurant> restaurantMap = new HashMap<>();

    public static String addRestaurant(String name, Location location, List<String> categories)
    {
        for (String cat : categories)
        {
            if (!Restaurant.validCategory(cat))
            {
                return ("Invalid Category: " + cat);
            }
        }
        Restaurant restaurant = new Restaurant(name, location, categories);

        String restaurantId = UUID.randomUUID().toString();
        restaurantMap.put(restaurantId, restaurant);
        return restaurantId;
    }

    public static String addRestaurant(String name, Location location, String category)
    {
        Restaurant restaurant = new Restaurant(name, location, category);

        String restaurantId = UUID.randomUUID().toString();
        restaurantMap.put(restaurantId, restaurant);
        return restaurantId;
    }

    public static JSONObject getRestaurantsJSON()
    {
        JSONObject it = new JSONObject();
        for (String key : restaurantMap.keySet())
        {
            Restaurant restaurant = restaurantMap.get(key);
            JSONObject restaurantObj = restaurant.getRestaurantJSON();
            it.put(key, restaurantObj);
        }
        return it;
    }

    public static JSONObject getRestaurantJSON(String uuid)
    {
        Restaurant restaurant = restaurantMap.get(uuid);

        return restaurant.getRestaurantJSON();
    }

    public static JSONObject getMenuJSON(String restaurantId)
    {
        Restaurant restaurant = restaurantMap.get(restaurantId);

        Menu menu = restaurant.getMenu();

        return menu.getMenuJSON();
    }

    public static JSONObject getMenuItemJSON(String restaurantId, String menuItemId)
    {
        Restaurant restaurant = restaurantMap.get(restaurantId);

        Menu menu = restaurant.getMenu();

        MenuItem menuItem = menu.getItem(menuItemId);

        return menuItem.getMenuItemJSON();
    }


    public static Map<String, Restaurant> getRestaurantMap()
    {
        return restaurantMap;
    }

    public static Restaurant getRestaurant(String uuid)
    {
        return restaurantMap.get(uuid);
    }

    public static boolean removeRestaurant(String uuid)
    {
        System.out.println("11111111111: " + uuid);
        if (!restaurantMap.containsKey(uuid))
        {
            System.out.println("2222222222222222222");
            return false;
        }
        System.out.println("3333333333333333");

        restaurantMap.remove(uuid);
        System.out.println("44444444444444");

        return true;
    }

    public static String removeRestaurant(Restaurant restaurant)
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

    public static String addMenuItem(String restaurantId, MenuItem menuItem)
    {
        if (!restaurantMap.containsKey(restaurantId))
        {
            return "Not Found";
        }
        Restaurant restaurant = restaurantMap.get(restaurantId);
        String menuItemId = restaurant.addMenuItem(menuItem);
        restaurantMap.put(restaurantId, restaurant);
        return menuItemId;
    }

    public static boolean removeMenuItem(String restaurantId, String menuItemId)
    {
        if (!restaurantMap.containsKey(restaurantId))
        {
            return false;
        }

        Restaurant restaurant = restaurantMap.get(restaurantId);

        return restaurant.removeMenuItem(menuItemId);
    }



}
