package com.epam.java.currecyexchanger.view;


import com.epam.java.currecyexchanger.logic.Action;
import com.epam.java.currecyexchanger.logic.dealwinner.MultiDealDone;
import com.epam.java.currecyexchanger.logic.dealwinner.SingleDealDone;
import com.epam.java.currecyexchanger.logic.dealwinner.api.DealWinner;
import com.epam.java.currecyexchanger.model.entity.CurrencyExchanger;
import com.epam.java.currecyexchanger.model.entity.Deal;
import com.epam.java.currecyexchanger.model.entity.Participant;
import com.epam.java.currecyexchanger.model.enumer.CurrencyType;
import com.epam.java.currecyexchanger.view.detailsprinter.ParticipantDetails;

import java.util.List;
import java.util.concurrent.*;

/**
 * @Author Ahmed Samy (serenitydiver@hotmail.com)
 */
public class Main {

    public static void main(String[] args) {

        Action action = new Action();

        ExecutorService executorService = Executors.newCachedThreadPool();


        CurrencyExchanger exchanger = CurrencyExchanger.getInstance();
        Deal deal = new Deal(2, CurrencyType.USD, 400);


        List<Future<Participant>> futureList = action.participantFutureList(executorService, exchanger, CurrencyType.BYN, 5);
        List<Future<Deal>> dealFutureList = action.dealFutureList(executorService, CurrencyType.USD, 4);

        //exchanger.addDeal(deal);

        DealWinner dealWinner = new MultiDealDone(exchanger, futureList, dealFutureList, CurrencyType.BYN, 2.1450, CurrencyType.USD);
       // dealWinner = new SingleDealDone(exchanger, futureList, CurrencyType.BYN, 2.145, CurrencyType.USD);
        dealWinner.dealWinner();

        System.out.println("///////////////////// Participants Info ///////////////////////////////////");
        ParticipantDetails participantDetails = new ParticipantDetails(exchanger.getObserverList());
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(participantDetails, 2, 8, TimeUnit.SECONDS);
        executorService.shutdown();

    }
}
