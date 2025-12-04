package com.example.scrabbleservice.controller;

import com.example.scrabbleservice.dto.AddScoringRuleRequestDto;
import com.example.scrabbleservice.model.ScoringRule;
import com.example.scrabbleservice.service.ScoringRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/scoring-rules")
public class ScoringRuleController {

    @Autowired
    private ScoringRuleService scoringRuleService;

    @PostMapping("/batch")
    public ResponseEntity<List<ScoringRule>> addScoringRulesByBatch(@RequestBody List<AddScoringRuleRequestDto> dtoList) {
        List<ScoringRule> savedScoringRules = scoringRuleService.addByBatch(dtoList);
        return ResponseEntity.ok(savedScoringRules);
    }

    @GetMapping
    public ResponseEntity<List<ScoringRule>> getAllScoringRules() {
        List<ScoringRule> scoringRules = scoringRuleService.getAll();
        return ResponseEntity.ok(scoringRules);
    }

    @GetMapping("/letter/{letter}")
    public ResponseEntity<ScoringRule> findByLetter(@PathVariable Character letter) {
        ScoringRule scoringRule = scoringRuleService.findByLetter(Character.toUpperCase(letter));
        if (scoringRule != null) {
            return ResponseEntity.ok(scoringRule);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
