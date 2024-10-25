package com.example.springsecurity.service.Impl;

import com.example.springsecurity.model.entity.Order;
import com.example.springsecurity.model.entity.Rating;
import com.example.springsecurity.model.entity.User;
import com.example.springsecurity.model.payload.request.RatingForm;
import com.example.springsecurity.model.dto.RatingResponseModel;
import com.example.springsecurity.model.payload.response.ResponseData;
import com.example.springsecurity.model.payload.response.ResponseError;
import com.example.springsecurity.repository.OrderRepository;
import com.example.springsecurity.repository.RatingRepository;
import com.example.springsecurity.repository.UserRepository;
import com.example.springsecurity.service.RatingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RatingServiceImpl implements RatingService {

    @Autowired
    private RatingRepository ratingRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public ResponseData<List<RatingResponseModel>> getAll() {
        log.info("Retrieving the review list ...");
        List<RatingResponseModel> listRating = ratingRepository.findAll().stream()
                .map(RatingResponseModel::toDto)
                .collect(Collectors.toList());

        return new ResponseData<>(200, "Successfully retrieved ratings", listRating);
    }

    @Override
    public ResponseData<RatingResponseModel> newRating(Long orderId, RatingForm form) {
        log.info("Creating a New Rate with orderId: "+ orderId);
        // Lấy thông tin người dùng hiện tại từ SecurityContextHolder
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User currentUser = userRepository.findByEmail(email).orElse(null);
        if (currentUser == null) {
            return new ResponseError<>(404, "User not found");
        }
        // Tìm đơn hàng theo orderId và userId
        Order existingOrder = orderRepository.findByOrderIdAndUserId(orderId, currentUser.getUserId()).orElse(null);
        if (existingOrder == null) {
            return new ResponseError<>(404,"Order not found for this user");
        }
        // Kiểm tra xem người dùng đã đánh giá cho đơn hàng này chưa
        Optional<Rating> existingRating = ratingRepository.findByOrderAndUser(existingOrder, currentUser);
        if (existingRating.isPresent()) {
            return new ResponseError<>(409, "You have already rated this order"); // 409 Conflict
        }
        // Validation cho các trường của form RatingForm
        if (form.getRating() < 1 || form.getRating() > 5) {
            return new ResponseData<>(400, "Rating must be between 1 and 5", null); // 400 Bad Request
        }
        if (form.getFeedback() == null || form.getFeedback().trim().isEmpty()) {
            return new ResponseData<>(400, "Feedback cannot be null or empty", null); // 400 Bad Request
        }
        // Tạo một rating mới nếu chưa tồn tại
        Rating newRating = new Rating();
        newRating.setUser(currentUser);
        newRating.setOrder(existingOrder);
        newRating.setRating(form.getRating());
        newRating.setFeedback(form.getFeedback());

        Rating savedRating = ratingRepository.save(newRating);
        return new ResponseData<>(200, "Rating retrieved successfully", RatingResponseModel.toDto(savedRating));
    }

    @Override
    public ResponseData<RatingResponseModel> change(Long orderId, RatingForm form){
        log.info("Changing Rating with orderId: "+orderId);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User currentUser = userRepository.findByEmail(email).orElse(null);
        if (currentUser == null) {
            return new ResponseError<>(404, "User not found");
        }

        // Tìm đơn hàng theo orderId và userId
        Order existingOrder = orderRepository.findByOrderIdAndUserId(orderId, currentUser.getUserId()).orElse(null);
        if (existingOrder == null) {
            return new ResponseError<>(404, "Order not found for this user");
        }
        // Validation cho các trường của form RatingForm
        if (form.getRating() < 1 || form.getRating() > 5) {
            return new ResponseData<>(400, "Rating must be between 1 and 5", null);
        }
        if (form.getFeedback() == null || form.getFeedback().trim().isEmpty()) {
            return new ResponseData<>(400, "Feedback cannot be null or empty", null);
        }

        Rating rating = new Rating();
        rating.setRating(form.getRating());
        rating.setFeedback(form.getFeedback());

        Rating saveRating = ratingRepository.save(rating);
        return new ResponseData<>(200,"Change feedback successfully");
    }

    @Override
    public ResponseData<RatingResponseModel> deleteRatingByAdmin(Long ratingId){
        log.info("Deleting rating with id: "+ ratingId);
        Rating rating = ratingRepository.findRatingByRatingId(ratingId).orElse(null);
        if( rating == null )    { return new ResponseError<>(400,"Rating not found with id: "+ratingId);}

        ratingRepository.delete(rating);
        return new ResponseData<>(200,"Delete feedback successfully", null);
    }



}