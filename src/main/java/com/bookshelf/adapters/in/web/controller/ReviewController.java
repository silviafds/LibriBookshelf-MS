package com.bookshelf.adapters.in.web.controller;

import com.bookshelf.adapters.in.web.dto.request.ReviewRequest;
import com.bookshelf.adapters.in.web.dto.response.ReviewRegistrationResponse;
import com.bookshelf.adapters.in.web.dto.response.ReviewResponse;
import com.bookshelf.application.mapper.ReviewMapper;
import com.bookshelf.application.ports.in.service.ReviewService;
import com.bookshelf.domain.vo.ReviewBookVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    @Operation(summary = "Register new review",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Review registered in the system"),
                    @ApiResponse(responseCode = "400", description = "Review registration failed"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            })
    @PostMapping("/register-review")
    public ReviewRegistrationResponse createBook(
            @Valid @RequestBody ReviewRequest request) {

        ReviewBookVo reviewBookVo = reviewMapper.toReviewBookVo(request);

        return reviewService.registerReview(reviewBookVo);
    }

    @Operation(summary = "Delete review",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Delete review in the system"),
                    @ApiResponse(responseCode = "400", description = "Delete review failed"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            })
    @DeleteMapping("/delete-book/{id}")
    public ReviewRegistrationResponse deleteBook(
            @PathVariable Long id) {
        return reviewService.deleteReview(id);
    }

    @Operation(summary = "List all reviews",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List all review in the system"),
                    @ApiResponse(responseCode = "400", description = "List all review failed"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            })
    @GetMapping("/list-reviews")
    public List<ReviewResponse> listAllBooks() {
        return reviewService.listAllReviews();
    }

    @Operation(summary = "List reviews for id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List review in the system for id"),
                    @ApiResponse(responseCode = "400", description = "List review failed"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            })
    @GetMapping("/list-reviews/{id}")
    public ReviewResponse listReviewForId(@PathVariable Long id) {
        return reviewService.listReviewForId(id);
    }

    @Operation(summary = "Edit review",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Edit review in the system"),
                    @ApiResponse(responseCode = "400", description = "Edit review failed"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            })
    @PatchMapping("/edit-book")
    public ReviewResponse partialUpdateReview(
            @RequestBody ReviewRequest request) {

        ReviewBookVo bookVo = reviewMapper.toReviewBookVo(request);

        return reviewService.partialUpdate(bookVo);
    }

    @Operation(summary = "Search review for title",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Search review in the system for title"),
                    @ApiResponse(responseCode = "400", description = "Search review failed"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            })
    @GetMapping("/search-review/{title}")
    public ReviewResponse searchReviewByTitle(
            @PathVariable String title) {

        return reviewService.searchReviewByTitle(title);
    }
}
