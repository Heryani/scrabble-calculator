package com.example.scrabbleservice.controller;

import com.example.scrabbleservice.dto.AddNewScoreRequestDto;
import com.example.scrabbleservice.dto.ErrorResponse;
import com.example.scrabbleservice.dto.ScoreResponseDto;
import com.example.scrabbleservice.model.Score;
import com.example.scrabbleservice.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/scores")
public class ScoreController {

    @Autowired
    private ScoreService scoreService;

    @PostMapping("/new")
    public ResponseEntity<?> addNewScore(@RequestBody AddNewScoreRequestDto dto) {
        try {
            Score newScore = scoreService.save(dto.getWord(), dto.getValue());
            ScoreResponseDto response = scoreService.toDto(newScore);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            ErrorResponse errorResponse = new ErrorResponse(400, e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    @GetMapping("/top/{n}")
    public ResponseEntity<List<ScoreResponseDto>> getTopNScores(@PathVariable int n) {
        List<ScoreResponseDto> response = scoreService.toDto(scoreService.getTopNScores(n));
        return ResponseEntity.ok(response);
    }
}
