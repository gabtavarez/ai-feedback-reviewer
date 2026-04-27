package com.tavarez.aifeedbackreviewer.services;

import com.tavarez.aifeedbackreviewer.repository.ReviewRepository;
import model.Review;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SentimentService {

    private final ChatClient chatClient;
    private final ReviewRepository repository;

    public SentimentService(ChatClient.Builder builder, ReviewRepository repository) {
        this.chatClient = builder.build();
        this.repository = repository;
    }

    public Review analyze(String customerName, String comment) {
        String prompt = """
                Analise o sentimento do seguinte comentário de um cliente:
                "%s"
                
                Responda APENAS com uma linha no formato: SENTIMENTO;SCORE
                Onde SENTIMENTO é (POSITIVO, NEGATIVO ou NEUTRO) e SCORE é de 0 a 10.
                """.formatted(comment); // Injeta o comentário do usuário dentro do prompt.

        String response = chatClient.prompt(prompt).call().content();

        String[] parts = response.split(";");
        String sentiment = parts[0].trim();
        Integer score = Integer.parseInt(parts[1].trim());

        Review review = new Review();
        review.setCustomerName(customerName);
        review.setComment(comment);
        review.setSentiment(sentiment);
        review.setScore(score);

        return repository.save(review);
    }

    public List<Review> findAll() {
        return repository.findAll();
    }
}