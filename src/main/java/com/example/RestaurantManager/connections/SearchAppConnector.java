package com.example.RestaurantManager.connections;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class SearchAppConnector {

    public static JSONArray getSearchResults()
    {
        JSONArray newArray = new JSONArray();
        JSONObject newObject = new JSONObject();

        try {

            URL url = new URL("http://ec2-54-198-54-151.compute-1.amazonaws.com:8080/search_statistics");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");

            if (connection.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + connection.getResponseCode());
            }
            String inline = "";
            Scanner sc = new Scanner(url.openStream());


            while(sc.hasNext())

            {

                inline+=sc.nextLine();

            }

            sc.close();

            connection.disconnect();

            JSONTokener tokener = new JSONTokener(inline);

            newArray = new JSONArray(tokener);

            System.out.println("JSON Array!!!!!!");
            System.out.println(newArray.getJSONObject(0));
            System.out.println("Next ONE---------");
            System.out.println(newArray.getJSONObject(1));

            for (int index = 0; index < newArray.length(); index++)
            {
                JSONObject obj = newArray.getJSONObject(index);
                newObject.put(obj.get("id").toString(), obj.toString());
            }
            JSONObject obj1 = newArray.getJSONObject(0);

            JSONObject obj2 = newArray.getJSONObject(1);

            System.out.println("FirstSetOfKeys: " + obj1.keySet());
            System.out.println("SecondSetOfKeys: " + obj2.keySet());

        } catch (MalformedURLException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();
        }
        return newArray;
    }
}
