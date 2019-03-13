package com.epam.java.currecyexchanger.model.entity;

import com.epam.java.currecyexchanger.model.enumer.CurrencyType;
import com.epam.java.currecyexchanger.model.observerapi.Observer;
import com.epam.java.currecyexchanger.util.ArgumentValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;

/**
 * Simple participant class.
 * implementing observer so we can update each instance of the class by the new information from currency exchanger class.
 * implementing callable so we can use the created instance to determine which one of them will have the deal.
 * @Author Ahmed Samy (serenitydiver@hotmail.com)
 */
public class Participant implements Observer, Callable<Participant> {

    private int id;
    private String name;
    private List<Account> accounts = new ArrayList<>();

    public Participant() {

    }

    public Participant(int id, String name) {
        setId(id);
        setName(name);

    }
    public Participant(int id, String name, List<Account> accounts) {
        setId(id);
        setName(name);
        this.accounts = accounts;

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
    public void update(CurrencyExchanger exchanger) {
        System.out.println(this.getName() + " There is a new deal at the currency, exchange holding the following info: " +
                exchanger.getDeal());
    }


    @Override
    public Participant call() throws Exception {
        this.setName(Thread.currentThread().getName());
        return this;
    }
}
