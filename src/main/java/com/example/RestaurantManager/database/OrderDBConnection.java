package com.example.RestaurantManager.database;

import com.example.RestaurantManager.model.Menu;
import com.example.RestaurantManager.model.MenuItem;
import com.example.RestaurantManager.model.Order;
import com.example.RestaurantManager.model.OrderItem;
import com.example.RestaurantManager.services.PropertiesService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderDBConnection {

    private Connection connect = null;
    private PreparedStatement preparedStatement = null;
    private final Logger log = LogManager.getLogger(getClass());
    private String url;
    private String username;
    private String password;

    public OrderDBConnection() {
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



    public boolean addOrder(int id, int restaurantId, double baseAmount,
                            double tax, double total, String status,
                            String deliveryAddress, String deliveryInstructions) throws IllegalArgumentException
    {
        List<Integer> categoryIds = new ArrayList<>();
        try {
            System.out.println("Connecting to DB");
            // Setup the connection with the DB
            connect = DriverManager
                    .getConnection(url, username, password);


            preparedStatement = connect
                    .prepareStatement("insert into orders (id, restaurantId, baseAmount, tax, total, " +
                            "status, deliveryAddress, deliveryInstructions) " +
                            "values(?, ?, ?, ?, ?, ?, ?, ?)");
            preparedStatement.setInt(1, id);
            preparedStatement.setInt(2, restaurantId);
            preparedStatement.setDouble(3, baseAmount);
            preparedStatement.setDouble(4, tax);
            preparedStatement.setDouble(5, total);
            preparedStatement.setString(6, status);
            preparedStatement.setString(7, deliveryAddress);
            preparedStatement.setString(8, deliveryInstructions);
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
                return true;
            }
            else
            {
                return false;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            log.fatal(e.getMessage());
            return false;
        }
    }

    public int addOrderItem(int orderId, int itemId,
                                double price, int quantity) throws IllegalArgumentException
    {
        List<Integer> categoryIds = new ArrayList<>();
        try {
            System.out.println("Connecting to DB");
            // Setup the connection with the DB
            connect = DriverManager
                    .getConnection(url, username, password);


            preparedStatement = connect
                    .prepareStatement("insert into orderitem (orderId, itemId, price, quantity) values(?, ?, ?, ?)");
            preparedStatement.setInt(1, orderId);
            preparedStatement.setInt(2, itemId);
            preparedStatement.setDouble(3, price);
            preparedStatement.setInt(4, quantity);
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

    public List<Order> getOrders() throws IllegalArgumentException, SQLException
    {
        List<Order> orders = new ArrayList<>();
        // Setup the connection with the DB
        connect = DriverManager
                .getConnection(url, username, password);

        try {
            preparedStatement = connect
                    .prepareStatement("select * from orders");
            System.out.println("Sending query: " + preparedStatement);

            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next())
            {
                System.out.println("Working with an order");
                int id = rs.getInt(1);
                int restaurantId = rs.getInt(2);
                double baseAmount = rs.getDouble(3);
                double tax = rs.getDouble(4);
                double total = rs.getDouble(5);
                String status = rs.getString(6);
                String deliveryAddress = rs.getString(7);
                String deliveryInstructions = rs.getString(8);

                Order order = new Order(String.valueOf(id), String.valueOf(restaurantId), baseAmount,
                        tax, total, status, deliveryAddress, deliveryInstructions);

                System.out.println("This order's data: " + order.getOrderJSON().toString());

                preparedStatement = connect
                        .prepareStatement("select * from orderitem where orderId = ?");
                preparedStatement.setInt(1, id);

                ResultSet rs2 = preparedStatement.executeQuery();

                while(rs2.next())
                {
                    System.out.println("Working with an order item.");
                    int orderItemId = rs.getInt(1);
                    int orderId = rs.getInt(2);
                    int itemId = rs.getInt(3);
                    double price = rs.getDouble(4);
                    int quantity = rs.getInt(5);

                    String oiiString = String.valueOf(orderItemId);
                    String oiString = String.valueOf(orderId);
                    String iIdString = String.valueOf(itemId);
                    OrderItem orderItem = new OrderItem(oiiString, oiString, iIdString, price, quantity);

                    System.out.println("This order item's data: " + orderItem.getItemJSON().toString());
                    order.addOrderItem(oiiString, orderItem);
                }
                orders.add(order);
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
        return orders;
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
