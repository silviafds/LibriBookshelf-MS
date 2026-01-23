package com.bookshelf.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class ReviewAccessDeniedException extends RuntimeException {
    public ReviewAccessDeniedException() {
        super("Usuário não autorizado a editar esta review");
    }
}
