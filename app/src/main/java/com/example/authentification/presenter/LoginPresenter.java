package com.example.authentification.presenter;

import android.util.Log;
import android.widget.Toast;

import com.example.authentification.activities.LoginActivity;
import com.example.authentification.data.RetrofitClient;
import com.example.authentification.data.model.User;
import com.example.authentification.presenter.contract.ILoginContract;
import com.example.authentification.presenter.contract.IRegisterContract;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPresenter implements ILoginContract.Presenter {

    private static final String TAG = LoginPresenter.class.getName();
    private ILoginContract.View loginView;

    public static String token;
    public static String id;
    public static String name;
    public static String club;
    public static String country;
    public static Boolean success;

    public LoginPresenter(ILoginContract.View loginView) {
        this.loginView = loginView;
    }

    @Override
    public void doLogin(HashMap<String, String> map) {
        Call<User> call = RetrofitClient.getRetrofitInstance().loginUser(map);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NotNull Call<User> call, @NotNull Response<User> response) {
                if(response.body() != null && response.body().getSuccess().equals("true")) {
                        Log.d(TAG, response.toString());
                        User user = response.body();
                            token = user.getToken();
                            id = user.getId();
                            name = user.getFirstName() + " " + user.getLastName();
                            club = user.getClub();
                            country = user.getCountry();
                            loginView.onLoginSuccess();
                        }
                        else {
                            loginView.onLoginFailed();
                        }
                }

            @Override
            public void onFailure(@NotNull Call<User> call, @NotNull Throwable t) {
                Log.e(TAG, "onFailure " + t.getMessage());
            }
        });
    }
    public static String getName() { return name; }
    public static String getClub() { return club; }
    public static String getCountry() { return country; }

}
