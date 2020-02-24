package com.example.RestaurantManager.model;

import org.json.JSONObject;

public class Restaurant {
    String name;
    Location location;
    Category category;
    Menu menu;

    public Restaurant(String name, Location location, Category category )
    {
        this.name = name;
        this.location = location;
        this.category = category;
        this.menu = new Menu();
    }

    public JSONObject getRestaurantJSON()
    {
        JSONObject restaurantObject = new JSONObject();
        restaurantObject.put("Name", name);
        restaurantObject.put("Location", location.toString());
        restaurantObject.put("Category", category);
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

    public Category getCategory()
    {
        return category;
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

    public void setCategory(Category category)
    {
        this.category = category;
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

    public enum Category{Pizza, Fastfood, Steak, Seafood,  Mexican, Chinese,Fuzion, Fine_Dinning};
}
