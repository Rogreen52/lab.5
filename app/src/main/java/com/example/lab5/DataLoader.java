package com.example.lab5;

import android.os.Handler;
import android.os.Looper;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DataLoader {
    private MainActivity activity;
    private ExecutorService executorService;
    private Handler mainHandler;

    public DataLoader(MainActivity activity) {
        this.activity = activity;
        this.executorService = Executors.newSingleThreadExecutor();
        this.mainHandler = new Handler(Looper.getMainLooper());
    }

    public void fetchCurrencyRates(String urlString) {
        executorService.execute(() -> {
            try {
                URL url = new URL(urlString);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(15000);
                connection.setReadTimeout(15000);
                connection.connect();

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStream inputStream = connection.getInputStream();
                    Parser parser = new Parser();
                    List<String> currencies = parser.parseXML(inputStream);
                    mainHandler.post(() -> activity.displayCurrencies(currencies));
                } else {
                    mainHandler.post(() -> activity.showError("Failed: Response Code " + responseCode));
                }
            } catch (Exception e) {
                e.printStackTrace();
                mainHandler.post(() -> activity.showError("Error: " + e.getMessage()));
            }

        });
    }

    public void shutdown() {
        executorService.shutdown();
    }
}
