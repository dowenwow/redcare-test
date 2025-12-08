package com.redcare.calculator;

import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;

@Component
public class ScoreCalculator {
    public double calculateScore(int stars, int forks, OffsetDateTime updatedAt) {
        long daysSinceLastUpdate = ChronoUnit.DAYS.between(updatedAt, OffsetDateTime.now());
        return stars * 2 + forks * 1.5 + (daysSinceLastUpdate < 30 ? 50 : 10);
    }
}
