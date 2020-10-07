package com.azuka.currencyapp.component;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RetrofitInterface {
    @GET("/latest?base=USD")
    Call<JsonObject> getExchangeCurrency(@Path("currency") String currency);
}
