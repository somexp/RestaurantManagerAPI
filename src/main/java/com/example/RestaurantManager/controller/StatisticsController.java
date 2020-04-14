package com.example.RestaurantManager.controller;

import com.example.RestaurantManager.connections.SearchAppConnector;
import com.example.RestaurantManager.model.MenuItem;
import com.example.RestaurantManager.model.RestaurantContainer;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/statistics")
public class StatisticsController {

    String SUCCESS_RESPONSE = "success";

    @CrossOrigin(origins = "http://localhost:8080")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<String> getStatistics() {

        String stats = "";

        try
        {
            JSONArray statsArray = SearchAppConnector.getSearchResults();
            stats = statsArray.toString();
        }
        catch(Exception e)
        {
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity(stats, HttpStatus.OK);
    }
}

