package com.epam.java.currecyexchanger.logic;

import com.epam.java.currecyexchanger.logic.dealtype.Buy;
import com.epam.java.currecyexchanger.model.entity.Deal;
import com.epam.java.currecyexchanger.model.enumer.CurrencyType;
import com.epam.java.currecyexchanger.model.enumer.DealType;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class BuyTest {
    private static Deal deal;
    private static double myBuyPrice;
    private static Buy buy;

    @BeforeClass
    public static void init(){
        deal = new Deal(1, CurrencyType.USD, 100, 1.25, 1.20, DealType.SALE);

    }

    @Test
    public void isGoodDealTestReturnsTrue(){
        myBuyPrice = 1.30;
        buy = new Buy(deal, myBuyPrice);

        boolean expected = buy.isGoodDeal();
        Assert.assertTrue(expected);
    }

    @Test
    public void isGoodDealTestReturnsFalse(){
        myBuyPrice = 1.20;
        buy = new Buy(deal, myBuyPrice);

        boolean expected = buy.isGoodDeal();
        Assert.assertFalse(expected);
    }
}
