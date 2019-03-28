package com.epam.java.currecyexchanger.model.entity;

import com.epam.java.currecyexchanger.model.enumer.CurrencyType;
import com.epam.java.currecyexchanger.model.enumer.DealType;
import com.epam.java.currecyexchanger.util.ArgumentValidator;

import java.util.Objects;
import java.util.concurrent.Callable;

/**
 * Simple Deal class for creating deals.
 * implementing callable so we can return the created deal then use it for the deal offer to the participants.
 * @Author Ahmed Samy (serenitydiver@hotmail.com)
 */
public class Deal {

    private int dealId;
    private CurrencyType currencyType;
    private double dealAmount;
    private double salePrice;
    private double buyPrice;
    private DealType dealType;


    public Deal() {
    }
    public Deal(int dealId, CurrencyType currencyType, double dealAmount, double salePrice, double buyPrice, DealType dealType) {
        setDealId(dealId);
        setCurrencyType(currencyType);
        setDealAmount(dealAmount);
        setSalePrice(salePrice);
        setBuyPrice(buyPrice);
        setDealType(dealType);
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

    public DealType getDealType() {
        return dealType;
    }

    public void setDealType(DealType dealType) {
        ArgumentValidator.checkForNull(dealType);
        this.dealType = dealType;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (this == o) return true;
        Deal deal = (Deal) o;
        return dealId == deal.dealId &&
                Double.compare(deal.dealAmount, dealAmount) == 0 &&
                Double.compare(deal.salePrice, salePrice) == 0 &&
                Double.compare(deal.buyPrice, buyPrice) == 0 &&
                currencyType == deal.currencyType &&
                dealType == deal.dealType;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        long temp;
        result = prime * result + dealId;
        temp = Double.doubleToLongBits(dealAmount);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(salePrice);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(buyPrice);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + ((currencyType != null) ? currencyType.hashCode() : 0);
        result = prime * result + ((dealType != null) ? dealType.hashCode() : 0);

        return result;
    }

    @Override
    public String toString() {
        return "Deal{" +
                "dealId=" + dealId +
                ", currencyType=" + currencyType +
                ", dealAmount=" + dealAmount +
                ", salePrice=" + salePrice +
                ", buyPrice=" + buyPrice +
                ", dealType=" + dealType +
                '}';
    }
}
