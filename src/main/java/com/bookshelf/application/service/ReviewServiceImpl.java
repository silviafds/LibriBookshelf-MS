package com.bookshelf.application.service;

import com.bookshelf.adapters.in.web.dto.response.ReviewRegistrationResponse;
import com.bookshelf.adapters.in.web.dto.response.ReviewResponse;
import com.bookshelf.application.mapper.ReviewMapper;
import com.bookshelf.application.ports.in.service.ReviewService;
import com.bookshelf.application.ports.out.repository.ReviewRepository;
import com.bookshelf.domain.enums.RegistrationStatus;
import com.bookshelf.domain.exceptions.ReviewNotFoundException;
import com.bookshelf.domain.model.Review;
import com.bookshelf.domain.vo.ReviewBookVo;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@Transactional
public class ReviewServiceImpl implements ReviewService {

    private final ReviewMapper reviewMapper;

    @Autowired
    private ReviewRepository repository;

    public ReviewServiceImpl(ReviewMapper reviewMapper) {
        this.reviewMapper = reviewMapper;
    }

    @Override
    public ReviewRegistrationResponse registerReview(ReviewBookVo reviewBookVo) {
        ReviewRegistrationResponse response = new ReviewRegistrationResponse();

        try {
            if (!isValid(reviewBookVo)) {
                response.setStatus(400);
                response.setMessage(String.valueOf(RegistrationStatus.MISSING_DATA.getDefaultMessage()));
                return response;
            }

            String validationError = validateReviewData(reviewBookVo);
            if (validationError != null) {
                response.setStatus(400);
                response.setMessage(String.valueOf(RegistrationStatus.VALIDATION_ERROR.getDefaultMessage()));
                return response;
            }

            Review review = reviewMapper.toReview(reviewBookVo);
            repository.save(review);

            response.setStatus(200);
            response.setMessage(String.valueOf(RegistrationStatus.SUCCESS.getDefaultMessage()));

        } catch (DataIntegrityViolationException e) {
            response.setStatus(400);
            response.setMessage(String.valueOf(RegistrationStatus.DATABASE_ERROR.getDefaultMessage()));

        } catch (Exception e) {
            response.setStatus(500);
            response.setMessage(String.valueOf(RegistrationStatus.UNKNOWN_ERROR.getDefaultMessage()));
        }

        return response;
    }

    @Override
    public ReviewRegistrationResponse deleteReview(Long id) {
        if (!repository.existsById(id)) {
            throw new ReviewNotFoundException(id);
        }

        Review review = repository.findById(id).get();
        repository.delete(review);

        ReviewRegistrationResponse response = new ReviewRegistrationResponse();
        response.setStatus(200);
        response.setMessage("Review removido do banco de dados.");
        return response;
    }

    @Override
    public List<ReviewResponse> listAllReviews() {
        List<Review> review = repository.findAll();
        return reviewMapper.reviewListToReviewResponseList(review);
    }

    @Override
    public ReviewResponse listReviewForId(Long id) {
        if (!repository.existsById(id)) {
            throw new ReviewNotFoundException(id);
        }

        Review book = repository.findById(id).get();

        return reviewMapper.reviewToReviewResponse(book);
    }

    @Override
    public ReviewResponse partialUpdate(ReviewBookVo vo) {

        if (!repository.existsById(vo.getId())) {
            throw new ReviewNotFoundException(vo.getId());
        }

        repository.updateReview(vo.getId(), vo.getReviewTitle(), vo.getBookName(), vo.getReview(), vo.getBookNote());

        Review updatedReview = repository.findById(vo.getId()).get();

        return reviewMapper.reviewToReviewResponse(updatedReview);
    }

    @Override
    public ReviewResponse searchReviewByTitle(String title) {
        Review review = repository.searchReviewByTitle(title);

        if (review == null) {
            throw new ReviewNotFoundException("Livro com título '" + title + "' não existe no catálogo");
        }

        return reviewMapper.reviewToReviewResponse(review);
    }

    private boolean isValid(ReviewBookVo reviewBookVo) {
        return reviewBookVo.getReviewTitle() != null && !reviewBookVo.getReviewTitle().trim().isEmpty() &&
                reviewBookVo.getBookName() != null && !reviewBookVo.getBookName().trim().isEmpty() &&
                reviewBookVo.getReview() != null && !reviewBookVo.getReview().trim().isEmpty() &&
                reviewBookVo.getBookNote() != null;
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
}
