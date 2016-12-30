package com.example.ahmaadyunus.task4.API;

import com.example.ahmaadyunus.task4.model.BillModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by ahmaadyunus on 30/12/16.
 */

public interface BillApi {

    @POST("https://private-80e9a-android23.apiary-mock.com/users")

    Call<BillModel> postBill(@Body BillModel user);


}