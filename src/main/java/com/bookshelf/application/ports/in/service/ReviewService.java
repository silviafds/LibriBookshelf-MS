package com.bookshelf.application.ports.in.service;

import com.bookshelf.adapters.in.web.dto.response.ReviewRegistrationResponse;
import com.bookshelf.adapters.in.web.dto.response.ReviewResponse;
import com.bookshelf.domain.vo.ReviewBookVo;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.nio.file.AccessDeniedException;
import java.util.List;

@Component
public interface ReviewService {

    ResponseEntity<ReviewRegistrationResponse> registerReview(ReviewBookVo reviewBookVo);
    ResponseEntity<ReviewRegistrationResponse> deleteReview(Long id);
    List<ReviewResponse> listAllReviews(String tokenAuth);
    ResponseEntity<ReviewResponse> listReviewForId(Long id, String tokenAuth);
    ReviewResponse partialUpdate(Long id, ReviewBookVo vo, String tokenAuth);

}
