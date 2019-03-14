package com.epam.java.currecyexchanger.fileoperator.read;

import com.epam.java.currecyexchanger.fileoperator.api.CurrencyExchangeReader;
import com.epam.java.currecyexchanger.model.entity.Deal;
import com.epam.java.currecyexchanger.model.entity.Participant;
import com.epam.java.currecyexchanger.util.ArgumentValidator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * To read the participants and the deals from json files
 * @Author Ahmed Samy (serenitydiver@hotmail.com)
 */
public class JsonReader implements CurrencyExchangeReader {
    private static Logger logger = LogManager.getLogger();
    private ObjectMapper mapper;

    /**
     *
     * @param jsonLocation for the file that holds the participants details to write them in a list
     * @return list of participants
     */
    public List<Participant> participantsReader(String jsonLocation) {
        ArgumentValidator.checkForNullOrEmptyString(jsonLocation);

        mapper = new ObjectMapper();
        List<Participant> participantList = null;
        try {
            participantList = mapper.readValue(new File(jsonLocation), new TypeReference<List<Participant>>() {
            });
        } catch (IOException e) {
            logger.error("Reading exception from Participants Json file", e);
        }
        return participantList;
    }

    /**
     *
     * @param jsonLocation for the file that holds the participants details to write them in a list
     * @return list of participants
     */
    public List<Deal> dealsReader(String jsonLocation) {
        ArgumentValidator.checkForNullOrEmptyString(jsonLocation);
        mapper = new ObjectMapper();
        List<Deal> dealList = null;
        try {
            dealList = mapper.readValue(new File(jsonLocation), new TypeReference<List<Deal>>() {
            });
        } catch (IOException e) {
            logger.error("Reading exception from Deals Json file", e);
        }
        return dealList;
    }


}
