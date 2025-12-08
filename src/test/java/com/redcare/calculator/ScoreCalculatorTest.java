package com.redcare.calculator;

import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class ScoreCalculatorTest {

    private final ScoreCalculator calculator = new ScoreCalculator();

    @Test
    void test() {
        assertThat(calculator.calculateScore(5, 12, OffsetDateTime.now().minusDays(10))).isEqualTo(78);
    }
}
