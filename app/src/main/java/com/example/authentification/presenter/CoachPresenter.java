package com.example.authentification.presenter;

import android.util.Log;

import com.example.authentification.data.RetrofitClient;
import com.example.authentification.data.model.Coach;
import com.example.authentification.presenter.contract.ICoachContract;

import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CoachPresenter implements ICoachContract.Presenter {


    ICoachContract.View view;
    private List<Coach> coachList;

    public CoachPresenter(ICoachContract.View view) {
        this.view = view;
    }

    @Override
    public void getCoaches() {
        Call<List<Coach>> call = RetrofitClient.getRetrofitInstance().getCoaches();
        call.enqueue(new Callback<List<Coach>>() {
            @Override
            public void onResponse(@NotNull Call<List<Coach>> call, @NotNull Response<List<Coach>> response) {
                if (response.body() != null) {
                    coachList = response.body();
                    view.displayCoachesSuccess(coachList);
                } else {
                    view.displayCoachesSuccess(Collections.emptyList());
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<Coach>> call, @NotNull Throwable t) {
                Log.d("myTag", Objects.requireNonNull(t.getMessage()));
                view.displayCoachesError();
            }
        });
    }
}
