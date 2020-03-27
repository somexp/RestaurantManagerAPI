package com.example.RestaurantManager;

import com.example.RestaurantManager.database.UserDBConnection;
import com.example.RestaurantManager.model.Location;
import com.example.RestaurantManager.model.MenuItem;
import com.example.RestaurantManager.model.Restaurant;
import com.example.RestaurantManager.model.RestaurantContainer;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import java.util.ArrayList;
import java.util.List;

public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {

		UserDBConnection userDBConnection = new UserDBConnection();
		//public Restaurant(String name, Location location, Restaurant.Category category, Menu menu)
		Location location = new Location("999999 Park Ave", "New York", "New York", "10001");
		List<Restaurant.Category> categories1 = new ArrayList<>();
		categories1.add(Restaurant.Category.Fine_Dinning);
		String restaurantId1 = RestaurantContainer.addRestaurant("Top Table", location, categories1);

		location = new Location("666 Dead Dog Avenue", "Sleazy", "Alabama", "36787");
		List<Restaurant.Category> categories2 = new ArrayList<>();
		categories2.add(Restaurant.Category.Fastfood);
		categories2.add(Restaurant.Category.Seafood);
		String restaurantId2 = RestaurantContainer.addRestaurant("Trash Land", location, categories2);


		MenuItem item11 = new MenuItem("Truffel Steak", "Just as expensive as possible.", 250.00);
		MenuItem item12 = new MenuItem("Gold Plated Lobster", "Just as expensive as possible-Seafood Variety.", 350.00);
		String item11Id = RestaurantContainer.addMenuItem(restaurantId1, item11);
		String item12Id = RestaurantContainer.addMenuItem(restaurantId1, item12);

		MenuItem item21 = new MenuItem("Mud Burger", "Cheap ground beef, dilluted with tree bark, in a bun made of flour and sawdust.", 0.78);
		MenuItem item22 = new MenuItem("Slime Nuggets", "Pulverized chicken, mostly bone, sinew and intestine, mixed with plaster, breaded and fried in petroleum distillate.", 0.65);
		String item21Id = RestaurantContainer.addMenuItem(restaurantId2, item21);
		String item22Id = RestaurantContainer.addMenuItem(restaurantId2, item22);


		return application.sources(RestaurantManagerApplication.class);
	}

}
