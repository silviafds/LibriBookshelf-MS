package com.bookshelf.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewBookVo {
    private Long id;
    private String reviewTitle;
    private String bookName;
    private String review;
    private Long bookNote;
    private Long idUserReviewed;
}
