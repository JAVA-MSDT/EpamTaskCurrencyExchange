package com.epam.java.currecyexchanger.logic.dealwinner;

import com.epam.java.currecyexchanger.util.CurrencyConverter;
import com.epam.java.currecyexchanger.logic.dealwinner.api.DealWinner;
import com.epam.java.currecyexchanger.model.entity.CurrencyExchanger;
import com.epam.java.currecyexchanger.model.entity.Deal;
import com.epam.java.currecyexchanger.model.entity.Participant;
import com.epam.java.currecyexchanger.model.enumer.CurrencyType;
import com.epam.java.currecyexchanger.util.ArgumentValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * For multiple deals and multiple participants,
 * the deal will be presented first to all the participants at the end of the participants cycle the deal will be closed
 * whether some one win the deal or not, then a new deal will be opened for the same participants then the cycle start again,
 * doing the same process until the deal list is end.
 * @Author Ahmed Samy (serenitydiver@hotmail.com)
 */
public class DealResult implements DealWinner {

    private CurrencyConverter converter;
    private CurrencyExchanger exchanger;
    private List<Future<Participant>> futureList;
    private List<Future<Deal>> dealList;
    private CurrencyType fromCurrency;
    private List<Participant> winnerList = new ArrayList<>();
    private List<Deal> closedDeal = new ArrayList<>();
    private List<Deal> unClosedDeal = new ArrayList<>();

    public DealResult(CurrencyExchanger exchanger, List<Future<Participant>> futureList, List<Future<Deal>> dealList,
                      CurrencyType fromCurrency){

        converter = new CurrencyConverter();
        setExchanger(exchanger);
        setFutureList(futureList);
        setDealList(dealList);
        setFromCurrency(fromCurrency);


    }

    private void setExchanger(CurrencyExchanger exchanger) {
        ArgumentValidator.checkForNull(exchanger);
        this.exchanger = exchanger;
    }

    private void setFutureList(List<Future<Participant>> futureList) {
        ArgumentValidator.checkForNull(futureList);
        this.futureList = futureList;
    }

    private void setDealList(List<Future<Deal>> dealList) {
        ArgumentValidator.checkForNull(dealList);
        this.dealList = dealList;
    }

    private void setFromCurrency(CurrencyType fromCurrency) {
        ArgumentValidator.checkForNull(fromCurrency);
        this.fromCurrency = fromCurrency;
    }


    public List<Participant> getWinnerList() {
        return winnerList;
    }

    public List<Deal> getClosedDeal() {
        return closedDeal;
    }

    public List<Deal> getUnClosedDeal() {
        return unClosedDeal;
    }

    @Override
    public void dealWinner() {
        multiDealDone(exchanger, futureList, dealList, fromCurrency);
    }

    /**
     * @param exchanger    Instance of exchanger to get the deal details
     * @param futureList   that have all the participants threads to check if one of them have the requirement to do the
     *                     deal or not
     * @param fromCurrency currency that the client have and want to use to complete the deal
     */
    private void multiDealDone(CurrencyExchanger exchanger, List<Future<Participant>> futureList,
                              List<Future<Deal>> dealList, CurrencyType fromCurrency) {

        int dealChecker = 0;
        Deal deal = null;
        for (Future<Deal> d : dealList) {

            try {
                System.out.println("=================================( Deal Id: " + d.get().getDealId() + " has Started)===============================");
                exchanger.addDeal(d.get());
                deal = d.get();
                double dealAmount = d.get().getDealAmount();
                for (Future<Participant> future : futureList) {

                    double currencyBalance = future.get().getAccountByCurrencyType(fromCurrency).getBalance();
                    double currencyAvailability = converter.buyingCurrencyQuantity(currencyBalance, deal.getSalePrice());

                    if (dealAmount < currencyAvailability) {
                        System.out.println("Amount of money in your account before the deal: " + currencyBalance + " " + fromCurrency + ".");
                        future.get().getAccountByCurrencyType(fromCurrency).withdraw(currencyAvailability);
                        future.get().getAccountByCurrencyType(deal.getCurrencyType()).deposit(dealAmount);
                        winnerList.add(future.get());
                        deal.setDealAmount(0);
                        closedDeal.add(deal);
                        System.out.println("Deal is closed, your balance after the deal is: " + future.get());
                        exchanger.setDeal(null);
                        dealChecker++;
                        break;
                    }

                }
                if(deal.getDealAmount() != 0){
                    unClosedDeal.add(deal);
                }
                if (exchanger.getDeal() != null) {
                    exchanger.setDeal(null);
                    dealChecker = 0;
                }
                TimeUnit.SECONDS.sleep(2);

            } catch (InterruptedException e) {
                return;
            } catch (ExecutionException e) {
                e.getMessage();
            }
            if (dealChecker == 0) {
                System.out.println("No one have enough money to make that deal: " + deal);
            }
            System.out.println("=================================( Deal Results Ends )===============================");
            System.out.println();
        }

    }
}
