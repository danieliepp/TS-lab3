package com.example.authentification.presenter.contract;

import java.util.HashMap;

public interface ILoginContract {
    interface Presenter{
        void doLogin(HashMap<String, String> map);
    }
    interface View{
        void onLoginSuccess();
        void onLoginFailed();
        void onInternetError();
    }
}
