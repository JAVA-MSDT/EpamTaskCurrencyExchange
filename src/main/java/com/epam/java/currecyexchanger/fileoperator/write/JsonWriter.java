package com.epam.java.currecyexchanger.fileoperator.write;

import com.epam.java.currecyexchanger.fileoperator.api.CurrencyExchangeWriter;
import com.epam.java.currecyexchanger.model.entity.Deal;
import com.epam.java.currecyexchanger.model.entity.Participant;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * To write Participants and deal list to Json file.
 * @Author Ahmed Samy (serenitydiver@hotmail.com)
 */
public class JsonWriter implements CurrencyExchangeWriter {
    private static Logger logger = LogManager.getLogger();
    private ObjectMapper mapper;

    /**
     *
     * @param jsonLocation file to write into it the participants list
     * @param participantList to be written to a json file
     */
    public void participantsWriter(String jsonLocation, List<Participant> participantList) {
        mapper = new ObjectMapper();

        try {
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            mapper.writeValue(new File(jsonLocation), participantList);

        } catch (IOException e) {
           logger.error("Writing exception to participant Json file", e);
        }
    }

    /**
     *
     * @param jsonLocation file to write into it the deal list
     * @param dealList to be written to a json file
     */
    public void dealsWriter(String jsonLocation, List<Deal> dealList) {
        mapper = new ObjectMapper();

        try {
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            mapper.writeValue(new File(jsonLocation), dealList);

        } catch (IOException e) {
            logger.error("Writing exception to deal Json file", e);
        }
    }
}
