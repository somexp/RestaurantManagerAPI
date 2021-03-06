package com.example.RestaurantManager;

import com.example.RestaurantManager.database.UserDBConnection;
import com.example.RestaurantManager.model.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class RestaurantManagerApplicationTests {

	@Test
	void contextLoads() {
	}


	@Test
	void testDBConnection()
	{
		try {
			UserDBConnection userDBConnection = new UserDBConnection();
			assert(userDBConnection.testConnect());
			System.out.println("NO ERRORS IN CONNECTING!!!!");
		}
		catch (Throwable t)
		{
			t.printStackTrace();
			assert(false);
		}
	}

	@Test
	void testRestaurantContainer()
	{

    //public Restaurant(String name, Location location, String category, Menu menu)
		Location location = new Location("999999 Park Ave", "New York", "New York", "10001");
		List<String> categories1 = new ArrayList<>();
		categories1.add("Fine_Dinning");
		String restaurantId1 = RestaurantContainer.addRestaurant("Top Table", location, categories1);

		System.out.println("After Add restaurantID1: " + restaurantId1);

		location = new Location("666 Dead Dog Avenue", "Sleazy", "Alabama", "36787");
		List<String> categories2 = new ArrayList<>();
		categories2.add("Fastfood");
		categories2.add("Seafood");
		String restaurantId2 = RestaurantContainer.addRestaurant("Trash Land", location, categories2);

		System.out.println("After Add restaurantID2: " + restaurantId2);

		for (int i = 0; i < 5; i++) { System.out.println("-----------------------------------------"); }

		System.out.println(RestaurantContainer.getRestaurantsJSON());

		for (int i = 0; i < 5; i++) { System.out.println("-----------------------------------------"); }

		MenuItem item11 = new MenuItem("Truffel Steak", "Just as expensive as possible.", 250.00);
		MenuItem item12 = new MenuItem("Gold Plated Lobster", "Just as expensive as possible-Seafood Variety.", 350.00);
		String item11Id = RestaurantContainer.addMenuItem(restaurantId1, item11);
		String item12Id = RestaurantContainer.addMenuItem(restaurantId1, item12);

		MenuItem item21 = new MenuItem("Mud Burger", "Cheap ground beef, dilluted with tree bark, in a bun made of flour and sawdust.", 0.78);
		MenuItem item22 = new MenuItem("Slime Nuggets", "Pulverized chicken, mostly bone, sinew and intestine, mixed with plaster, breaded and fried in petroleum distillate.", 0.65);
		String item21Id = RestaurantContainer.addMenuItem(restaurantId2, item21);
		String item22Id = RestaurantContainer.addMenuItem(restaurantId2, item22);

		System.out.println(RestaurantContainer.getMenuJSON(restaurantId1));
		for (int i = 0; i < 5; i++) { System.out.println("-----------------------------------------"); }
		System.out.println(RestaurantContainer.getMenuJSON(restaurantId2));
		for (int i = 0; i < 5; i++) { System.out.println("-restaurantId1, item11Id)----------------------------------------"); }
		System.out.println(RestaurantContainer.getMenuItemJSON(restaurantId1, item11Id));
		for (int i = 0; i < 5; i++) { System.out.println("-restaurantId1, item12Id----------------------------------------"); }
		System.out.println(RestaurantContainer.getMenuItemJSON(restaurantId1, item12Id));
		for (int i = 0; i < 5; i++) { System.out.println("-restaurantId2, item21Id----------------------------------------"); }
		System.out.println(RestaurantContainer.getMenuItemJSON(restaurantId2, item21Id));
		for (int i = 0; i < 5; i++) { System.out.println("-restaurantId2, item22Id----------------------------------------"); }
		System.out.println(RestaurantContainer.getMenuItemJSON(restaurantId2, item22Id));
		for (int i = 0; i < 5; i++) { System.out.println("-----------------------------------------"); }

		System.out.println("restaurantID: " + restaurantId1);
		System.out.println("restaurantID: " + restaurantId2);
		assert(RestaurantContainer.removeRestaurant(restaurantId1));
		assert(RestaurantContainer.removeRestaurant(restaurantId2));
	}

}
