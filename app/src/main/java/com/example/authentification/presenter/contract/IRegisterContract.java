package com.example.authentification.presenter.contract;

import java.util.HashMap;

public interface IRegisterContract {

    interface Presenter{
        void registerNewUser(HashMap<String, String> map);
    }
    interface View{
        void registerNewUserMessage();
        void displayError();
        void displayErrorUserEmailOrUsername();
    }
}
