package com.example.RestaurantManager.database;

import com.example.RestaurantManager.model.Location;
import com.example.RestaurantManager.model.Menu;
import com.example.RestaurantManager.model.MenuItem;
import com.example.RestaurantManager.model.Restaurant;
import com.example.RestaurantManager.services.PropertiesService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuDBConnection {

    private Connection connect = null;
    private PreparedStatement preparedStatement = null;
    private final Logger log = LogManager.getLogger(getClass());
    private String url;
    private String username;
    private String password;

    public MenuDBConnection() {
        try {
            PropertiesService config = new PropertiesService();
            url = config.getPropValues("dbURL");
            username = config.getPropValues("username");
            password = config.getPropValues("password");
            // This will load the MySQL driver, each DB has its own driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
            log.fatal(e.getMessage());
        }
    }



    public int addMenuItem(String restaurantId, String name,
                             String description, double price) throws IllegalArgumentException
    {
        List<Integer> categoryIds = new ArrayList<>();
        try {
            System.out.println("Connecting to DB");
            // Setup the connection with the DB
            connect = DriverManager
                    .getConnection(url, username, password);


            preparedStatement = connect
                    .prepareStatement("insert into menuitem (restaurantId, name, description, price) values(?, ?, ?, ?)");
            preparedStatement.setString(1, restaurantId);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, description);
            preparedStatement.setDouble(4, price);
            System.out.println("Execute: " + preparedStatement.toString());
            int result = preparedStatement.executeUpdate();

            if (result!=1)
            {
                log.debug("failed insert");
            }

            PreparedStatement check = connect.prepareCall("SELECT LAST_INSERT_ID();");

            ResultSet idResSet = check.executeQuery();

            if(idResSet.next())
            {
                int nextId = idResSet.getInt(1);
                return nextId;
            }
            else
            {
                connect.close();
                return -1;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            log.fatal(e.getMessage());
            return -1;
        }
    }

    public boolean deleteMenuItem(String restaurantId, String itemId) throws IllegalArgumentException
    {
        int rId = Integer.valueOf(restaurantId);
        int iId = Integer.valueOf(itemId);

        try {
            System.out.println("Connecting to DB");
            // Setup the connection with the DB
            connect = DriverManager
                    .getConnection(url, username, password);
            preparedStatement = connect
                    .prepareStatement("delete from menuitem where id = ? and restaurantId = ?");
            System.out.println("c");
            preparedStatement.setInt(1, iId);
            preparedStatement.setInt(2, rId);
            int result = preparedStatement.executeUpdate();

            preparedStatement = connect
                    .prepareStatement("select name from menuitem where id = ?");
            preparedStatement.setInt(1,rId);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next())
            {
                return false;
            }
            return true;

        }
        catch (SQLException e) {
            e.printStackTrace();
            log.fatal(e.getMessage());
            return false;
        }
    }


    public MenuItem getMenuItem(String id, String restaurantId) throws IllegalArgumentException, SQLException
    {
        // Setup the connection with the DB
        connect = DriverManager
                .getConnection(url, username, password);

        int idInt = Integer.valueOf(id);
        int rIdInt = Integer.valueOf(restaurantId);
        String name = "";
        String description = "";
        double price = -1.0;

        try {
            preparedStatement = connect
                    .prepareStatement("select * from menuitem where id = ? and restaurantId = ?");
            preparedStatement.setInt(1, idInt);;
            preparedStatement.setInt(2, rIdInt);

            ResultSet rs = preparedStatement.executeQuery();

            if(rs.next())
            {
                String restaurantIdString = String.valueOf(restaurantId);
                name = rs.getString(3);
                description = rs.getString(4);
                price = rs.getDouble(5);

                MenuItem menuItem = new MenuItem(name, description, price);
                return menuItem;
            }
            else
            {
                return null;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            log.fatal(e.getMessage());
            throw e;
        }
        finally
        {
            connect.close();
        }
    }


    public Menu getMenu(String restaurantId) throws IllegalArgumentException, SQLException
    {
        Menu menu = new Menu();
        // Setup the connection with the DB
        connect = DriverManager
                .getConnection(url, username, password);

        int rIdInt = Integer.valueOf(restaurantId);
        String name = "";
        String description = "";
        double price = -1.0;

        try {

            preparedStatement = connect
                    .prepareStatement("select * from menuitem where restaurantId = ?");
            preparedStatement.setInt(1, rIdInt);;

            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next())
            {
                int id = rs.getInt(1);
                String idString = String.valueOf(id);
                name = rs.getString(3);
                description = rs.getString(4);
                price = rs.getDouble(5);

                MenuItem menuItem = new MenuItem(name, description, price);
                menu.addItem(idString, menuItem);
            }
            return menu;
        }
        catch (SQLException e) {
            e.printStackTrace();
            log.fatal(e.getMessage());
            throw e;
        }
        finally
        {
            connect.close();
        }
    }

}
