package com.epam.java.currecyexchanger.view;

import com.epam.java.currecyexchanger.model.entity.Account;
import com.epam.java.currecyexchanger.model.entity.CurrencyExchanger;
import com.epam.java.currecyexchanger.model.entity.Participant;
import com.epam.java.currecyexchanger.model.enumer.CurrencyType;
import com.epam.java.currecyexchanger.util.ArgumentValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * This class is to reduce the amount of lines in the Main class as a view helper, by creating helper methods.
 * @Author Ahmed Samy (serenitydiver@hotmail.com)
 */
public class ViewHelper {

    /**
     *
     * @param participantList from any source.
     * @param currencyExchanger to set it to the participants and to add the participant in it's observer list
     */
    public void addCurrencyExchangerAndObserver(List<Participant> participantList, CurrencyExchanger currencyExchanger){
        ArgumentValidator.checkForNull(participantList);
        ArgumentValidator.checkForNull(currencyExchanger);

        for (Participant participant : participantList) {
            participant.setCurrencyExchanger(currencyExchanger);
            currencyExchanger.addObserver(participant);
        }
    }

    /**
     *
     * @return CurrencyExchanger instance with accounts.
     */
    public CurrencyExchanger currencyExchangerAccount(){

        CurrencyExchanger currencyExchanger = CurrencyExchanger.getInstance();
        Account exchangerAccount = new Account(CurrencyType.BYN, 0);
        Account exchangerAccount1 = new Account(CurrencyType.EUR, 0);
        Account exchangerAccount2 = new Account(CurrencyType.USD, 0);
        List<Account> exchangerAccountList = new ArrayList<>();
        exchangerAccountList.add(exchangerAccount);
        exchangerAccountList.add(exchangerAccount1);
        exchangerAccountList.add(exchangerAccount2);
        currencyExchanger.setAccounts(exchangerAccountList);
        return currencyExchanger;
    }

}
