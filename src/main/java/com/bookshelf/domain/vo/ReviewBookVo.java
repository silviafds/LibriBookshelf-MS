package com.bookshelf.domain.vo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewBookVo {
    @NotNull
    private Long id;

    @NotBlank
    private String reviewTitle;

    @NotNull
    private Long idBookReviewed;

    @NotBlank
    private String review;

    @NotNull
    private Integer bookNote;

    @NotNull
    private Long idUserReviewed;
}
