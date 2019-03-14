package com.epam.java.currecyexchanger.fileoperator.api;

import com.epam.java.currecyexchanger.model.entity.Deal;
import com.epam.java.currecyexchanger.model.entity.Participant;

import java.util.List;

public interface CurrencyExchangeReader {

    List<Participant> participantsReader(String uri);
    List<Deal> dealsReader(String uri);
}
