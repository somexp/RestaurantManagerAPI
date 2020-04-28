package com.example.RestaurantManager.database;

import com.example.RestaurantManager.model.Location;
import com.example.RestaurantManager.model.Restaurant;
import com.example.RestaurantManager.services.PropertiesService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RestaurantDBConnection {

    private Connection connect = null;
    private PreparedStatement preparedStatement = null;
    private final Logger log = LogManager.getLogger(getClass());
    private String url;
    private String username;
    private String password;

    public RestaurantDBConnection() {
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


    public List<String> getCategories()
    {
        List<String> categories =  new ArrayList<String>();
        try {
            // Setup the connection with the DB
            connect = DriverManager
                    .getConnection(url, username, password);

            preparedStatement = connect
                    .prepareStatement("select * from restaurant_category");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
            {
                categories.add(resultSet.getString("name"));
            }
            connect.close();
        }
        catch (SQLException e) {
            log.fatal(e.getMessage());
        }
        return categories;
    }


    public int getCategoryId(String name)
    {
        List<String> categories =  new ArrayList<String>();
        try {
            // Setup the connection with the DB
            connect = DriverManager
                    .getConnection(url, username, password);

            preparedStatement = connect
                    .prepareStatement("select id from restaurant_category where name = ?");
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
            {
                int id = resultSet.getInt(1);
                connect.close();
                return id;
            }
            else
            {
                connect.close();
                return -1;
            }
        }
        catch (SQLException e) {
            log.fatal(e.getMessage());
            return -1;
        }
    }



    public int addRestaurant(String name, String street, String city,
                             String state, String zipcode, int owner,
                             List<String> categories) throws IllegalArgumentException
    {
        List<Integer> categoryIds = new ArrayList<>();
        try {
            for (String categoryName: categories)
            {
                int categoryId = getCategoryId(categoryName);

                if (categoryId>=0) {
                    categoryIds.add(categoryId);
                }
            }


            // Setup the connection with the DB
            connect = DriverManager
                    .getConnection(url, username, password);


            preparedStatement = connect
                    .prepareStatement("insert into restaurant (name, street, city, state, zipcode, owner) values(?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, street);
            preparedStatement.setString(3, city);
            preparedStatement.setString(4, state);
            preparedStatement.setString(5, zipcode);
            preparedStatement.setInt(6, owner);
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
                for (int categoryId: categoryIds) {
                    preparedStatement = connect
                            .prepareStatement("insert into category_lookup (restaurantId, categoryId) values(?, ?)");
                    preparedStatement.setInt(1, nextId);
                    preparedStatement.setInt(2, categoryId);
                    preparedStatement.executeUpdate();
                }
                preparedStatement = connect
                        .prepareStatement("select categoryId from category_lookup where restaurantId = ?");
                preparedStatement.setInt(1, nextId);
                ResultSet catRestSet = preparedStatement.executeQuery();
                while (catRestSet.next())
                {
                    System.out.println("Added category id: " + catRestSet.getInt(1));
                }
                connect.close();
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
        }
        return -1;
    }

    public boolean deleteRestaurant(int restaurantId) throws IllegalArgumentException
    {
        try {
            // Setup the connection with the DB
            connect = DriverManager
                    .getConnection(url, username, password);
            preparedStatement = connect
                    .prepareStatement("delete from category_lookup where restaurantId = ?");

            preparedStatement.setInt(1,restaurantId);
            int result = preparedStatement.executeUpdate();


            preparedStatement = connect
                    .prepareStatement("delete from restaurant where id = ?");
            preparedStatement.setInt(1,restaurantId);
            result = preparedStatement.executeUpdate();

            preparedStatement = connect
                    .prepareStatement("select categoryId from category_lookup where restaurantId = ?");
            preparedStatement.setInt(1,restaurantId);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next())
            {
                return false;
            }


            preparedStatement = connect
                    .prepareStatement("select name from restaurant where id = ?");
            preparedStatement.setInt(1,restaurantId);
            rs = preparedStatement.executeQuery();
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


    public Restaurant getRestaurant(String id) throws IllegalArgumentException, SQLException
    {
        // Setup the connection with the DB
        connect = DriverManager
                .getConnection(url, username, password);

        int idInt = Integer.valueOf(id);
        String name = "";
        String street = "";
        String city = "";
        String state = "";
        String zipcode = "";
        List<String> categories =  new ArrayList<>();

        try {

            preparedStatement = connect
                    .prepareStatement("select * from restaurant where id = ?");
            preparedStatement.setInt(1, idInt);;

            ResultSet rs = preparedStatement.executeQuery();

            if(rs.next())
            {
                String idString = String.valueOf(id);
                name = rs.getString(2);
                street = rs.getString(3);
                city = rs.getString(4);
                state = rs.getString(5);
                zipcode = rs.getString(6);

                PreparedStatement preparedStatement2 = connect
                        .prepareStatement("select rc.name from restaurant_category rc and category_lookup cl where cl.restaurantId = ? and cl.categoryId = rc.id");
                preparedStatement2.setInt(1, idInt);
                ResultSet rs2 = preparedStatement2.executeQuery();
                while(rs2.next())
                {
                    categories.add(rs2.getString(1));
                }

                Location location = new Location(street, city, state, zipcode);
                Restaurant restaurant = new Restaurant(name, location, categories);
                connect.close();
                return restaurant;
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


    public Map<String, Restaurant> getRestaurants() throws SQLException
    {
        HashMap<String, Restaurant> restaurantsMap = new HashMap<String, Restaurant>();

        // Setup the connection with the DB
        connect = DriverManager
                .getConnection(url, username, password);

        String name = "";
        String street = "";
        String city = "";
        String state = "";
        String zipcode = "";
        List<String> categories =  new ArrayList<>();

        try {

            preparedStatement = connect
                    .prepareStatement("select * from restaurant");

            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next())
            {
                int id = rs.getInt(1);
                String idString = String.valueOf(id);
                name = rs.getString(2);
                street = rs.getString(3);
                city = rs.getString(4);
                state = rs.getString(5);
                zipcode = rs.getString(6);

                PreparedStatement preparedStatement2 = connect
                        .prepareStatement("select rc.name from restaurant_category rc, category_lookup cl where cl.restaurantId = ? and cl.categoryId = rc.id");
                preparedStatement2.setInt(1, id);
                ResultSet rs2 = preparedStatement2.executeQuery();
                while(rs2.next())
                {
                    categories.add(rs2.getString(1));
                }

                Location location = new Location(street, city, state, zipcode);
                Restaurant restaurant = new Restaurant(name, location, categories);
                restaurantsMap.put(idString, restaurant);
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
        return restaurantsMap;
    }


    public boolean testConnect() throws Exception
    {
        try {
            connect = DriverManager
                    .getConnection(url, username, password);
        }
        catch (Throwable t)
        {
            t.printStackTrace();
            return false;
        }
        return true;
    }


    public int addUser(String user, String pass)
    {

        try {
            // Setup the connection with the DB
            connect = DriverManager
                    .getConnection(url, username, password);
            preparedStatement = connect
                    .prepareStatement("insert into stratego.users (username, password, isActive, dateAdded) values(?, ?, TRUE, SYSDATE())");
            preparedStatement.setString(1, user);
            preparedStatement.setString(2, pass);
            int result = preparedStatement.executeUpdate();

            if (result!=1)
            {
                log.debug("failed insert");
            }

            PreparedStatement lastIdStat = connect.prepareCall("SELECT LAST_INSERT_ID()");

            ResultSet idResSet = lastIdStat.executeQuery();

            if(idResSet.next())
            {
                return idResSet.getInt(1);
            }

            connect.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
            log.fatal(e.getMessage());
        }
        return -1;
    }



    public List<String> getMoves(String username)
    {
        List<String> moves =  new ArrayList<String>();
        try {
            // Setup the connection with the DB
            connect = DriverManager
                    .getConnection(url, username, password);

            preparedStatement = connect
                    .prepareStatement("Select m.move from stratego.moveshistory m join (select g.id, u.username, g.winner from stratego.game g join stratego.users u on g.id = u.id) " +
                            "gu where gu.username =? and gu.winner IS NOT NULL order by m.dateAdded desc");
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
            {
                    moves.add(resultSet.getString("username"));
            }
            connect.close();
        }
        catch (SQLException e) {
            log.fatal(e.getMessage());
        }
        return moves;
    }


    public List<String> getOpponent(String username)
    {
        List<String> users =  new ArrayList<String>();
        String user;
        try {
            // Setup the connection with the DB
            connect = DriverManager
                    .getConnection(url, username, password);

            preparedStatement = connect
                    .prepareStatement("select username from stratego.users");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
            {
                user = resultSet.getString("username");
                if(!user.equals(username))
                users.add(username);
            }
            connect.close();
        }
        catch (SQLException e) {
            log.fatal(e.getMessage());
        }
        return users;
    }


    public String validateUserName(String usname)
    {
        String user = "";
        try {
            // Setup the connection with the DB
            connect = DriverManager
                    .getConnection(url, username, password);
            preparedStatement = connect
                    .prepareStatement("select username from stratego.users WHERE username = ?");
            preparedStatement.setString(1, usname);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
            {
                user = resultSet.getString("username");
            }
            connect.close();
        }
        catch (SQLException e) {
            log.fatal(e.getMessage());

        }

        return user;
    }

    public String getUserAttributes(int userId)
    {
        try {
            // Setup the connection with the DB
            connect = DriverManager
                    .getConnection(url, username, password);
            preparedStatement = connect
                    .prepareStatement("select username from stratego.users WHERE id = ?");
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
            {
                String name = resultSet.getString("username");
                int id = resultSet.getInt("id");

                return id + ":" + name;
            }
            connect.close();
        }
        catch (SQLException e) {
            log.fatal(e.getMessage());

        }
        throw new RuntimeException("Failed to get user: " + userId);
    }

    public String getUserAttributes(String userName)
    {
        try {
            // Setup the connection with the DB
            connect = DriverManager
                    .getConnection(url, username, password);
            preparedStatement = connect
                    .prepareStatement("select id, username from stratego.users WHERE username = ?");
            preparedStatement.setString(1, userName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
            {
                String name = resultSet.getString("username");

                int id = resultSet.getInt("id");

                connect.close();

                return id + ":" + name;
            }
            connect.close();
        }
        catch (SQLException e) {
            log.fatal(e.getMessage());

        }
        return null;
    }

    public String getUserID(String userName)
    {
        try {
            // Setup the connection with the DB
            connect = DriverManager
                    .getConnection(url, username, password);
            preparedStatement = connect
                    .prepareStatement("select id from stratego.users WHERE username = ?");
            preparedStatement.setString(1, userName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
            {
                String id = resultSet.getString("id");

                connect.close();

                return id;
            }
            connect.close();
        }
        catch (SQLException e) {
            log.fatal(e.getMessage());

        }
        return null;
    }

    public boolean checkPassword(String user, String pass)
    {
        try {
            // Setup the connection with the DB
            connect = DriverManager
                    .getConnection(url, username, password);
            preparedStatement = connect
                    .prepareStatement("select password from stratego.users WHERE username = ?");
            preparedStatement.setString(1, user);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next())
            {
                String stored = resultSet.getString(1);
                connect.close();

                //return SecureHash.validatePassword(pass, stored);
                return true;
            }
            connect.close();
            return false;
        }
        catch (SQLException e) {
            log.fatal(e.getMessage());
            return false;
        }
        /**catch (NoSuchAlgorithmException nsae)
        {
            log.fatal(nsae.getMessage());
            return false;
        }
        catch (InvalidKeySpecException ikse)
        {
            log.fatal(ikse.getMessage());
            return false;
        }**/
    }

    public void logMove(String user, String gameId, String move) {

        try {
            // Setup the connection with the DB
            connect = DriverManager
                    .getConnection(url, username, password);
            preparedStatement = connect
                    .prepareStatement("insert into stratego.moveshistory (username, game_id, move, dateAdded) values(?, ?, ?, SYSDATE())");
            preparedStatement.setString(1, user);
            preparedStatement.setString(2, gameId);
            preparedStatement.setString(3, move);
            int result = preparedStatement.executeUpdate();

            if (result!=1)
            {
                log.fatal("failed insert");
            }

            connect.close();
        }
        catch (Exception e) {
            log.fatal(e.getMessage());
        }
    }

    public List<String> getGameMoves(String gameId)
    {

        List<String>  moves = new ArrayList<String>();
        try {
            // Setup the connection with the DB
            connect = DriverManager
                    .getConnection(url, username, password);
            preparedStatement = connect
                    .prepareStatement("select username, move from stratego.moveshistory where game_id=?");

            preparedStatement.setString(1, gameId);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next())
            {
                String username = resultSet.getString("username");
                String move = resultSet.getString("move");
                moves.add(username + ": " + move);
            }

            connect.close();
        }
        catch (Exception e) {
            log.fatal(e.getMessage());
        }

        return moves;

    }
}
