package com.epam.java.currecyexchanger.fileoperator.api;

import com.epam.java.currecyexchanger.model.entity.Deal;
import com.epam.java.currecyexchanger.model.entity.Participant;

import java.util.List;

public interface CurrencyExchangeWriter {

    void participantsWriter(String uri, List<Participant> participantList);
    void dealsWriter(String uri, List<Deal> dealList);
}
