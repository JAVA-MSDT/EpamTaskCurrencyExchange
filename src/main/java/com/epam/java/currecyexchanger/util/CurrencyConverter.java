package com.epam.java.currecyexchanger.util;

/**
 * @Author Ahmed Samy (serenitydiver@hotmail.com)
 */
public class CurrencyConverter {

    /**
     *
     * @param myMoney that i have in my account or i want to use
     * @param buyPrice the desired currency buying price
     * @return the amount of money that i can buy according to the money i have and the currency buying price
     */
    public double buyingCurrencyQuantity(double myMoney, double buyPrice){
        ArgumentValidator.checkForNegativity(myMoney);
        ArgumentValidator.checkForNegativity(buyPrice);
        if(buyPrice < 0){
            throw new ArithmeticException("Buy Price can ot be negative number");
        }
        return myMoney / buyPrice;
    }

    /**
     *
     * @param myMoney that i have in my account or i want to use
     * @param salePrice the desired currency sale price
     * @return the amount of money that i can sale according to the money i have and the currency sale price
     */
    public double sellingCurrencyQuantity(double myMoney, double salePrice){
        ArgumentValidator.checkForNegativity(myMoney);
        ArgumentValidator.checkForNegativity(salePrice);
        return  myMoney * salePrice;
    }
}
