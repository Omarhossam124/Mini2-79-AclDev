package com.example.demo.models;



import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "ratings")
public class Rating {

    @Id
    private String id;
    private Long entityId;
    private String entityType; // "captain", "customer", or "trip"
    private Integer score;     // rating from 1 to 5
    private String comment;
    private LocalDateTime ratingDate;

    public Rating() {}

    public Rating(Long entityId, String entityType, Integer score, String comment, LocalDateTime ratingDate) {
        this.entityId = entityId;
        this.entityType = entityType;
        this.score = score;
        this.comment = comment;
        this.ratingDate = ratingDate;
    }

    public Rating(String id, Long entityId, String entityType, Integer score, String comment, LocalDateTime ratingDate) {
        this.id = id;
        this.entityId = entityId;
        this.entityType = entityType;
        this.score = score;
        this.comment = comment;
        this.ratingDate = ratingDate;
    }

    public void setId(String id) {
    }

    // Getters and Setters
}