package br.com.wal.delivery.component;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by marcelotozzi on 03/09/14.
 */
public class CostCalculator {
    public static BigDecimal calculateCost(double distance, BigDecimal autonomy, BigDecimal liter) {
        Money moneyLiter = Money.of(CurrencyUnit.of("BRL"), liter);
        Money costByKM = moneyLiter.dividedBy(autonomy, RoundingMode.CEILING);
        Money totalCost = costByKM.multipliedBy(distance, RoundingMode.CEILING);
        return totalCost.getAmount();
    }
}
