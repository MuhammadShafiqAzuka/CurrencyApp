package com.azuka.currencyapp.view;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.azuka.currencyapp.R;
import com.azuka.currencyapp.component.RetrofitBuilder;
import com.azuka.currencyapp.component.RetrofitInterface;
import com.google.gson.JsonObject;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    Button button;
    EditText currencyToBeConverted;
    EditText currencyConverted;
    Spinner convertToDropdown;
    Spinner convertFromDropdown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialization
        currencyConverted = findViewById(R.id.currency_converted);
        currencyToBeConverted = findViewById(R.id.currency_to_be_converted);
        convertToDropdown = findViewById(R.id.convert_to);
        convertFromDropdown = findViewById(R.id.convert_from);
        button = findViewById(R.id.button);

        //Adding Functionality
        String[] dropDownList = {"EUR", "INR", "USD", "NZD"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, dropDownList);
        convertToDropdown.setAdapter(adapter);
        convertFromDropdown.setAdapter(adapter);

        button.setOnClickListener(v -> {
            RetrofitInterface retrofitInterface = RetrofitBuilder.getRetrofitInstance().create(RetrofitInterface.class);
            Call<JsonObject> call = retrofitInterface.getExchangeCurrency(convertFromDropdown.getSelectedItem().toString());
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NotNull Call<JsonObject> call, @NotNull Response<JsonObject> response) {
                    JsonObject res = response.body();
                    assert res != null;
                    JsonObject rates = res.getAsJsonObject("rates");
                    double currency = Double.parseDouble(currencyToBeConverted.getText().toString());
                    double multiplier = Double.parseDouble(rates.get(convertToDropdown.getSelectedItem().toString()).toString());
                    double result = currency * multiplier;
                    currencyConverted.setText(String.valueOf(result));
                }

                @Override
                public void onFailure(@NotNull Call<JsonObject> call, @NotNull Throwable t) {
                }
            });
        });


    }
}