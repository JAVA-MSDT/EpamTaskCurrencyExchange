package com.epam.java.currecyexchanger.io.write;

import com.epam.java.currecyexchanger.io.api.IParticipantWriter;
import com.epam.java.currecyexchanger.model.entity.Participant;
import com.epam.java.currecyexchanger.util.ArgumentValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.List;
/**
 * To write the Participants list to a Json file.
 * @Author Ahmed Samy (serenitydiver@hotmail.com)
 */
public class ParticipantWriter implements IParticipantWriter {

    private static Logger logger = LogManager.getLogger();

    /**
     *
     * @param jsonLocation file to write into it the participants list
     * @param participantList to be written to a json file
     */
    public void participantsWriter(String jsonLocation, List<Participant> participantList) {
        ArgumentValidator.checkForNullOrEmptyString(jsonLocation);
        ArgumentValidator.checkForNull(participantList);

        ObjectMapper mapper = new ObjectMapper();

        try {
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            mapper.writeValue(new File(jsonLocation), participantList);

        } catch (IOException e) {
            logger.error("Writing exception to participant Json file", e);
        }
    }
}
