package com.bookshelf.adapters.in.web.dto.request;

public record ReviewRequest(
        Long id,
        String reviewTitle,
        String bookName,
        String review,
        Long bookNote
) {

}
