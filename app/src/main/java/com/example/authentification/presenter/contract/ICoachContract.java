package com.example.authentification.presenter.contract;

import com.example.authentification.data.model.Coach;
import com.example.authentification.data.model.Country;

import java.util.List;

public interface ICoachContract {

    interface Presenter{
        void getCoaches();
    }

    interface View {
        void displayCoachesSuccess(List<Coach> coachList);
        void displayCoachesError();
    }
}
