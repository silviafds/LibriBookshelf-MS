package com.bookshelf.adapters.in.web.controller;

import com.bookshelf.adapters.in.web.dto.request.ReviewRequest;
import com.bookshelf.adapters.in.web.dto.response.ReviewRegistrationResponse;
import com.bookshelf.adapters.in.web.dto.response.ReviewResponse;
import com.bookshelf.application.mapper.ReviewMapper;
import com.bookshelf.application.ports.in.service.ReviewService;
import com.bookshelf.domain.vo.ReviewBookVo;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/review")
public class ReviewController {

    private final ReviewService reviewService;

    private final ReviewMapper reviewMapper;

    public ReviewController(ReviewService reviewService, ReviewMapper reviewMapper) {
        this.reviewService = reviewService;
        this.reviewMapper = reviewMapper;
    }

    @PostMapping("/register-review")
    public ReviewRegistrationResponse createBook(
            @Valid @RequestBody ReviewRequest request) {

        ReviewBookVo reviewBookVo = reviewMapper.toReviewBookVo(request);

        return reviewService.registerReview(reviewBookVo);
    }

    @DeleteMapping("/delete-book/{id}")
    public ReviewRegistrationResponse deleteBook(
            @PathVariable Long id) {
        return reviewService.deleteReview(id);
    }

    @GetMapping("/list-reviews")
    public List<ReviewResponse> listAllBooks() {
        return reviewService.listAllReviews();
    }

    @GetMapping("/list-reviews/{id}")
    public ReviewResponse listReviewForId(@PathVariable Long id) {
        return reviewService.listReviewForId(id);
    }

    @PatchMapping("/edit-book")
    public ReviewResponse partialUpdateReview(
            @RequestBody ReviewRequest request) {

        ReviewBookVo bookVo = reviewMapper.toReviewBookVo(request);

        return reviewService.partialUpdate(bookVo);
    }

    @GetMapping("/search-review/{title}")
    public ReviewResponse searchReviewByTitle(
            @PathVariable String title) {

        return reviewService.searchReviewByTitle(title);
    }
}
