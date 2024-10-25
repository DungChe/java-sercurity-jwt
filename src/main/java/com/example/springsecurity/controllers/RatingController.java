package com.example.springsecurity.controllers;

import com.example.springsecurity.model.dto.RatingResponseModel;
import com.example.springsecurity.model.payload.request.RatingForm;
import com.example.springsecurity.model.payload.response.ResponseData;
import com.example.springsecurity.repository.UserRepository;
import com.example.springsecurity.service.RatingService;
import com.example.springsecurity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class RatingController {

    @Autowired
    private RatingService ratingService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @PostMapping("/orders/{orderId}/new-rate")
    public ResponseEntity<ResponseData<RatingResponseModel>> newRating(@PathVariable Long orderId, @RequestBody RatingForm form) {return ResponseEntity.ok(ratingService.newRating(orderId, form));}

    @PutMapping("/orders/{ordersId}/change-feedback")
    public ResponseEntity<ResponseData<RatingResponseModel>> changeRating(@PathVariable Long ordersId, @RequestBody RatingForm form){
        return ResponseEntity.ok(ratingService.change(ordersId, form)) ;
    }


}
