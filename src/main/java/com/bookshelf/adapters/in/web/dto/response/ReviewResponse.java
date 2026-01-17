package com.bookshelf.adapters.in.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResponse {
    private Long id;
    private String reviewTitle;
    private String nameBookReviewed;
    private String review;
    private Long bookNote;
    private String nameUserReviewed;
}
