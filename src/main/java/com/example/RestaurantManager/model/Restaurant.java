package com.example.RestaurantManager.model;

import com.example.RestaurantManager.database.RestaurantDBConnection;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Restaurant {
    String name;
    Location location;
    List<String> categories;

    public Restaurant(String name, Location location, List<String> categories )
    {
        this.categories = new ArrayList<>();
        this.name = name;
        this.location = location;
        this.categories = categories;
    }

    public Restaurant(String name, Location location, String category )
    {
        categories = new ArrayList<>();
        this.name = name;
        this.location = location;
        if (validCategory(category))
        {
            categories.add(category);
        }
    }

    public JSONObject getRestaurantJSON()
    {
        JSONObject restaurantObject = new JSONObject();
        restaurantObject.put("Name", name);
        restaurantObject.put("Location", location.getJSON());
        JSONArray catArray = new JSONArray();
        for (String category: categories)
        {
            catArray.put(category);
        }


        restaurantObject.put("Categories", catArray);
        return restaurantObject;
    }

    public String getName()
    {
        return name;
    }

    public Location getLocation()
    {
        return location;
    }

    public List<String> getRestCategories()
    {
        return categories;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setLocation(Location location)
    {
        this.location = location;
    }

    public void addCategory(String category)
    {
        categories.add(category);
    }

    public void addCategories(List<String> categories)
    {
        for (String category: categories)
        {
            categories.add(category);
        }
    }

    public static boolean validCategory(String cat)
    {
        RestaurantDBConnection restaurantDBConnection = new RestaurantDBConnection();
        List<String> values = restaurantDBConnection.getCategories();
        for (String aCat: values)
        {
            if (cat.equals(aCat))
            {
                return true;
            }
        }
        return false;
    }

    public static List<String> getCategories()
    {
        RestaurantDBConnection restaurantDBConnection = new RestaurantDBConnection();
        List<String> values = restaurantDBConnection.getCategories();
        return values;
    }

    public enum Category{Pizza, Fastfood, Steak, Seafood,  Mexican, Chinese, Fuzion, Fine_Dinning};
}
