package com.example.RestaurantManager.model;

import org.json.JSONArray;
import org.json.JSONObject;

public class Location {

    private String street;
    private String city;
    private String state;
    private String zipcode;

    public Location(String street, String city, String state, String zipcode)
    {
        this.street = street;
        this.city = city;
        this.state = state;
        this.zipcode = zipcode;
    }

    /**public String toString()
    {
        return number + " " + street.toString();
    }**/
    public JSONObject getJSON()
    {
        JSONObject jsonLoc = new JSONObject();
        jsonLoc.put("Street", street);
        jsonLoc.put("City", city);
        jsonLoc.put("State", state);
        jsonLoc.put("Zipcode", zipcode);
        return jsonLoc;
    }

    public String getStreet()
    {
        return street;
    }

    public String getCity()
    {
        return city;
    }

    public String getState()
    {
        return state;
    }

    public String getZipcode()
    {
        return zipcode;
    }



    public void setStreet()
    {
        this.street = street;
    }

    public void setCity()
    {
        this.city = city;
    }

    public void setState()
    {
        this.state = state;
    }

    public void setZipcode()
    {
        this.zipcode = zipcode;
    }
}
