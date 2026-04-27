package com.tavarez.aifeedbackreviewer.repository;

import model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findBySentiment(String sentiment);
    List<Review> findByScoreLessThanEqual(Integer score);
}
