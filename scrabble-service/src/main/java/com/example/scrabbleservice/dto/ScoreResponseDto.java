package com.example.scrabbleservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ScoreResponseDto {
    private Long id;
    private String word;
    private int value;
}
