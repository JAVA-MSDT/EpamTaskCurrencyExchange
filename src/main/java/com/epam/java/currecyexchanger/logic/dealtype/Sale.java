package com.epam.java.currecyexchanger.logic.dealtype;

import com.epam.java.currecyexchanger.model.entity.Deal;
import com.epam.java.currecyexchanger.model.enumer.DealType;

/**
 * To sale a currency you need to check if the currency buying price is greater than your expected sale price.
 * @Author Ahmed Samy (serenitydiver@hotmail.com)
 */
public class Sale implements IDealType {

    private Deal deal;
    private double mySalePrice;

    public Sale(Deal deal, double mySalePrice) {
        this.deal = deal;
        this.mySalePrice = mySalePrice;
    }

    @Override
    public boolean isGoodDeal() {
        return deal.getDealType() == DealType.BUY && deal.getBuyPrice() > mySalePrice;
    }
}
