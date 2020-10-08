package com.azuka.currencyapp.component;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RetrofitInterface {
    @GET("v6/b40f3e2053a169120473ccc1/latest/{currency}")
    Call<JsonObject> getExchangeCurrency(@Path("currency") String currency);
}
