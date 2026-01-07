package com.bookshelf.adapters.in.web.controller;

import com.bookshelf.adapters.in.web.dto.request.ReviewRequest;
import com.bookshelf.adapters.in.web.dto.response.ReviewRegistrationResponse;
import com.bookshelf.adapters.in.web.dto.response.ReviewResponse;
import com.bookshelf.application.mapper.ReviewMapper;
import com.bookshelf.application.ports.in.service.ReviewService;
import com.bookshelf.domain.vo.ReviewBookVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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
                    @ApiResponse(responseCode = "201", description = "Review registered in the system"),
                    @ApiResponse(responseCode = "400", description = "Review registration failed"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            })
    @PostMapping("/register-review")
    public ReviewRegistrationResponse createReview(
            @Valid @RequestBody ReviewRequest request) {

        ReviewBookVo reviewBookVo = reviewMapper.toReviewBookVo(request);

        return reviewService.registerReview(reviewBookVo);
    }

    @Operation(summary = "Delete review",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Delete deleted successfully"),
                    @ApiResponse(responseCode = "400", description = "Delete review failed"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            })
    @DeleteMapping("/delete-review/{id}")
    public ReviewRegistrationResponse deleteReview(
            @PathVariable Long id) {
        return reviewService.deleteReview(id);
    }

    @Operation(summary = "List all reviews",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List all review in the system"),
                    @ApiResponse(responseCode = "400", description = "List all review failed"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            })
    @GetMapping("/list-all-reviews")
    public List<ReviewResponse> listAllReviews() {
        return reviewService.listAllReviews();
    }

    @Operation(summary = "List reviews for id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "LList review in the system for id",
                            content = @Content(
                                    mediaType = "application/json",
                                    array  = @ArraySchema(schema = @Schema(implementation = ReviewResponse.class))
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "List review failed"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            })
    @GetMapping("/list-only-review/{id}")
    public ReviewResponse listReviewForId(@PathVariable Long id) {
        return reviewService.listReviewForId(id);
    }

    @Operation(summary = "Edit review",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Edit review in the system"),
                    @ApiResponse(responseCode = "400", description = "Edit review failed"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            })
    @PatchMapping("/edit-review/{id}")
    public ReviewResponse updateReview(@PathVariable Long id,
            @RequestBody ReviewRequest request) {

        ReviewBookVo bookVo = reviewMapper.toReviewBookVo(request);

        return reviewService.partialUpdate(id, bookVo);
    }

    @Operation(summary = "Search reviews by book title",
            description = "Returns a list of reviews for books matching the given title (partial match)",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "List of reviews found",
                            content = @Content(
                                    mediaType = "application/json",
                                    array  = @ArraySchema(schema = @Schema(implementation = ReviewResponse.class))
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "No reviews found for the given book title"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid request parameter"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error"
                    )
            })
    @GetMapping("/search")
    public List<ReviewResponse> searchReviewsByBookTitle(
            @Parameter(
                    description = "DDD",
                    required = true,
                    example = "Clean Code"
            )
            @RequestParam String title) {

        return reviewService.searchReviewByTitle(title);
    }
}
