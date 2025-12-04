package com.example.scrabbleservice.service;

import com.example.scrabbleservice.dto.AddScoringRuleRequestDto;
import com.example.scrabbleservice.model.ScoringRule;
import com.example.scrabbleservice.repository.ScoringRuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScoringRuleService {

    @Autowired
    private ScoringRuleRepository scoringRuleRepository;

    public List<ScoringRule> addByBatch(List<AddScoringRuleRequestDto> dtoList) {
        List<ScoringRule> mappedScoringRules = dtoList.stream()
                .flatMap(dto -> dto.getLetters().stream()
                        .map(letter -> new ScoringRule(Character.toUpperCase(letter), dto.getPoint())))
                .toList();
        return scoringRuleRepository.saveAll(mappedScoringRules);
    }

    public List<ScoringRule> getAll() {
        return scoringRuleRepository.findAll();
    }

    public ScoringRule findByLetter(Character letter) {
        return scoringRuleRepository.findById(letter).orElse(null);
    }
}
