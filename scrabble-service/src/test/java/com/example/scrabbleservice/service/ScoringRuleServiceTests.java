package com.example.scrabbleservice.service;

import com.example.scrabbleservice.dto.AddScoringRuleRequestDto;
import com.example.scrabbleservice.model.ScoringRule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ScoringRuleServiceTests {
    @Autowired
    private ScoringRuleService scoringRuleService;

    @Test
    public void should_AddScoringRulesByBatch() {
        List<AddScoringRuleRequestDto> dtoList = Arrays.asList(
                new AddScoringRuleRequestDto(Arrays.asList('A', 'E', 'I', 'O', 'U', 'L', 'N', 'S', 'T', 'R'), 1),
                new AddScoringRuleRequestDto(Arrays.asList('D', 'G'), 2),
                new AddScoringRuleRequestDto(Arrays.asList('B', 'C', 'M', 'P'), 3)
        );

        List<ScoringRule> added = scoringRuleService.addByBatch(dtoList);
        assertNotNull(added);
        assertEquals(16, added.size());

        ScoringRule ruleA = scoringRuleService.findByLetter('A');
        ScoringRule ruleG = scoringRuleService.findByLetter('G');
        ScoringRule ruleM = scoringRuleService.findByLetter('M');
        assertEquals(1, ruleA.getPoint());
        assertEquals(2, ruleG.getPoint());
        assertEquals(3, ruleM.getPoint());
    }

    @Test
    public void should_AddScoringRulesByBatch_WithUppercaseLetters() {
        List<AddScoringRuleRequestDto> dtoList = Arrays.asList(
                new AddScoringRuleRequestDto(Arrays.asList('D', 'G'), 2)
        );

        List<ScoringRule> added = scoringRuleService.addByBatch(dtoList);
        assertNotNull(added);
        assertEquals(2, added.size());

        ScoringRule ruleD = scoringRuleService.findByLetter('D');
        ScoringRule ruleG = scoringRuleService.findByLetter('G');
        assertEquals(2, ruleD.getPoint());
        assertEquals(2, ruleG.getPoint());
    }
}
