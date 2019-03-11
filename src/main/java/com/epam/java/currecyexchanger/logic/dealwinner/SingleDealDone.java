package com.epam.java.currecyexchanger.logic.dealwinner;

import com.epam.java.currecyexchanger.logic.CurrencyConverter;
import com.epam.java.currecyexchanger.logic.dealwinner.api.DealWinner;
import com.epam.java.currecyexchanger.model.entity.CurrencyExchanger;
import com.epam.java.currecyexchanger.model.entity.Participant;
import com.epam.java.currecyexchanger.model.enumer.CurrencyType;
import com.epam.java.currecyexchanger.util.ArgumentValidator;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * For a single Deal and Multiple participants,
 * the deal will be presented for a list of participants, at the end if someone win the deal or not the deal will be closed.
 * @Author Ahmed Samy (serenitydiver@hotmail.com)
 */

public class SingleDealDone implements DealWinner {
    private CurrencyConverter converter;
    private CurrencyExchanger exchanger;
    private List<Future<Participant>> futureList;
    private CurrencyType fromCurrency;
    private double buyPrice;
    private CurrencyType toCurrency;

    public SingleDealDone (CurrencyExchanger exchanger, List<Future<Participant>> futureList,
                          CurrencyType fromCurrency, double buyPrice, CurrencyType toCurrency){

        converter = new CurrencyConverter();
        setExchanger(exchanger);
        setFutureList(futureList);
        setFromCurrency(fromCurrency);
        setBuyPrice(buyPrice);
        setToCurrency(toCurrency);


    }

    private void setExchanger(CurrencyExchanger exchanger) {
        ArgumentValidator.checkForNull(exchanger);
        this.exchanger = exchanger;
    }

    private void setFutureList(List<Future<Participant>> futureList) {
        ArgumentValidator.checkForNull(futureList);
        this.futureList = futureList;
    }

    private void setFromCurrency(CurrencyType fromCurrency) {
        ArgumentValidator.checkForNull(fromCurrency);
        this.fromCurrency = fromCurrency;
    }

    private void setBuyPrice(double buyPrice) {
        ArgumentValidator.checkForNegativity(buyPrice);
        this.buyPrice = buyPrice;
    }

    private void setToCurrency(CurrencyType toCurrency) {
        ArgumentValidator.checkForNull(toCurrency);
        this.toCurrency = toCurrency;
    }


    @Override
    public void dealWinner() {
        singleDealDone(exchanger, futureList, fromCurrency, buyPrice, toCurrency);
    }


    /**
     * @param exchanger    Instance of exchanger to get the deal details
     * @param futureList   that have all the participants threads to check if one of them have the requirement to do the
     *                     deal or not
     * @param fromCurrency currency that the client have and want to use to complete the deal
     * @param buyPrice     for the currency that the client want to buy
     * @param toCurrency   the currency that the client want to buy
     */
    private void singleDealDone(CurrencyExchanger exchanger, List<Future<Participant>> futureList,
                               CurrencyType fromCurrency, double buyPrice, CurrencyType toCurrency) {

        double dealAmount = exchanger.getDeal().getDealAmount();
        int dealChecker = 0;
        for (Future<Participant> future : futureList) {

            try {
                double currencyBalance = future.get().getAccountByCurrencyType(fromCurrency).getBalance();
                double currencyAvailability = converter.buyingCurrencyQuantity(currencyBalance, buyPrice);

                if (dealAmount < currencyAvailability) {
                    System.out.println("Amount of money in your account before the deal: " + currencyBalance + " " + fromCurrency + ".");
                    future.get().getAccountByCurrencyType(fromCurrency).withdraw(currencyAvailability);
                    future.get().getAccountByCurrencyType(toCurrency).deposit(dealAmount);
                    System.out.println("Deal is closed, your balance after the deal is: " + future.get());
                    exchanger.setDeal(null);
                    dealChecker++;
                    break;
                }
            } catch (InterruptedException e) {
                return;
            } catch (ExecutionException e) {
                e.getMessage();
            }
        }
        if (dealChecker == 0) {
            System.out.println("No one have enough money to make that deal: " + exchanger.getDeal());
        }
    }
}
