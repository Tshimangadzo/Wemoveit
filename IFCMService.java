package com.example.a800361.shifterapp.Remote;

import com.example.a800361.shifterapp.model.FCMResponse;
import com.example.a800361.shifterapp.model.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface IFCMService {

    @Headers({
             "Content-Type:application/json",
            "Authorization:key =AAAAZwDN9h0:APA91bHb3QNHxqc4Sys5nUlq-aE9VjmKmssDtU0MMN1rUUNy3bFDTosWI4IJYxHV-V_0NF-baxiGDT9-ux6rZ-C2xihloWDS8-5MI9orY8tLxHHuEmAufyaE3d5lO5MeZtZKupTBAjm_"
    })
    @POST("fcm/send")
    Call<FCMResponse> sendMessage(@Body Sender body);



}
