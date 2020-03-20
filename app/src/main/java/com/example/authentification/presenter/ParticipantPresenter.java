package com.example.authentification.presenter;

import android.util.Log;

import com.example.authentification.data.RetrofitClient;
import com.example.authentification.data.model.Participant;
import com.example.authentification.presenter.contract.IParticipantContract;

import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ParticipantPresenter implements IParticipantContract.Presenter {

    IParticipantContract.View view;
    private List<Participant> participantList;

    public ParticipantPresenter(IParticipantContract.View view) {
        this.view = view;
    }

    @Override
    public void getParticipants() {
        Call<List<Participant>> call = RetrofitClient.getRetrofitInstance().getParticipants(LoginPresenter.token);
        call.enqueue(new Callback<List<Participant>>() {
            @Override
            public void onResponse(@NotNull Call<List<Participant>> call, @NotNull Response<List<Participant>> response) {
                if (response.body() != null) {
                    participantList = response.body();
                    view.displayParticipants(participantList);
                } else {
                    view.displayParticipants(Collections.emptyList());
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<Participant>> call, @NotNull Throwable t) {
                Log.d("myTag", Objects.requireNonNull(t.getMessage()));
                view.displayError();
            }
        });
    }

    @Override
    public void addParticipant(Participant participant) {
        Call<String> call = RetrofitClient.getRetrofitInstance().addParticipant(LoginPresenter.token, participant);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NotNull Call<String> call, @NotNull Response<String> response) {
                if (response.body() != null) {
                    if(response.body().contains("participant added")){
                        view.addParticipantSuccess();
                    }else {
                        view.addParticipantFailed(response.body());
                    }
                } else {
                    view.addParticipantFailed(response.body());
                }
            }

            @Override
            public void onFailure(@NotNull Call<String> call, @NotNull Throwable t) {
                Log.d("myTag", Objects.requireNonNull(t.getMessage()));
                view.displayError();
            }
        });
    }
}
