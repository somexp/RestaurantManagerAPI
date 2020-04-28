package com.example.RestaurantManager.model;

import org.json.JSONObject;

import java.util.*;

public class Menu {
    Map<String, MenuItem> menuList = new HashMap<>();

    public Menu()
    {
    }

    public String addItem(String id, MenuItem menuItem)
    {
        menuList.put(id, menuItem);
        return id;
    }

    public JSONObject getMenuJSON()
    {
        JSONObject menuObj = new JSONObject();

        for(String key: menuList.keySet())
        {
            MenuItem menuItem = menuList.get(key);
            menuObj.put(key, menuItem.getMenuItemJSON());
        }

        return menuObj;
    }

    public MenuItem getItem(String id)
    {
        return menuList.get(id);
    }

    public boolean removeItem(String id)
    {
        if (!menuList.containsKey(id))
        {
            return false;
        }

        menuList.remove(id);

        return true;
    }

    public String removeItem(MenuItem menuItem)
    {
        for(String key : menuList.keySet())
        {
            if (menuItem.equals(menuList.get(key)))
            {
                menuList.remove(key);
                return key;
            }
        }

        return "Not Found";
    }


}
