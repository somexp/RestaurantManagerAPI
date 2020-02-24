package com.example.RestaurantManager.controller;

import com.example.RestaurantManager.model.RestaurantContainer;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value="/menu")
public class MenuController {

    String SUCCESS_RESPONSE = "success";



    @RequestMapping(value = "/{restaurantId}", method = RequestMethod.GET)
    public ResponseEntity<String> getMenu(@PathVariable("restaurantId") String restaurantId) {

        String menu = "";

        try
        {
            JSONObject menuJSON = RestaurantContainer.getMenuJSON(restaurantId);
            menu = menuJSON.toString();
        }
        catch(Exception e)
        {
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity(menu, HttpStatus.OK);
    }


    @RequestMapping(value = "/{restaurantId}/menuItemId", method = RequestMethod.GET)
    public ResponseEntity<String> getMenuItem(@PathVariable("menuId") String menuId,
                                              @PathVariable("menuId") String menuItemId) {

        String menuItem = "";

        try
        {
            JSONObject menuItemJSON = RestaurantContainer.getMenuItemJSON(menuId, menuItemId);
            menuItem = menuItemJSON.toString();
        }
        catch(Exception e)
        {
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity(menuItem, HttpStatus.OK);
    }
}

