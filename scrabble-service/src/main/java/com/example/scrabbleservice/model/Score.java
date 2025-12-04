package com.example.scrabbleservice.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class Score {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false)
    @JoinColumn(name = "word_id")
    private Word word;
    private int value;

    public Score(Word word, int value) {
        this.word = word;
        this.value = value;
    }
}
