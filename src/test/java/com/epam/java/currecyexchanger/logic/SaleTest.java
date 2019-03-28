package com.epam.java.currecyexchanger.logic;

import com.epam.java.currecyexchanger.logic.dealtype.Sale;
import com.epam.java.currecyexchanger.model.entity.Deal;
import com.epam.java.currecyexchanger.model.enumer.CurrencyType;
import com.epam.java.currecyexchanger.model.enumer.DealType;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class SaleTest {
    private static Deal deal;
    private static double mySalePrice;
    private static Sale sale;

    @BeforeClass
    public static void init(){
        deal = new Deal(1, CurrencyType.BYN, 100, 1.25, 1.20, DealType.BUY);

    }

    @Test
    public void isGoodDealTestReturnsTrue(){
        mySalePrice = 1.10;
        sale = new Sale(deal, mySalePrice);

        boolean expected = sale.isGoodDeal();
        Assert.assertTrue(expected);
    }

    @Test
    public void isGoodDealTestReturnsFalse(){
        mySalePrice = 1.20;
        sale = new Sale(deal, mySalePrice);

        boolean expected = sale.isGoodDeal();
        Assert.assertFalse(expected);
    }
}
