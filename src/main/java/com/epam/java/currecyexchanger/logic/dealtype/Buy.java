package com.epam.java.currecyexchanger.logic.dealtype;

import com.epam.java.currecyexchanger.model.entity.Deal;
import com.epam.java.currecyexchanger.model.enumer.DealType;

/**
 * To buy a currency you need to check that the currency sale price is less than your expecting buy price.
 * @Author Ahmed Samy (serenitydiver@hotmail.com)
 */
public class Buy implements IDealType {

    private Deal deal;
    private double myBuyPrice;

    public Buy(Deal deal, double myBuyPrice) {
        this.deal = deal;
        this.myBuyPrice = myBuyPrice;
    }

    @Override
    public boolean isGoodDeal() {
        return deal.getDealType() == DealType.SALE && deal.getSalePrice() < myBuyPrice;
    }
}
