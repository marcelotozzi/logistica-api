package br.com.wal.delivery.component;

import org.junit.Test;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class CostCalculatorTest {

    @Test
    public void itShouldCalcutateTheCost() {
        assertThat(
                CostCalculator.calculateCost(25, BigDecimal.valueOf(10), BigDecimal.valueOf(2.50)),
                is(equalTo(BigDecimal.valueOf(6.25))));
    }

}