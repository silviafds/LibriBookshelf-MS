package com.bookshelf.domain.exceptions;

public class ReviewNotFoundException extends RuntimeException {
    public ReviewNotFoundException(Long id) {
        super("Review com ID " + id + " não encontrado.");
    }

    public ReviewNotFoundException(String message) {
        super(message);
    }
}
