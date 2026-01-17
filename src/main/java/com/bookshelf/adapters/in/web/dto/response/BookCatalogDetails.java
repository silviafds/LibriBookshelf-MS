package com.bookshelf.adapters.in.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookCatalogDetails {
    private Long bookId;
    private String title;
    private String author;
    private String isbn;
    private String genre;
    private Integer pageCount;
    private String publisher;
}
