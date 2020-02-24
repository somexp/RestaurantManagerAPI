package com.example.RestaurantManager.controller;

import org.apache.coyote.Response;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value="/frontEnd")
public class FrontEndController {

    String SUCCESS_RESPONSE = "success";

    @PostMapping(value="addRestaurant", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addRestaurant(
            @RequestHeader HttpHeaders httpHeaders,
            @RequestParam(value="restaurantName", required=true) String restaurantName,
            HttpServletResponse resp, HttpServletRequest req) throws Exception
    {
        HttpHeaders responseHeader = new HttpHeaders();

        return new ResponseEntity<>(SUCCESS_RESPONSE, responseHeader, HttpStatus.OK);
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public ResponseEntity<List<String>> listAllUsers() {
        List<String> users = new ArrayList<>();

        for(int i = 0; i < 10; i++)
        {
            users.add("abcdefg");
        }

        if (users.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
            // You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<String>>(users, HttpStatus.OK);
    }
}
