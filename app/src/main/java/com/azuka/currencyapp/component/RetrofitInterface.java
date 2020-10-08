package com.azuka.currencyapp.component;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitInterface {
    @GET("latest")
    Call<JsonObject> getExchangeCurrency(@Query("?base=USD") String currency);

    @GET("history")
    Call<JsonObject> getLatestChart(@Query("?start_at=2020-10-08&end_at=2020-10-01") String days);
}
