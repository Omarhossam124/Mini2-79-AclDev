package com.example.demo.repositories;



import com.example.demo.models.Rating;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface RatingRepository extends MongoRepository<Rating, String> {
    List<Rating> findByEntityIdAndEntityType(Long entityId, String entityType);
    List<Rating> findByScoreGreaterThan(int minScore);
}