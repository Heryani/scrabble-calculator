package com.example.scrabbleservice.service;

import com.example.scrabbleservice.model.Word;
import com.example.scrabbleservice.repository.WordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WordService {

    public static final int MAX_LENGTH = 10;

    @Autowired
    private WordRepository wordRepository;

    public Word save(String text) {
        String uppercaseText = text.toUpperCase();
        return findByText(uppercaseText)
                .orElseGet(() -> {
                    if (uppercaseText.length() > MAX_LENGTH) {
                        throw new IllegalArgumentException(String.format("Word length exceeds the maximum limit of %d characters.", MAX_LENGTH));
                    }
                    return wordRepository.save(new Word(uppercaseText));
                });
    }

    private Optional<Word> findByText(String text) {
        return wordRepository.findByText(text);
    }
}
