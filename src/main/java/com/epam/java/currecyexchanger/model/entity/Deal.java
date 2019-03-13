package com.epam.java.currecyexchanger.model.entity;

import com.epam.java.currecyexchanger.model.enumer.CurrencyType;
import com.epam.java.currecyexchanger.util.ArgumentValidator;

import java.util.concurrent.Callable;

/**
 * Simple Deal class for creating deals.
 * implementing callable so we can return the created deal then use it for the deal offer to the participants.
 * @Author Ahmed Samy (serenitydiver@hotmail.com)
 */
public class Deal implements Callable<Deal> {

    private int dealId;
    private CurrencyType currencyType;
    private double dealAmount;
    private double salePrice;
    private double buyPrice;


    public Deal() {
    }

    public Deal(int dealId, CurrencyType currencyType, double dealAmount, double salePrice, double buyPrice) {
        setDealId(dealId);
        setCurrencyType(currencyType);
        setDealAmount(dealAmount);
        setSalePrice(salePrice);
        setBuyPrice(buyPrice);
    }

    public Deal(int dealId, CurrencyType currencyType, double dealAmount) {
       setDealId(dealId);
        setCurrencyType(currencyType);
        setDealAmount(dealAmount);
    }

    public int getDealId() {
        return dealId;
    }

    public void setDealId(int dealId) {
        ArgumentValidator.checkForNegativity(dealId);
        this.dealId = dealId;
    }

    public CurrencyType getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(CurrencyType currencyType) {
        ArgumentValidator.checkForNull(currencyType);
        this.currencyType = currencyType;
    }

    public double getDealAmount() {
        return dealAmount;
    }

    public void setDealAmount(double dealAmount) {
        ArgumentValidator.checkForNegativity(dealAmount);
        this.dealAmount = dealAmount;
    }

    public double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(double salePrice) {
        ArgumentValidator.checkForNegativity(salePrice);
        this.salePrice = salePrice;
    }

    public double getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(double buyPrice) {
        ArgumentValidator.checkForNegativity(buyPrice);
        this.buyPrice = buyPrice;
    }

    @Override
    public String toString() {
        return "Deal{" +
                "dealId=" + dealId +
                ", currencyType=" + currencyType +
                ", dealAmount=" + dealAmount +
                ", salePrice=" + salePrice +
                ", buyPrice=" + buyPrice +
                '}';
    }

    @Override
    public Deal call() throws Exception {

        return this;
    }
}
