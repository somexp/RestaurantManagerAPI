package com.example.RestaurantManager.model;

public class Location {

    private Street street;
    private int number;

    public Location(int number, Street street)
    {
        this.number = number;
        this.street = street;
    }

    public Location(int number, String street)
    {
        this.number = number;

        for (Street aStreet: Street.values())
        {
            if (street.equals(aStreet.toString()))
            {
                this.street = aStreet;
            }
        }
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

    public static boolean validStreet(String st)
    {
        for (Street aStreet: Street.values())
        {
            if (st.equals(aStreet.toString()))
            {
                return true;
            }
        }
        return false;
    }

    public enum Street{A, B, C, D, F, G, H, I, J, K, L, First, Second, Third, Fourth, Fifth, Sixth, Seventh};
}
