package com.example.authentification.presenter.contract;

import com.example.authentification.data.model.Participant;

import java.util.List;

public interface IParticipantContract {
    interface Presenter {
        void getParticipants();
        void addParticipant(Participant participant);
    }
    interface View {
        void displayParticipants(List<Participant> participantList);
        void displayError();
        void addParticipantSuccess();
        void addParticipantFailed(String message);
    }
}
