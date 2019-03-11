package com.epam.java.currecyexchanger.model.entity;


import com.epam.java.currecyexchanger.model.enumer.CurrencyType;
import com.epam.java.currecyexchanger.util.ArgumentValidator;

import java.util.Objects;

/**
 * simple account class
 * @Author Ahmed Samy (serenitydiver@hotmail.com)
 */
public class Account {

    private CurrencyType currencyType;
    private double balance;

    public CurrencyType getCurrencyType() {
        return currencyType;
    }

    public Account(CurrencyType currencyType, double balance) {
        setCurrencyType(currencyType);
        setBalance(balance);
    }


    public void setCurrencyType(CurrencyType currencyType) {
        ArgumentValidator.checkForNull(currencyType);
        this.currencyType = currencyType;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
       ArgumentValidator.checkForNegativity(balance);
        this.balance = balance;
    }

    public void deposit(double money) {
        ArgumentValidator.checkForNegativity(money);
        balance += money;
    }

    public double withdraw(double money) {
        ArgumentValidator.checkForNegativity(money);
        double moneyRequired = 0;
        if (!(money > balance)) {
            moneyRequired = money;
            balance -= money;
        } else {
            String currency = currencyType.name();
            String balanceInAccount = String.valueOf(balance);
            System.out.printf("Account of: %s has not enough money for the withdraw, you have: %s %s and you request: %s.%c", currency,
                    balanceInAccount, currency,String.valueOf(money),'\n');
        }
        return moneyRequired;
    }

    public boolean equals(Object object){
        if(object == null || getClass() != object.getClass()){
            return false;
        }

        if(object == this){
            return true;
        }

        Account a = (Account) object;

        return Objects.equals(currencyType, a.currencyType) &&
                Double.doubleToLongBits(balance) == Double.doubleToLongBits(a.balance);
    }

    public int hashCode(){
        final int prime = 31;
        int result = 1;
        result = prime * result + ((currencyType != null) ? currencyType.hashCode() : 0);
        long temp = Double.doubleToLongBits(balance);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() +" {" +
                "currencyType=" + currencyType +
                ", balance=" + balance +
                '}';
    }
}
