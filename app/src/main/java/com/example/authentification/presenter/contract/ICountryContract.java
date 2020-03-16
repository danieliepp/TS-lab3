package com.example.authentification.presenter.contract;

import com.example.authentification.data.model.Country;

import java.util.List;

public interface ICountryContract {

    interface Presenter{
        void getCountries();
    }

    interface View {
        void displayCountriesSuccess(List<Country> countryList);
        void displayCountriesError();
    }
}
