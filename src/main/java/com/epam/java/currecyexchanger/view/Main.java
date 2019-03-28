package com.epam.java.currecyexchanger.view;


import com.epam.java.currecyexchanger.io.read.DealReader;
import com.epam.java.currecyexchanger.io.read.ParticipantReader;
import com.epam.java.currecyexchanger.model.entity.CurrencyExchanger;
import com.epam.java.currecyexchanger.model.entity.Deal;
import com.epam.java.currecyexchanger.model.entity.Participant;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author Ahmed Samy (serenitydiver@hotmail.com)
 */
public class Main {

    private static final String PARTICIPANTS = "json/participants.json";
    private static final String DEALS = "json/deals.json";
    private static Lock lock = new ReentrantLock(false);
    private static Condition condition = lock.newCondition();

    public static void main(String[] args) {

        ParticipantReader participantReader = new ParticipantReader();
        List<Participant> participants = participantReader.participantsReader(PARTICIPANTS);

        DealReader reader = new DealReader();
        List<Deal> deals = reader.dealsReader(DEALS);

        ViewHelper viewHelper = new ViewHelper();
        CurrencyExchanger currencyExchanger = viewHelper.currencyExchangerAccount();
        viewHelper.addCurrencyExchangerAndObserver(participants, currencyExchanger);

        ExecutorService executorService = Executors.newCachedThreadPool();

        for (Deal de : deals) {
            currencyExchanger.addDeal(de);
            for (Participant part : participants) {
                executorService.execute(new Thread(part));
                try {
                    lock.lock();
                    condition.await(1, TimeUnit.SECONDS);
                    if (currencyExchanger.getDeal() == null) {
                        break;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
            if (currencyExchanger.getDeal() != null) {
                System.out.println("Deal is done without any action" + currencyExchanger.getDeal());
                currencyExchanger.setDeal(null);
            }
        }
        executorService.shutdown();
    }
}
