package com.example.RestaurantManager;

import com.example.RestaurantManager.model.Location;
import com.example.RestaurantManager.model.Menu;
import com.example.RestaurantManager.model.Restaurant;
import com.example.RestaurantManager.model.RestaurantContainer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RestaurantManagerApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void testRestaurantContainer()
	{

    //public Restaurant(String name, Location location, Restaurant.Category category, Menu menu)
		Location location = new Location(123, Location.Street.A);
		Restaurant.Category category = Restaurant.Category.Fine_Dinning;
		RestaurantContainer.addRestaurant("Top Table", location, category);

		location = new Location(456, Location.Street.D);
		category = Restaurant.Category.Fastfood;
		RestaurantContainer.addRestaurant("Trash Land", location, category);

		System.out.println(RestaurantContainer.getRestaurantsJSON());
	}

}
