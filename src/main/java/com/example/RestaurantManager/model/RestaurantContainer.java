package com.example.RestaurantManager.model;

import java.sql.SQLException;
import java.util.*;

import com.example.RestaurantManager.database.MenuDBConnection;
import com.example.RestaurantManager.database.RestaurantDBConnection;
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

        RestaurantDBConnection restaurantDBConnection = new RestaurantDBConnection();

        int id = restaurantDBConnection.addRestaurant(name, location.getStreet(), location.getCity(), location.getState(), location.getZipcode(), 0, categories);

        String restaurantId = String.valueOf(id);

        restaurantMap.put(restaurantId, restaurant);

        return restaurantId;
    }

    public static String addRestaurant(String name, Location location, String category)
    {
        Restaurant restaurant = new Restaurant(name, location, category);

        RestaurantDBConnection restaurantDBConnection = new RestaurantDBConnection();

        List<String> categories = new ArrayList<>();
        categories.add(category);

        int id = restaurantDBConnection.addRestaurant(name, location.getStreet(), location.getCity(), location.getState(), location.getZipcode(), 0, categories);

        String restaurantId = String.valueOf(id);

        restaurantMap.put(restaurantId, restaurant);

        return restaurantId;
    }

    public static JSONObject getRestaurantsJSON()
    {
        RestaurantDBConnection restaurantDBConnection = new RestaurantDBConnection();
        try {
            restaurantMap = restaurantDBConnection.getRestaurants();
            JSONObject it = new JSONObject();
            for (String key : restaurantMap.keySet()) {
                Restaurant restaurant = restaurantMap.get(key);
                JSONObject restaurantObj = restaurant.getRestaurantJSON();
                it.put(key, restaurantObj);
            }
            return it;
        }
        catch(SQLException sqle)
        {
            sqle.printStackTrace();
            return null;
        }
    }

    public static JSONObject getRestaurantJSON(String id)
    {
        RestaurantDBConnection restaurantDBConnection = new RestaurantDBConnection();
        try
        {
            restaurantMap = restaurantDBConnection.getRestaurants();
            JSONObject it = new JSONObject();

            Restaurant restaurant = restaurantDBConnection.getRestaurant(id);
            //Restaurant restaurant = restaurantMap.get(uuid);
            JSONObject restaurantObj = restaurant.getRestaurantJSON();
            return restaurantObj;
        }
        catch(SQLException sqle)
        {
            sqle.printStackTrace();
            return null;
        }
    }

    public static JSONObject getMenuJSON(String restaurantId)
    {
        MenuDBConnection menuDBConnection = new MenuDBConnection();
        try
        {
            Menu menu = menuDBConnection.getMenu(restaurantId);
            //Restaurant restaurant = restaurantMap.get(uuid);
            JSONObject menuJSON = menu.getMenuJSON();
            return menuJSON;
        }
        catch(SQLException sqle)
        {
            sqle.printStackTrace();
            return null;
        }
    }

    public static JSONObject getMenuItemJSON(String restaurantId, String menuItemId)
    {
        MenuDBConnection menuDBConnection = new MenuDBConnection();
        try
        {
            MenuItem menuItem = menuDBConnection.getMenuItem(menuItemId, restaurantId);

            if (menuItem==null)
            {
                return new JSONObject();
            }
            //Restaurant restaurant = restaurantMap.get(uuid);
            JSONObject menuItemJSON = menuItem.getMenuItemJSON();
            return menuItemJSON;
        }
        catch(SQLException sqle)
        {
            sqle.printStackTrace();
            return null;
        }
    }


    public static Map<String, Restaurant> getRestaurantMap()
    {
        return restaurantMap;
    }

    public static Restaurant getRestaurant(String uuid)
    {
        return restaurantMap.get(uuid);
    }

    public static boolean removeRestaurant(String id)
    {
        if (!restaurantMap.containsKey(id))
        {
            return false;
        }

        RestaurantDBConnection restaurantDBConnection = new RestaurantDBConnection();

        Integer idInt = Integer.valueOf(id);

        if(restaurantDBConnection.deleteRestaurant(idInt))
        {
            restaurantMap.remove(id);
            return true;
        }
        else
        {
            return false;
        }
    }

    public static String addMenuItem(String restaurantId, MenuItem menuItem)
    {
        MenuDBConnection menuDBConnection = new MenuDBConnection();
        try
        {
            int itemId = menuDBConnection.addMenuItem(restaurantId, menuItem.getName(),
                    menuItem.getDescription(), menuItem.getPrice());
            //Restaurant restaurant = restaurantMap.get(uuid);
            String idString = String.valueOf(itemId);
            return idString;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean removeMenuItem(String restaurantId, String menuItemId)
    {
        MenuDBConnection menuDBConnection = new MenuDBConnection();
        try
        {
            boolean success = menuDBConnection.deleteMenuItem(restaurantId, menuItemId);
            //Restaurant restaurant = restaurantMap.get(uuid);
            return success;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }



}
