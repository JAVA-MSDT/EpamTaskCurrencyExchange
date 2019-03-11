package com.epam.java.currecyexchanger.model.entity;

import com.epam.java.currecyexchanger.model.observerapi.Observer;
import com.epam.java.currecyexchanger.model.observerapi.Observable;
import com.epam.java.currecyexchanger.util.ArgumentValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * SingleTon Currency Exchanger.
 * implements observer so we cna notify the participants if we have a new deal added at the exchanger.
 * @Author Ahmed Samy (serenitydiver@hotmail.com)
 */
public class CurrencyExchanger implements Observable {

    private static Logger logger = LogManager.getLogger();
    private static CurrencyExchanger instance;
    private Deal deal;
    private boolean dealDone;
    private List<Observer> observerList = new ArrayList<>();

    private CurrencyExchanger() {
        if(instance != null){
            throw new RuntimeException("Please use getInstance() method");
        }
    }

    public synchronized static CurrencyExchanger getInstance(){
        if(instance == null){
            instance = new CurrencyExchanger();
        }
        return instance;
    }

    public Deal getDeal() {
        return deal;
    }

    public void setDeal(Deal deal) {
        this.deal = deal;
    }

    public boolean isDealDone() {
        return dealDone;
    }

    public void setDealDone(boolean dealDone) {
        this.dealDone = dealDone;
    }

    public List<Observer> getObserverList() {
        return observerList;
    }


    public void addDeal(Deal deal) {
        ArgumentValidator.checkForNull(deal);
        if (this.deal == null) {
            setDeal(deal);
            notifyObserver();
        }
    }
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (this == o) return true;
        CurrencyExchanger cE = (CurrencyExchanger) o;
        return dealDone == cE.dealDone &&
                Objects.equals(deal, cE.deal);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((deal != null) ? deal.hashCode() : 0);
        result = prime * result + (dealDone ? 1231 : 1237);
        return result;
    }

    @Override
    public String toString() {
        return "CurrencyExchanger{" +
                "deal=" + deal +
                ", dealDone=" + dealDone +
                '}';
    }

    @Override
    public void addObserver(Observer observer) {
        ArgumentValidator.checkForNull(observer);
        observerList.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        ArgumentValidator.checkForNull(observer);
        observerList.remove(observer);
    }

    @Override
    public void notifyObserver() {
        System.out.println();
        System.out.println("****************( Participants Notify Starts )******************");
        for (Observer o : observerList) {
            o.update(this);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                logger.error("InterruptedException at notify observer method",e);
            }
        }
        System.out.println("****************( Participants Notify Ends )******************");
        System.out.println();
    }

}
