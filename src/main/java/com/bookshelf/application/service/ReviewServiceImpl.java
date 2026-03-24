package com.bookshelf.application.service;

import com.bookshelf.adapters.in.web.dto.response.ReviewRegistrationResponse;
import com.bookshelf.adapters.in.web.dto.response.ReviewResponse;
import com.bookshelf.adapters.out.client.CatalogClientService;
import com.bookshelf.adapters.out.client.UserServiceFeignClient;
import com.bookshelf.adapters.out.producer.ReviewKafkaProducer;
import com.bookshelf.application.mapper.ReviewMapper;
import com.bookshelf.application.ports.in.service.JwtService;
import com.bookshelf.application.ports.in.service.ReviewService;
import com.bookshelf.application.ports.out.repository.ReviewRepository;
import com.bookshelf.domain.enums.RegistrationStatus;
import com.bookshelf.domain.exceptions.ReviewAccessDeniedException;
import com.bookshelf.domain.exceptions.ReviewNotFoundException;
import com.bookshelf.domain.model.Review;
import com.bookshelf.domain.vo.ReviewBookVo;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Application service responsible for managing book reviews.
 *
 * This class implements the ReviewService interface and contains
 * the business logic for creating, deleting, updating, and
 * retrieving reviews.
 *
 * It also integrates with external services to enrich review data
 * with user and book information.
 */
@Slf4j
@Service
@Transactional
public class ReviewServiceImpl implements ReviewService {

    private final ReviewMapper reviewMapper;

    private final UserServiceFeignClient userServiceFeignClient;

    private final JwtService jwtService;

    private final ReviewKafkaProducer kafkaProducer;

    @Autowired
    private CatalogClientService catalogClient;

    @Autowired
    private ReviewRepository repository;

    public ReviewServiceImpl(ReviewMapper reviewMapper, UserServiceFeignClient userServiceFeignClient,
                             JwtService jwtService, ReviewKafkaProducer kafkaProducer) {
        this.reviewMapper = reviewMapper;
        this.userServiceFeignClient = userServiceFeignClient;
        this.jwtService = jwtService;
        this.kafkaProducer = kafkaProducer;
    }

    /**
     * Registers a new review after validating input data
     * and business rules.
     *
     * @param reviewBookVo review data
     * @return registration status response
     */
    @Override
    public ResponseEntity<ReviewRegistrationResponse> registerReview(ReviewBookVo reviewBookVo) {

        if (!isValid(reviewBookVo)) {
            throw new IllegalArgumentException(RegistrationStatus.MISSING_DATA.getDefaultMessage());
        }

        String validationError = validateReviewData(reviewBookVo);
        if (validationError != null) {
            throw new IllegalArgumentException(RegistrationStatus.VALIDATION_ERROR.getDefaultMessage());
        }

        Review review = reviewMapper.toReview(reviewBookVo);
        repository.save(review);

        kafkaProducer.sendReviewCreatedMessage(
                "Review: '" + review.getReviewTitle() + "' cadastrada com sucesso"
        );

        ReviewRegistrationResponse response = new ReviewRegistrationResponse();
        response.setMessage(RegistrationStatus.SUCCESS.getDefaultMessage());

        return ResponseEntity.ok(response);
    }

    /**
     * Deletes a review by its identifier.
     *
     * @param id review identifier
     * @return deletion response
     */
    @Override
    public ResponseEntity<ReviewRegistrationResponse> deleteReview(Long id) {

        Review review = repository.findById(id)
                .orElseThrow(() -> new ReviewNotFoundException(id));

        repository.delete(review);

        kafkaProducer.sendReviewDeleteMessage(
                "Review de id: " + id + " deletada com sucesso."
        );

        ReviewRegistrationResponse response = new ReviewRegistrationResponse();
        response.setMessage("Review removida com sucesso.");

        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves all reviews and enriches them with user
     * and book information from external services.
     *
     * @param tokenAuth authorization token
     * @return list of reviews
     */
    @Override
    public List<ReviewResponse> listAllReviews(String tokenAuth) {
        List<Review> reviews = repository.findAll();

        Set<Long> distinctUserIds = reviews.stream()
                .map(Review::getIdUserReviewed)
                .collect(Collectors.toSet());

        Set<Long> distinctBookNamesIds = reviews.stream()
                .map(Review::getIdBookReviewed)
                .collect(Collectors.toSet());

        Map<Long, String> userNames = new HashMap<>();
        Map<Long, String> bookNames = new HashMap<>();

        for (Long userId : distinctUserIds) {
            try {
                userNames.put(userId, userServiceFeignClient.getUserById(userId, tokenAuth));
            } catch (Exception e) {
                userNames.put(userId, "Usuário não disponível");
            }
        }

        for (Long bookId : distinctBookNamesIds) {
            try {
                bookNames.put(bookId, catalogClient.buscarTituloLivroNoCatalog(bookId));
            } catch (Exception e) {
                bookNames.put(bookId, "Livro não disponível");
            }
        }

        List<ReviewResponse> responses = new ArrayList<>();
        for (Review review : reviews) {
            ReviewResponse response = reviewMapper.reviewToReviewResponse(review);
            String userName = userNames.get(review.getIdUserReviewed());
            String nameBook = bookNames.get(review.getIdBookReviewed());
            response.setNameUserReviewed(userName);
            response.setNameBookReviewed(nameBook);
            responses.add(response);
        }

        if(responses != null) {
            kafkaProducer.sendReviewTopicAllMessage("Reviews retornadas: '" + responses);
        }

        return responses;
    }

    /**
     * Retrieves a single review by its identifier.
     *
     * @param id review identifier
     * @param tokenAuth authorization token
     * @return review details
     */
    @Override
    public ResponseEntity<ReviewResponse> listReviewForId(Long id, String tokenAuth) {

        Review review = repository.findById(id)
                .orElseThrow(() -> new ReviewNotFoundException(id));

        String userName = getUserNameSafely(review.getIdUserReviewed(), tokenAuth);
        String bookName = getBookNameSafely(review.getIdBookReviewed());

        ReviewResponse response = reviewMapper
                .reviewToReviewResponse(review, userName, bookName);

        kafkaProducer.sendReviewTopicForIdMessage(
                "Review: '" + response.getReviewTitle() + "' listada com sucesso"
        );

        return ResponseEntity.ok(response);
    }

    /**
     * Partially updates an existing review.
     *
     * @param id review identifier
     * @param vo updated review data
     * @return updated review
     */
    @Override
    public ReviewResponse partialUpdate(Long id, ReviewBookVo vo, String tokenAuth) {

        Review review = repository.findById(id)
                .orElseThrow(() -> new ReviewNotFoundException(id));

        Long userIdFromToken = jwtService.getUserIdFromToken(tokenAuth);

        if (!review.getIdUserReviewed().equals(userIdFromToken)) {
            throw new ReviewAccessDeniedException();
        }

        repository.updateReview(
                id,
                vo.getReviewTitle(),
                vo.getIdBookReviewed(),
                vo.getReview(),
                Long.valueOf(vo.getBookNote())
        );

        Review updatedReview = repository.findById(id)
                .orElseThrow(() -> new ReviewNotFoundException(id));

        String userName = getUserNameSafely(updatedReview.getIdUserReviewed(), tokenAuth);
        String bookName = getBookNameSafely(updatedReview.getIdBookReviewed());

        kafkaProducer.sendReviewEditMessage(
                "Review: '" + updatedReview.getReviewTitle() + "' editada com sucesso"
        );

        return reviewMapper.reviewToReviewResponse(updatedReview, userName, bookName);
    }

    private boolean isValid(ReviewBookVo vo) {
        return isNotBlank(vo.getReviewTitle())
                && vo.getIdBookReviewed() != null
                && isNotBlank(vo.getReview())
                && vo.getBookNote() != null;
    }

    private boolean isNotBlank(String value) {
        return value != null && !value.trim().isEmpty();
    }

    private String validateReviewData(ReviewBookVo reviewBookVo) {
        if (reviewBookVo.getReviewTitle().length() > 200) {
            return "Título não pode exceder 200 caracteres";
        }

        if (reviewBookVo.getBookNote() > 5 || reviewBookVo.getBookNote() < 0) {
            return "A nota da resenha tem que ser no minimo 0 e no máximo 5";
        }

        return null;
    }

    private String getUserNameSafely(Long userId, String tokenAuth) {
        try {
            return userServiceFeignClient.getUserById(userId, tokenAuth);
        } catch (Exception e) {
            return "Usuário não disponível";
        }
    }

    private String getBookNameSafely(Long bookId) {
        try {
            return catalogClient.buscarTituloLivroNoCatalog(bookId);
        } catch (Exception e) {
            return "Livro não disponível";
        }
    }
}
