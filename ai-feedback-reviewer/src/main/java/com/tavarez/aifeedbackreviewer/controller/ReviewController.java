package com.tavarez.aifeedbackreviewer.controller;

import com.tavarez.aifeedbackreviewer.services.SentimentService;
import model.Review;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final SentimentService service;

    public ReviewController(SentimentService service) {
        this.service = service;
    }

    @PostMapping("/analyze")
    public ResponseEntity<Review> createAnalysis(@RequestBody ReviewRequest request) {
        return ResponseEntity.ok(service.analyze(request.name(), request.comment()));
    }

    static record ReviewRequest(String name, String comment) {
    }

    @GetMapping
    public ResponseEntity<List<Review>> getAllReviews() {
        return ResponseEntity.ok(service.findAll());
    }
}
