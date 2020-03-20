package com.example.authentification.data;

import com.example.authentification.data.model.Coach;
import com.example.authentification.data.model.Country;
import com.example.authentification.data.model.Participant;
import com.example.authentification.data.model.User;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiInterface {

    @POST("/api/user/register")
    Call<User> registerNewUser(@Body HashMap<String, String> map);

    @POST("/api/user/login")
    Call<User> loginUser(@Body HashMap<String, String> map);

    @GET("/api/user/get_participants")
    Call<List<Participant>> getParticipants(@Header("x-access-token") String token);

    @GET("/api/user/get_countries")
    Call<List<Country>> getCountries();

    @GET("/api/user/get_coaches")
    Call<List<Coach>> getCoaches();

    @POST("/api/user/add_participant")
    Call<String> addParticipant(@Header("x-access-token") String token,
                                @Body Participant participant);
}
