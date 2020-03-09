package com.example.RestaurantManager.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Restaurant {
    String name;
    Location location;
    List<Category> categories;
    Menu menu;

    public Restaurant(String name, Location location, List<Category> categories )
    {
        this.categories = new ArrayList<>();
        this.name = name;
        this.location = location;
        this.categories = categories;
        this.menu = new Menu();
    }

    public Restaurant(String name, Location location, String category )
    {
        categories = new ArrayList<>();
        this.name = name;
        this.location = location;
        for (Category aCat: Category.values())
        {
            if (category.equals(aCat.toString()))
            {
                categories.add(aCat);
            }
        }

        this.menu = new Menu();
    }

    public JSONObject getRestaurantJSON()
    {
        JSONObject restaurantObject = new JSONObject();
        restaurantObject.put("Name", name);
        restaurantObject.put("Location", location.getJSON());
        JSONArray catArray = new JSONArray();
        for (Category category: categories)
        {
            catArray.put(category.toString());
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

    public List<Category> getRestCategories()
    {
        return categories;
    }

    public Menu getMenu()
    {
        return menu;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setLocation(Location location)
    {
        this.location = location;
    }

    public void addCategory(Category category)
    {
        categories.add(category);
    }

    public void setMenu(Menu menu)
    {
        this.menu = menu;
    }

    public String addMenuItem(MenuItem menuItem)
    {
        return menu.addItem(menuItem);
    }

    public boolean removeMenuItem(String key)
    {
        return menu.removeItem(key);
    }

    public String removeMenuItem(MenuItem menuItem)
    {
        return menu.removeItem(menuItem);
    }

    public static boolean validCategory(String cat)
    {
        for (Category aCat: Category.values())
        {
            if (cat.equals(aCat.toString()))
            {
                return true;
            }
        }
        return false;
    }

    public static List<String> getCategories()
    {
        List<String> categories =  new ArrayList<>();
        Category[] catObjects = Category.values();

        for (Category catObj : catObjects)
        {
            categories.add(catObj.toString());
        }
        return categories;
    }

    public enum Category{Pizza, Fastfood, Steak, Seafood,  Mexican, Chinese, Fuzion, Fine_Dinning};
}
