package com.azuka.currencyapp.view;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.azuka.currencyapp.R;
import com.azuka.currencyapp.component.RetrofitBuilder;
import com.azuka.currencyapp.component.RetrofitInterface;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.JsonObject;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    Button button;
    EditText currencyToBeConverted;
    EditText currencyConverted;
    Spinner convertToDropdown;
    Spinner convertFromDropdown;
    //firebase
    DatabaseReference RootRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InitializeVariables();
        CurrencyChange();
    }

    private void InitializeVariables() {

        //Initialization
        currencyConverted = findViewById(R.id.currency_converted);
        currencyToBeConverted = findViewById(R.id.currency_to_be_converted);
        convertToDropdown = findViewById(R.id.convert_to);
        convertFromDropdown = findViewById(R.id.convert_from);
        button = findViewById(R.id.button);

        //Adding Functionality
        String[] dropDownList = {"USD", "SGD", "EUR", "MYR", "CNY", "GBP", "AUD"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, dropDownList);
        convertToDropdown.setAdapter(adapter);
        convertFromDropdown.setAdapter(adapter);

        //Firebase Fucntion
        //create user to db
        RootRef = FirebaseDatabase.getInstance().getReference().child("Currency");
    }

    public void CurrencyChange() {
        button.setOnClickListener(v -> {
            RetrofitInterface retrofitInterface = RetrofitBuilder.getRetrofitInstance().create(RetrofitInterface.class);
            Call<JsonObject> call = retrofitInterface.getExchangeCurrency(convertFromDropdown.getSelectedItem().toString());
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NotNull Call<JsonObject> call, @NotNull Response<JsonObject> response) {
                    JsonObject res = response.body();
                    assert res != null;
                    JsonObject rates = res.getAsJsonObject("conversion_rates");
                    double currency = Double.parseDouble(currencyToBeConverted.getText().toString());
                    double multiplier = Double.parseDouble(rates.get(convertToDropdown.getSelectedItem().toString()).toString());
                    double result = currency * multiplier;
                    DecimalFormat precision = new DecimalFormat("0.00");
                    currencyConverted.setText(precision.format(result));
                    SaveToDatabase(currency, result);
                }
                @Override
                public void onFailure(@NotNull Call<JsonObject> call, @NotNull Throwable t) {
                }
            });
        });
    }

    private void SaveToDatabase(double currency, double result) {

        DecimalFormat precision = new DecimalFormat("0.00");
        String from = convertFromDropdown.getSelectedItem().toString();
        String to = convertToDropdown.getSelectedItem().toString();

        HashMap<String, Object> CurrencyMap = new HashMap<>();
        CurrencyMap.put("from", from);
        CurrencyMap.put("results", precision.format(result));
        CurrencyMap.put("to", to);
        CurrencyMap.put("values", precision.format(currency));

        RootRef.child("Results").
                updateChildren(CurrencyMap)
                .addOnCompleteListener(task -> Toast.makeText(MainActivity.this, "Value Added", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(MainActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());

    }
}