package com.epam.java.currecyexchanger.io.write;

import com.epam.java.currecyexchanger.io.api.IDealWriter;
import com.epam.java.currecyexchanger.model.entity.Deal;
import com.epam.java.currecyexchanger.util.ArgumentValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * To write the deal list to a Json file.
 * @Author Ahmed Samy (serenitydiver@hotmail.com)
 */
public class DealWriter implements IDealWriter {
    private static Logger logger = LogManager.getLogger();

    /**
     *
     * @param jsonLocation file to write into it the deal list
     * @param dealList to be written to a json file
     */
    public void dealsWriter(String jsonLocation, List<Deal> dealList) {
        ArgumentValidator.checkForNullOrEmptyString(jsonLocation);
        ArgumentValidator.checkForNull(dealList);

        ObjectMapper mapper = new ObjectMapper();

        try {
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            mapper.writeValue(new File(jsonLocation), dealList);

        } catch (IOException e) {
            logger.error("Writing exception to deal Json file", e);
        }
    }
}
