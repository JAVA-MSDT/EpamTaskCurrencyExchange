package com.epam.java.currecyexchanger.io.read;

import com.epam.java.currecyexchanger.io.api.IParticipantReader;
import com.epam.java.currecyexchanger.model.entity.Participant;
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
 * To read the participantsfrom json files
 * @Author Ahmed Samy (serenitydiver@hotmail.com)
 */
public class ParticipantReader implements IParticipantReader {

    private static Logger logger = LogManager.getLogger();

    /**
     *
     * @param jsonLocation for the file that holds the participants details to write them in a list
     * @return list of participants
     */
    public List<Participant> participantsReader(String jsonLocation) {
        ArgumentValidator.checkForNullOrEmptyString(jsonLocation);

        ObjectMapper mapper = new ObjectMapper();
        List<Participant> participantList = Collections.emptyList();
        try {
            participantList = mapper.readValue(new File(jsonLocation), new TypeReference<List<Participant>>() {
            });
        } catch (IOException e) {
            logger.error("Reading exception from Participants Json file", e);
        }
        return participantList;
    }
}
