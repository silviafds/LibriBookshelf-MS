package com.bookshelf.application.ports.in.service;

import com.bookshelf.adapters.in.web.dto.response.ReviewRegistrationResponse;
import com.bookshelf.adapters.in.web.dto.response.ReviewResponse;
import com.bookshelf.domain.vo.ReviewBookVo;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ReviewService {

    ReviewRegistrationResponse registerReview(ReviewBookVo reviewBookVo);
    ReviewRegistrationResponse deleteReview(Long id);
    List<ReviewResponse> listAllReviews();
    ReviewResponse listReviewForId(Long id);
    ReviewResponse partialUpdate(ReviewBookVo vo);
    ReviewResponse searchReviewByTitle(String title);

}
