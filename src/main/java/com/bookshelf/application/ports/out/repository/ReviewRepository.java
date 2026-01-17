package com.bookshelf.application.ports.out.repository;

import com.bookshelf.domain.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Modifying
    @Query("UPDATE Review r SET " +
            "r.reviewTitle  = :review_title, " +
            "r.idBookReviewed  = :id_book_reviewed, " +
            "r.review = :review, " +
            "r.bookNote  = :book_note " +
            "WHERE r.id = :id")
    int updateReview(
            @Param("id") Long id,
            @Param("review_title") String reviewTitle,
            @Param("id_book_reviewed") Long idBookReviewed,
            @Param("review") String review,
            @Param("book_note") Long bookNote);

}
