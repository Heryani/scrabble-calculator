package com.example.scrabbleservice.repository;

import com.example.scrabbleservice.model.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ScoreRepository extends JpaRepository<Score, Long> {
}
