package com.bookshelf.application.mapper;

import com.bookshelf.adapters.in.web.dto.request.ReviewRequest;
import com.bookshelf.adapters.in.web.dto.response.ReviewResponse;
import com.bookshelf.domain.model.Review;
import com.bookshelf.domain.vo.ReviewBookVo;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class ReviewMapper {

    public ReviewBookVo toReviewBookVo(ReviewRequest request) {
        return ReviewBookVo.builder()
                .reviewTitle(request.reviewTitle())
                .idBookReviewed(request.idBookReviewed())
                .review(request.review())
                .bookNote(Math.toIntExact(request.bookNote()))
                .idUserReviewed(request.idUserReviewed())
                .build();
    }

    public Review toReview(ReviewBookVo vo) {
        return Review.builder()
                .reviewTitle(vo.getReviewTitle())
                .idBookReviewed(vo.getIdBookReviewed())
                .review(vo.getReview())
                .bookNote(Long.valueOf(vo.getBookNote()))
                .idUserReviewed(vo.getIdUserReviewed())
                .build();
    }

    public List<ReviewResponse> reviewListToReviewResponseList(List<Review> reviews, String name) {
        if (reviews == null) {
            return Collections.emptyList();
        }

        List<ReviewResponse> responses = new ArrayList<>();

        for (Review review : reviews) {
            ReviewResponse response = ReviewResponse.builder()
                    .id(review.getId())
                    .reviewTitle(review.getReviewTitle())
                    .nameBookReviewed(name)
                    .bookNote(review.getBookNote())
                    .review(review.getReview())
                    .build();
            responses.add(response);
        }

        return responses;
    }


    public ReviewResponse reviewToReviewResponse(Review review, String name, String nameBook) {
        ReviewResponse response = ReviewResponse.builder()
                .id(review.getId())
                .reviewTitle(review.getReviewTitle())
                .nameBookReviewed(nameBook)
                .bookNote(review.getBookNote())
                .review(review.getReview())
                .nameUserReviewed(name)
                .build();

        return response;
    }

    public ReviewResponse reviewToReviewResponse(Review review) {
        ReviewResponse response = ReviewResponse.builder()
                .id(review.getId())
                .reviewTitle(review.getReviewTitle())
                .bookNote(review.getBookNote())
                .review(review.getReview())
                .build();

        return response;
    }
}
