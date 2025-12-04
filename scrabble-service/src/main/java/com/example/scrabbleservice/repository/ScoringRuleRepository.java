package com.example.scrabbleservice.repository;

import com.example.scrabbleservice.model.ScoringRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScoringRuleRepository extends JpaRepository<ScoringRule, Character> {
}
