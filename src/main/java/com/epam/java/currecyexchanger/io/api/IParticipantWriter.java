package com.epam.java.currecyexchanger.io.api;

import com.epam.java.currecyexchanger.model.entity.Participant;

import java.util.List;

public interface IParticipantWriter {
    void participantsWriter(String uri, List<Participant> participantList);
}
