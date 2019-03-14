package com.epam.java.currecyexchanger.view;


import com.epam.java.currecyexchanger.fileoperator.read.JsonReader;
import com.epam.java.currecyexchanger.fileoperator.write.JsonWriter;
import com.epam.java.currecyexchanger.logic.futurelist.FutureDeal;
import com.epam.java.currecyexchanger.logic.futurelist.FutureParticipants;
import com.epam.java.currecyexchanger.logic.dealwinner.DealResult;
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

        String participantsWinnerJson = "json/winners.json";
        String participantsJson = "json/participants.json";
        String completedDeals = "json/completedDeals.json";
        String unCompletedDeals = "json/unCompletedDeals.json";
        String fromDeals = "json/deals.json";

        ExecutorService executorService = Executors.newCachedThreadPool();
        CurrencyExchanger exchanger = CurrencyExchanger.getInstance();
        FutureDeal futureDeal = new FutureDeal();
        FutureParticipants futureParticipants = new FutureParticipants();
        JsonWriter writer = new JsonWriter();
        JsonReader reader = new JsonReader();

        List<Participant> participantList = reader.participantsReader(participantsJson);
        List<Deal> dealList = reader.dealsReader(fromDeals);

        List<Future<Participant>> futureList = futureParticipants.customParticipantList(executorService, exchanger, participantList);
        List<Future<Deal>> dealFutureList = futureDeal.customDealList(executorService, dealList);


        DealResult dealWinner = new DealResult(exchanger, futureList, dealFutureList, CurrencyType.BYN);

        dealWinner.dealWinner();
        writer.participantsWriter(participantsWinnerJson, dealWinner.getWinnerList());
        writer.dealsWriter(completedDeals, dealWinner.getClosedDeal());
        writer.dealsWriter(unCompletedDeals, dealWinner.getUnClosedDeal());

        System.out.println("///////////////////// Participants Info ///////////////////////////////////");
        ParticipantDetails participantDetails = new ParticipantDetails(exchanger.getObserverList());
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(participantDetails, 2, 8, TimeUnit.SECONDS);
        executorService.shutdown();

    }
}
