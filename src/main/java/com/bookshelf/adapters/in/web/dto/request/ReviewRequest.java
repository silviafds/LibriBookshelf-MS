package com.bookshelf.adapters.in.web.dto.request;

public record ReviewRequest(
        String reviewTitle,
        Long idBookReviewed,
        String review,
        Long bookNote,
        Long idUserReviewed
) {

}
