package com.example.scrabbleservice.service;

import com.example.scrabbleservice.dto.ScoreResponseDto;
import com.example.scrabbleservice.model.Score;
import com.example.scrabbleservice.model.ScoringRule;
import com.example.scrabbleservice.model.Word;
import com.example.scrabbleservice.repository.ScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ScoreService {

    @Autowired
    private ScoreRepository scoreRepository;
    @Autowired
    private ScoringRuleService scoringRuleService;
    @Autowired
    private WordService wordService;

    public Score save(String text, Integer value) {
        if (text == null || text.isEmpty()) {
            throw new IllegalArgumentException("Word cannot be empty.");
        }

        int calculatedScoreValue = calculateScoreValue(text);
        if (doesNotMatch(value, calculatedScoreValue)) {
            throw new IllegalArgumentException(String.format("Submitted score value (%d) does not match calculated score value (%d).", value, calculatedScoreValue));
        }

        Word word = wordService.save(text);
        Score score = new Score(word, calculatedScoreValue);

        return scoreRepository.save(score);
    }

    public List<Score> getTopNScores(int n) {
        List<Score> sortedScores = scoreRepository.findAll(Sort.by(Sort.Direction.DESC, "value"));
        List<Score> uniqueScores = handleDuplicates(sortedScores);

        if (uniqueScores.size() < n) {
            n = uniqueScores.size();
        }
        return uniqueScores.subList(0, n);
    }

    public ScoreResponseDto toDto(Score score) {
        return new ScoreResponseDto(score.getId(), score.getWord().getText(), score.getValue());
    }

    public List<ScoreResponseDto> toDto(List<Score> scores) {
        return scores.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private int calculateScoreValue(String text) {
        return text
                .chars()
                .map(letter -> {
                    char charLetter = (char) letter;
                    ScoringRule rule = scoringRuleService.findByLetter(charLetter);
                    if (rule == null) {
                        throw new IllegalArgumentException("Cannot find ScoringRule for letter " + charLetter);
                    }
                    return rule.getPoint();
                }).sum();
    }

    private static boolean doesNotMatch(Integer value, int calculatedScoreValue) {
        return value != null && calculatedScoreValue != value;
    }

    private List<Score> handleDuplicates(List<Score> sortedScores) {
        Set<String> seen = new HashSet<>();
        return sortedScores.stream()
                .filter(score -> seen.add(score.getWord().getText() + ":" + score.getValue()))
                .collect(Collectors.toList());
    }
}
