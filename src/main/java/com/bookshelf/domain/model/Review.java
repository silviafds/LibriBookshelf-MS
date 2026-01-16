package com.bookshelf.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "review")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "review_title", nullable = false, length = 200)
    private String reviewTitle;

    @Column(name = "id_book_reviewed", nullable = false)
    private Long idBookReviewed;

    @Column(name = "review", nullable = false, length = 800)
    private String review;

    @Column(name = "book_note", nullable = false, length = 200)
    private Long bookNote;

    @Column(name = "id_user_reviewed", nullable = false)
    private Long idUserReviewed;
}
