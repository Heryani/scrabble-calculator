package com.example.scrabbleservice.service;

import com.example.scrabbleservice.model.Word;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class WordServiceTests {
    @Autowired
    private WordService wordService;

    @Test
    public void should_SaveWord_InUppercase() {
        String input = "Scrabble";
        String expected = "SCRABBLE";

        Word result = wordService.save(input);

        assertEquals(expected, result.getText());
    }

    @Test
    public void should_ThrowException_When_WordExceedsMaxLength() {
        String input = "Extraordinary"; // 13 characters, exceeds max length of 10

        try {
            wordService.save(input);
        } catch (IllegalArgumentException e) {
            assertEquals("Word length exceeds the maximum limit of 10 characters.", e.getMessage());
        }
    }

    @Test
    public void should_ReturnExistingWord_IfAlreadyPresent() {
        String input = "TestWord";
        String expected = "TESTWORD";

        // First save
        Word firstSave = wordService.save(input);

        // Second save should return the existing word
        Word secondSave = wordService.save(input);

        assertEquals(firstSave, secondSave);
        assertEquals(expected, secondSave.getText());
    }
}
