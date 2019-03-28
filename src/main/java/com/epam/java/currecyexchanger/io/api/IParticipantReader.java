package com.epam.java.currecyexchanger.io.api;

import com.epam.java.currecyexchanger.model.entity.Participant;

import java.util.List;

public interface IParticipantReader {
    List<Participant> participantsReader(String uri);
}
