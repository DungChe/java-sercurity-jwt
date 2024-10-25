package com.example.springsecurity.service;

import com.example.springsecurity.model.payload.request.RatingForm;
import com.example.springsecurity.model.dto.RatingResponseModel;
import com.example.springsecurity.model.payload.response.ResponseData;

import java.util.List;

public interface RatingService {
    ResponseData<List<RatingResponseModel>> getAll();
    ResponseData<RatingResponseModel> newRating(Long oderID, RatingForm ratingForm);
    // change, delete
    ResponseData<RatingResponseModel> change(Long orderID, RatingForm ratingForm);
    ResponseData<RatingResponseModel> deleteRatingByAdmin(Long ratingId);
}
