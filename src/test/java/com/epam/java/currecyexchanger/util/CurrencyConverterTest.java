package com.epam.java.currecyexchanger.util;

import org.junit.Assert;
import org.junit.Test;

public class CurrencyConverterTest {

    private static final double DELTA = 0.00;
    private CurrencyConverter converter = new CurrencyConverter();

    @Test
    public void buyingCurrencyQuantityPass(){
        double myMoney = 100;
        double buyPrice = 1.25;

        double actual = 80;
        double expected = converter.buyingCurrencyQuantity(myMoney, buyPrice);

        Assert.assertEquals(expected, actual, DELTA);
    }

    @Test
    public void buyingCurrencyQuantityFail(){
        double myMoney = 90;
        double buyPrice = 1.15;

        double actual = 70;
        double expected = converter.buyingCurrencyQuantity(myMoney, buyPrice);

        Assert.assertEquals(expected, actual, DELTA);
    }

    @Test
    public void sellingCurrencyQuantityPass(){
        double myMoney = 100;
        double salePrice = 1.20;

        double actual = 120;
        double expected = converter.sellingCurrencyQuantity(myMoney, salePrice);

        Assert.assertEquals(expected, actual, DELTA);
    }

    @Test
    public void sellingCurrencyQuantityFail(){
        double myMoney = 110;
        double salePrice = 1.30;

        double actual = 100;
        double expected = converter.sellingCurrencyQuantity(myMoney, salePrice);

        Assert.assertEquals(expected, actual, DELTA);
    }
}
