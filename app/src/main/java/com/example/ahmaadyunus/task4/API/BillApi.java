package com.example.ahmaadyunus.task4.API;

import com.example.ahmaadyunus.task4.model.BillModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by ahmaadyunus on 30/12/16.
 */

public interface BillApi {

    @POST("https://private-574b4-synchronize.apiary-mock.com/bill")

    Call<BillModel> postBill(@Body BillModel user);


}