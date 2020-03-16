package com.example.authentification.presenter;


import android.util.Log;

import com.example.authentification.data.RetrofitClient;
import com.example.authentification.data.model.User;
import com.example.authentification.presenter.contract.IRegisterContract;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterPresenter implements IRegisterContract.Presenter {

    private static final String TAG = RegisterPresenter.class.getName();
    private IRegisterContract.View registerView;

    public RegisterPresenter(IRegisterContract.View registerView) {
        this.registerView = registerView;
    }

    @Override
    public void registerNewUser(HashMap<String, String> map) {
        Call<User> callRegisterNewCoach = RetrofitClient.getRetrofitInstance()
                .registerNewUser(map);

        callRegisterNewCoach.enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NotNull Call<User> call, @NotNull Response<User> response) {
                if (response.body() != null && response.body().getSuccess().equals("true")) {
                    Log.d(TAG, "onResponse" + response.body());
                    registerView.registerNewUserMessage();
                } else {
                    registerView.displayError();
                }
            }

            @Override
            public void onFailure(@NotNull Call<User> call, @NotNull Throwable t) {
                Log.e(TAG, "onFailure " + t.getMessage());
            }
        });
    }
}