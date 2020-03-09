package com.example.RestaurantManager;

import com.example.RestaurantManager.model.Location;
import com.example.RestaurantManager.model.MenuItem;
import com.example.RestaurantManager.model.Restaurant;
import com.example.RestaurantManager.model.RestaurantContainer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class RestaurantManagerApplication extends SpringBootServletInitializer {

	public static void main(String[] args)
	{
		SpringApplication.run(RestaurantManagerApplication.class, args);


	}


	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(RestaurantManagerApplication.class);
	}

}
