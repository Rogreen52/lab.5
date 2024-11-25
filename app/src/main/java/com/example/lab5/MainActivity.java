package com.example.lab5;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText editTextFilter;
    private Button buttonFetch;
    private ListView listViewCurrencies;
    private ArrayAdapter<String> adapter;
    private DataLoader dataLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextFilter = findViewById(R.id.editTextFilter);
        buttonFetch = findViewById(R.id.buttonFetch);
        listViewCurrencies = findViewById(R.id.listViewCurrencies);

        dataLoader = new DataLoader(this);

        buttonFetch.setOnClickListener(v -> {
            String url = "http://www.floatrates.com/daily/usd.xml";
            dataLoader.fetchCurrencyRates(url);
        });

        editTextFilter.addTextChangedListener(new android.text.TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterCurrencies(s.toString());
            }

            @Override
            public void afterTextChanged(android.text.Editable s) {}
        });
    }

    public void displayCurrencies(List<String> currencies) {
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, currencies);
        listViewCurrencies.setAdapter(adapter);
    }

    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void filterCurrencies(String query) {
        if (adapter != null) {
            adapter.getFilter().filter(query);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dataLoader.shutdown();
    }
}
