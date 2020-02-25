package com.example.RestaurantManager;

import com.example.RestaurantManager.model.Location;
import com.example.RestaurantManager.model.MenuItem;
import com.example.RestaurantManager.model.Restaurant;
import com.example.RestaurantManager.model.RestaurantContainer;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {

		//public Restaurant(String name, Location location, Restaurant.Category category, Menu menu)
		Location location = new Location(123, Location.Street.A);
		Restaurant.Category category = Restaurant.Category.Fine_Dinning;
		String restaurantId1 = RestaurantContainer.addRestaurant("Top Table", location, category);

		location = new Location(456, Location.Street.D);
		category = Restaurant.Category.Fastfood;
		String restaurantId2 = RestaurantContainer.addRestaurant("Trash Land", location, category);

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
