package com.example.RestaurantManager.model;

public class Location {

    private Street street;
    private int number;

    public Location(int number, Street street)
    {
        this.number = number;
        this.street = street;
    }

    public String toString()
    {
        return number + " " + street.toString();
    }

    public int getNumber()
    {
        return number;
    }

    public Street getStreet()
    {
        return street;
    }

    public enum Street{A, B, C, D, F, G, H, I, J, K, L, First, Second, Third, Fourth, Fifth, Sixth, Seventh};
}
