package com.azuka.currencyapp.view;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.azuka.currencyapp.R;

import java.text.BreakIterator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static BreakIterator data;
    List<String> keysList;
    Spinner toCurrency;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toCurrency = findViewById(R.id.planets_spinner);
        final EditText edtEuroValue = findViewById(R.id.editText4);
        final Button btnConvert = findViewById(R.id.button);
        textView = findViewById(R.id.textView7);

    }
}