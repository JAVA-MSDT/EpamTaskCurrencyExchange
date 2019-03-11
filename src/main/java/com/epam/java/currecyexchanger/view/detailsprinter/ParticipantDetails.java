package com.epam.java.currecyexchanger.view.detailsprinter;

import com.epam.java.currecyexchanger.model.observerapi.Observer;
import com.epam.java.currecyexchanger.model.entity.Participant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * To print the participants details.
 * Implementing Runnable so we can used it to print the participants details on a regular time scheduled using the
 * ScheduledExecutorService class or any similar way.
 * @Author Ahmed Samy (serenitydiver@hotmail.com)
 */
public class ParticipantDetails implements Runnable {
    private static Logger logger = LogManager.getLogger();
    private List<Observer> observerList;

    public ParticipantDetails(List<Observer> observerList){
        this.observerList = observerList;
    }

    @Override
    public void run() {
        for (Observer o : observerList){
            Participant p = (Participant) o;
            System.out.println(p.getId() + " : " + p.getName() + " : " + p.getAccounts());
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                logger.error("InterruptedException in the print details class", e);
            }
        }
        System.out.println("(==================================)");
    }
}
