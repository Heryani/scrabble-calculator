package com.example.scrabbleservice.dto;

import lombok.Data;

@Data
public class AddNewScoreRequestDto {
    private String word;
    private Integer value;
}
