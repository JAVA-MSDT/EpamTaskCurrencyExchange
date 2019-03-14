package com.epam.java.currecyexchanger.logic.futurelist;


import com.epam.java.currecyexchanger.model.entity.Deal;
import com.epam.java.currecyexchanger.model.enumer.CurrencyType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * @Author Ahmed Samy (serenitydiver@hotmail.com)
 */
public class FutureDeal {


    public FutureDeal(){

    }

    /**
     * @param executorService to create a thread pool
     * @param currencyType    to setup a currency type for the money account
     * @param dealQuantity  the number of thread that we need to create
     * @return List<Future < Deal>> to deal with them later for the deal setup.
     */
    public List<Future<Deal>> randomDealList(ExecutorService executorService, CurrencyType currencyType, int dealQuantity) {
        List<Future<Deal>> list = new ArrayList<>();
        for (int i = 0; i < dealQuantity; i++) {
            int randomNumber = new Random().nextInt(10);
            double randomAmount =  (Math.random() + 1) * 200;
            double randomSalePrice = (Math.random() + 1) * 1.5;
            double randomBuyPrice = (Math.random() + 1) * 1.5;
            Deal deal = new Deal(randomNumber, currencyType, randomAmount, randomSalePrice, randomBuyPrice);
            Future<Deal> dealFuture = executorService.submit(deal);
            list.add(dealFuture);
        }
        return list;
    }


    /**
     * @param executorService to create a thread pool
     * @param dealList to work with them as deal threads, whatever from where the deal coming, file, console,,,, etc.
     * @return List<Future < Deal>> to deal with them later for the deal setup.
     */
    public List<Future<Deal>> customDealList(ExecutorService executorService, List<Deal> dealList) {
        List<Future<Deal>> list = new ArrayList<>();
        for (Deal deal : dealList) {
            Future<Deal> dealFuture = executorService.submit(deal);
            list.add(dealFuture);
        }
        return list;
    }



}
