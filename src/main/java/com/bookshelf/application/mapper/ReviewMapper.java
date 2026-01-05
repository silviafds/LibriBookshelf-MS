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
                .bookName(request.bookName())
                .review(request.review())
                .bookNote(request.bookNote())
                .build();
    }

    public Review toReview(ReviewBookVo vo) {
        return Review.builder()
                .reviewTitle(vo.getReviewTitle())
                .bookName(vo.getBookName())
                .review(vo.getReview())
                .bookNote(vo.getBookNote())
                .build();
    }

    public List<ReviewResponse> reviewListToReviewResponseList(List<Review> reviews) {
        if (reviews == null) {
            return Collections.emptyList();
        }

        List<ReviewResponse> responses = new ArrayList<>();

        for (Review review : reviews) {
            ReviewResponse response = ReviewResponse.builder()
                    .id(review.getId())
                    .reviewTitle(review.getReviewTitle())
                    .bookName(review.getBookName())
                    .bookNote(review.getBookNote())
                    .review(review.getReview())
                    .build();
            responses.add(response);
        }

        return responses;
    }

    public ReviewResponse reviewToReviewResponse(Review review) {
        ReviewResponse response = ReviewResponse.builder()
                .id(review.getId())
                .reviewTitle(review.getReviewTitle())
                .bookName(review.getBookName())
                .bookNote(review.getBookNote())
                .review(review.getReview())
                .build();

        return response;
    }
}
