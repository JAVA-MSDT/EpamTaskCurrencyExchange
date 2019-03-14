package com.epam.java.currecyexchanger.logic.futurelist;

import com.epam.java.currecyexchanger.model.entity.Account;
import com.epam.java.currecyexchanger.model.entity.CurrencyExchanger;
import com.epam.java.currecyexchanger.model.entity.Participant;
import com.epam.java.currecyexchanger.model.enumer.CurrencyType;
import com.epam.java.currecyexchanger.util.ArgumentValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * @Author Ahmed Samy (serenitydiver@hotmail.com)
 */
public class FutureParticipants {


    public FutureParticipants(){

    }
    /**
     * @param executorService to create a thread pool
     * @param exchanger       instance of exchanger to create a list of observer
     * @param currencyType    to setup a currency type for the money account
     * @param threadQuantity  the number of thread that we need to create
     * @return List<Future < Participant>> to deal with them later for the deal setup.
     */
    public List<Future<Participant>> randomParticipantList(ExecutorService executorService, CurrencyExchanger exchanger,
                                                           CurrencyType currencyType, int threadQuantity) {
        ArgumentValidator.checkForNull(executorService);
        ArgumentValidator.checkForNull(exchanger);
        ArgumentValidator.checkForNull(currencyType);
        ArgumentValidator.checkForNegativity(threadQuantity);

        List<Future<Participant>> list = new ArrayList<>();
        for (int i = 0; i < threadQuantity; i++) {
            int randomNumber = new Random().nextInt(10);
            int randomAmount = new Random().nextInt(1000);
            Account account = new Account(currencyType, randomAmount);
            Account dollar = new Account(CurrencyType.USD, randomAmount);
            Account euro = new Account(CurrencyType.EUR, randomAmount);
            Participant participant = new Participant(randomNumber, Thread.currentThread().getName());
            participant.addAccount(account);
            participant.addAccount(dollar);
            participant.addAccount(euro);
            exchanger.addObserver(participant);
            Future<Participant> participantFuture = executorService.submit(participant);
            list.add(participantFuture);

        }
        return list;
    }

    /**
     * @param executorService to create a thread pool
     * @param exchanger       instance of exchanger to create a list of observer
     * @param participantList to add to the future list and for the observer list, whatever the list source, file, console,,,, etc.
     * @return List<Future < Participant>> to deal with them later for the deal setup.
     */
    public List<Future<Participant>> customParticipantList(ExecutorService executorService, CurrencyExchanger exchanger,
                                                           List<Participant> participantList) {
        ArgumentValidator.checkForNull(executorService);
        ArgumentValidator.checkForNull(exchanger);
        ArgumentValidator.checkForNull(participantList);

        List<Future<Participant>> list = new ArrayList<>();
        for (Participant participant : participantList) {
            exchanger.addObserver(participant);
            Future<Participant> participantFuture = executorService.submit(participant);
            list.add(participantFuture);
        }
        return list;
    }

}
