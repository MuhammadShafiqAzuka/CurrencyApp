package com.azuka.currencyapp.component;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitInterface {
    @GET("v4/latest/{currency}")
    Call<JsonObject> getExchangeCurrency(@Query("currency") String currency);
}
