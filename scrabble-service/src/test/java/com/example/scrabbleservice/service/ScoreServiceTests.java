package com.example.scrabbleservice.service;

import com.example.scrabbleservice.model.Score;
import com.example.scrabbleservice.model.ScoringRule;
import com.example.scrabbleservice.model.Word;
import com.example.scrabbleservice.repository.ScoreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ScoreServiceTests {

    @Autowired
    private ScoreService scoreService;

    @MockitoBean
    private ScoringRuleService scoringRuleServiceMock;

    @MockitoBean
    private ScoreRepository scoreRepositoryMock;

    @Nested
    class SaveScoreTests {
        @BeforeEach
        public void setup() {
            when(scoringRuleServiceMock.findByLetter('H')).thenReturn(new ScoringRule('H', 4));
            when(scoringRuleServiceMock.findByLetter('E')).thenReturn(new ScoringRule('E', 1));
            when(scoringRuleServiceMock.findByLetter('L')).thenReturn(new ScoringRule('L', 1));
            when(scoringRuleServiceMock.findByLetter('O')).thenReturn(new ScoringRule('O', 1));

            when(scoreRepositoryMock.save(any())).thenAnswer(invocation -> {
                Score savedScore = invocation.getArgument(0);
                savedScore.setId(1L);
                return savedScore;
            });
        }

        @Test
        public void should_SaveScore_When_TextAndValueAreValid() {
            String text = "hello";
            int value = 8;

            Score savedScore = scoreService.save(text, value);
            assertNotNull(savedScore.getId());
            assertEquals(text.toUpperCase(), savedScore.getWord().getText());
            assertEquals(value, savedScore.getValue());
        }

        @Test
        public void should_ThrowException_When_TextIsEmpty() {
            String emptyText = "";
            int value = 5;

            try {
                scoreService.save(emptyText, value);
            } catch (IllegalArgumentException e) {
                assertEquals("Word cannot be empty.", e.getMessage());
            }
        }

        @Test
        public void should_ThrowException_When_TextIsNull() {
            String emptyText = null;
            int value = 5;

            try {
                scoreService.save(emptyText, value);
            } catch (IllegalArgumentException e) {
                assertEquals("Word cannot be empty.", e.getMessage());
            }
        }

        @Test
        public void should_ThrowException_When_ValueIsIncorrect() {
            String text = "hello";
            int incorrectValue = 10;

            try {
                scoreService.save(text, incorrectValue);
            } catch (IllegalArgumentException e) {
                assertEquals("Submitted score value (10) does not match calculated score value (8).", e.getMessage());
            }
        }

        @Test
        public void should_ThrowException_When_TextExceedsMaxLengths() {
            String text = "hellooooooo"; // 11 characters
            int value = 14;

            try {
                scoreService.save(text, value);
            } catch (IllegalArgumentException e) {
                assertEquals("Word length exceeds the maximum limit of 10 characters.", e.getMessage());
            }
        }

        @Test
        public void shouldNot_SaveScore_When_LetterHasNoScoringRule() {
            String text = "hi"; // 'i' has no scoring rule defined in the mock
            int value = 5;

            try {
                scoreService.save(text, value);
            } catch (IllegalArgumentException e) {
                assertEquals("Cannot find ScoringRule for letter I", e.getMessage());
            }
        }
    }

    @Nested
    class GetTopNScoresTests {
        @Test
        public void should_ReturnTop5Scores_When_Requested5() {
            List<Score> allRecords = Arrays.asList(
                    new Score(new Word("JAVA"), 14),
                    new Score(new Word("SPRING"), 10),
                    new Score(new Word("BOOT"), 10),
                    new Score(new Word("WORLD"), 9),
                    new Score(new Word("HELLO"), 8),
                    new Score(new Word("TEST"), 7),
                    new Score(new Word("CODE"), 6)
            );
            when(scoreRepositoryMock.findAll(Sort.by(Sort.Direction.DESC, "value"))).thenReturn(allRecords);

            var topScores = scoreService.getTopNScores(5);

            assertEquals(5, topScores.size());
            for (int i = 0; i < topScores.size() - 1; i++) {
                assertTrue(topScores.get(i).getValue() >= topScores.get(i + 1).getValue());
            }
        }

        @Test
        public void should_ReturnUniqueTopScores_When_DuplicatesExist() {
            List<Score> allRecords = Arrays.asList(
                    new Score(new Word("JAVA"), 14),
                    new Score(new Word("JAVA"), 14), // duplicate
                    new Score(new Word("SPRING"), 10),
                    new Score(new Word("BOOT"), 10),
                    new Score(new Word("HELLO"), 8)
            );
            when(scoreRepositoryMock.findAll(Sort.by(Sort.Direction.DESC, "value"))).thenReturn(allRecords);

            var topScores = scoreService.getTopNScores(5);

            assertEquals(4, topScores.size()); // should return only unique scores
            for (int i = 0; i < topScores.size() - 1; i++) {
                assertTrue(topScores.get(i).getValue() >= topScores.get(i + 1).getValue());
            }
        }

        @Test
        public void should_ReturnAllScores_When_RequestedMoreThanAvailable() {
            List<Score> allRecords = Arrays.asList(
                    new Score(new Word("JAVA"), 14),
                    new Score(new Word("SPRING"), 10),
                    new Score(new Word("BOOT"), 10)
            );
            when(scoreRepositoryMock.findAll(Sort.by(Sort.Direction.DESC, "value"))).thenReturn(allRecords);

            var topScores = scoreService.getTopNScores(5);

            assertEquals(3, topScores.size());
            for (int i = 0; i < topScores.size() - 1; i++) {
                assertTrue(topScores.get(i).getValue() >= topScores.get(i + 1).getValue());
            }
        }
    }
}
