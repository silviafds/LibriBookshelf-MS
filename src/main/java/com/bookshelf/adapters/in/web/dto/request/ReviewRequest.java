package com.bookshelf.adapters.in.web.dto.request;

public record ReviewRequest(
        String reviewTitle,
        String bookName,
        String review,
        Long bookNote
) {

}
