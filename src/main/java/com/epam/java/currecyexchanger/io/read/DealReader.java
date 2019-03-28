package com.epam.java.currecyexchanger.io.read;

import com.epam.java.currecyexchanger.io.api.IDealReader;
import com.epam.java.currecyexchanger.model.entity.Deal;
import com.epam.java.currecyexchanger.util.ArgumentValidator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * To read the deals from json files
 * @Author Ahmed Samy (serenitydiver@hotmail.com)
 */
public class DealReader implements IDealReader {
    private static Logger logger = LogManager.getLogger();

    /**
     *
     * @param jsonLocation for the file that holds the participants details to write them in a list
     * @return list of participants or in case of the deal list is empty or something went wrong
     * we will return emptyList
     */
    public List<Deal> dealsReader(String jsonLocation) {
        ArgumentValidator.checkForNullOrEmptyString(jsonLocation);
        ObjectMapper mapper = new ObjectMapper();
        List<Deal> dealList = Collections.emptyList();
        try {
            dealList = mapper.readValue(new File(jsonLocation), new TypeReference<List<Deal>>() {
            });
        } catch (IOException e) {
            logger.error("Reading exception from Deals Json file", e);
        }
        return dealList;
    }


}
