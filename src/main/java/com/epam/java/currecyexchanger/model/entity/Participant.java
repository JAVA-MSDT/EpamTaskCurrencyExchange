package com.epam.java.currecyexchanger.model.entity;

import com.epam.java.currecyexchanger.logic.dealtype.Buy;
import com.epam.java.currecyexchanger.logic.dealtype.IDealType;
import com.epam.java.currecyexchanger.logic.dealtype.Sale;
import com.epam.java.currecyexchanger.model.enumer.CurrencyType;
import com.epam.java.currecyexchanger.model.observerapi.Observer;
import com.epam.java.currecyexchanger.util.ArgumentValidator;
import com.epam.java.currecyexchanger.util.CurrencyConverter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Simple participant class.
 * implementing observer so we can update each instance of the class by the new information from currency exchanger class.
 * implementing callable so we can use the created instance to determine which one of them will have the deal.
 *
 * @Author Ahmed Samy (serenitydiver@hotmail.com)
 */
public class Participant implements Observer, Runnable {

    private int id;
    private String name;
    private List<Account> accounts = new ArrayList<>();
    private CurrencyExchanger currencyExchanger;
    private Lock lock = new ReentrantLock(false);
    private Condition condition = lock.newCondition();

    public Participant() {

    }

    public Participant(int id, String name) {
        setId(id);
        setName(name);

    }

    public Participant(int id, String name, List<Account> accounts) {
        setId(id);
        setName(name);
        setAccounts(accounts);
    }

    public Participant(int id, String name, List<Account> accounts, CurrencyExchanger currencyExchanger) {
        setId(id);
        setName(name);
        setAccounts(accounts);
        this.currencyExchanger = currencyExchanger;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        ArgumentValidator.checkForNegativity(id);
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        ArgumentValidator.checkForNullOrEmptyString(name);
        this.name = name;
    }

    public Account getAccountByCurrencyType(CurrencyType currencyType) {
        ArgumentValidator.checkForNull(currencyType);
        Account accountIn = null;
        for (Account a : accounts) {
            if (a.getCurrencyType() == currencyType) {
                accountIn = a;
            }
        }
        return accountIn;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void addAccount(Account account) {
        ArgumentValidator.checkForNull(account);
        accounts.add(account);
    }

    private void setAccounts(List<Account> accounts) {
        ArgumentValidator.checkForNull(accounts);
        this.accounts = accounts;
    }

    public void setCurrencyExchanger(CurrencyExchanger currencyExchanger) {
        ArgumentValidator.checkForNull(currencyExchanger);
        this.currencyExchanger = currencyExchanger;
    }

    public CurrencyExchanger getCurrencyExchanger() {
        return currencyExchanger;
    }


    public boolean equals(Object ob) {
        if (ob == null || getClass() != ob.getClass()) {
            return false;
        }

        if (ob == this) {
            return true;
        }

        Participant p = (Participant) ob;

        return this.id == p.id &&
                Objects.equals(name, p.name) &&
                Objects.equals(accounts, p.accounts);
    }

    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + ((name != null) ? name.hashCode() : 0);
        result = prime * result + ((accounts != null) ? accounts.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Participant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", account=" + accounts +
                '}';
    }

    @Override
    public void update() {
        System.out.println(this.getName() + " There is a new deal at the currency exchanger, holding the following info: " +
                currencyExchanger.getDeal());
    }


    @Override
    public void run() {

        CurrencyConverter converter = new CurrencyConverter();
        Deal deal = getCurrencyExchanger().getDeal();
        try {
            lock.lock();
            condition.await(1, TimeUnit.SECONDS);
            if (deal != null) {
                if (deal.getDealType() == com.epam.java.currecyexchanger.model.enumer.DealType.SALE) {

                    buyCurrency(deal, currencyExchanger, converter, this);

                } else if (deal.getDealType() == com.epam.java.currecyexchanger.model.enumer.DealType.BUY) {

                    saleCurrency(deal, currencyExchanger, converter, this);
                }
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }


    }


    private void buyCurrency(Deal deal, CurrencyExchanger currencyExchanger, CurrencyConverter converter, Participant participant) {
            double myBuyPrice = Math.random() + 1;
            IDealType buyState = new Buy(deal, myBuyPrice);

            double currencySalePrice = deal.getBuyPrice();
            double dealAmount = deal.getDealAmount();
            double dealAmountInBYN = converter.sellingCurrencyQuantity(dealAmount, currencySalePrice);

            if (buyState.isGoodDeal()) {

                if (participant.getAccountByCurrencyType(CurrencyType.BYN).getBalance() > dealAmountInBYN) {
                    participant.getAccountByCurrencyType(CurrencyType.BYN).withdraw(dealAmountInBYN);
                    participant.getAccountByCurrencyType(deal.getCurrencyType()).deposit(dealAmount);
                    currencyExchanger.getAccountByCurrencyType(CurrencyType.BYN).deposit(dealAmountInBYN);
                    deal.setDealAmount(0);
                    currencyExchanger.setDeal(null);
                    System.out.println("Buying Currency.........................");
                    System.out.println(this + "::" + deal);

                }
            }

    }


    private void saleCurrency(Deal deal, CurrencyExchanger currencyExchanger, CurrencyConverter converter, Participant participant) {
            double mySalePrice = Math.random() + 1;
            IDealType saleState = new Sale(deal, mySalePrice);

            double currencyBalance = participant.getAccountByCurrencyType(CurrencyType.USD).getBalance();
            double currencyBalanceInBYN = converter.sellingCurrencyQuantity(currencyBalance, deal.getSalePrice());
            double dealBalance = deal.getDealAmount();
            if (saleState.isGoodDeal()) {

                if (currencyBalanceInBYN <= dealBalance) {
                    double amountOfCurrencyToWithdraw = converter.buyingCurrencyQuantity(currencyBalance, deal.getSalePrice());
                    participant.getAccountByCurrencyType(CurrencyType.USD).withdraw(amountOfCurrencyToWithdraw);
                    participant.getAccountByCurrencyType(CurrencyType.BYN).deposit(dealBalance);
                    currencyExchanger.getAccountByCurrencyType(CurrencyType.USD).deposit(amountOfCurrencyToWithdraw);

                    deal.setDealAmount(0);
                    currencyExchanger.setDeal(null);
                    System.out.println("Selling Currency........................");
                    System.out.println(this + "::" + deal);

                }
            }
    }

}

