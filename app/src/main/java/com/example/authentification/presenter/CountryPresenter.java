package com.example.authentification.presenter;

import android.util.Log;

import com.example.authentification.data.RetrofitClient;
import com.example.authentification.data.model.Country;
import com.example.authentification.presenter.contract.ICountryContract;

import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CountryPresenter implements ICountryContract.Presenter {

    ICountryContract.View view;
    private List<Country> countryList;

    public CountryPresenter(ICountryContract.View view) {
        this.view = view;
    }

    @Override
    public void getCountries() {
        Call<List<Country>> call = RetrofitClient.getRetrofitInstance().getCountries();
        call.enqueue(new Callback<List<Country>>() {
            @Override
            public void onResponse(@NotNull Call<List<Country>> call, @NotNull Response<List<Country>> response) {
                if (response.body() != null) {
                    countryList = response.body();
                    view.displayCountriesSuccess(countryList);
                } else {
                    view.displayCountriesSuccess(Collections.emptyList());
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<Country>> call, @NotNull Throwable t) {
                Log.d("myTag", Objects.requireNonNull(t.getMessage()));
                view.displayCountriesError();
            }
        });
    }
}
