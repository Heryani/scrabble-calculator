package com.example.scrabbleservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class AddScoringRuleRequestDto {
    private List<Character> letters;
    private int point;
}
