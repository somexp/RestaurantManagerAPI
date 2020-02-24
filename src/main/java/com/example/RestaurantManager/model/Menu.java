package com.example.RestaurantManager.model;

import java.util.*;

public class Menu {
    Map<String, MenuItem> menuList = new HashMap<>();

    public Menu()
    {
    }

    public String addItem(MenuItem menuItem)
    {
        String uuid = UUID.randomUUID().toString();
        menuList.put(uuid, menuItem);
        return uuid;
    }

    public MenuItem getItem(String uuid)
    {
        return menuList.get(uuid);
    }

    public boolean removeItem(String uuid)
    {
        if (!menuList.containsKey(uuid))
        {
            return false;
        }

        menuList.remove(uuid);

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
