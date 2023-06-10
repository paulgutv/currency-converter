package com.paulgutv.currencyconverter.model;

import okhttp3.*;
import org.json.JSONObject;

import java.io.IOException;

public class Conversion {
    static String currencyInput;
    static String currencyOutput;
    private String exchangeRate;

    public String convert(String amount, String currencyInput, String currencyOutput) {
        Conversion.currencyInput = currencyInput;
        Conversion.currencyOutput = currencyOutput;

        OkHttpClient client = new OkHttpClient().newBuilder().build();
        String responseStr;
        try {
            Request request = new Request.Builder()
                    .url(String.format("https://api.exchangerate.host/convert?from=%s&to=%s&amount=%s", currencyInput, currencyOutput, amount))
                    .build();
            Response response = client.newCall(request).execute();
            assert response.body() != null;
            responseStr = response.body().string();

            JSONObject json = new JSONObject(responseStr);
            exchangeRate = String.valueOf(json.getJSONObject("info").getDouble("rate"));
            return String.format("%.2f",json.getDouble("result"));
        } catch (IOException e) {
            System.out.println("no hay internet");
            Errors.noConexion();
            return "offline";
        }
    }

    public String convertOffline(String exchangeRates, String amounts) {
        return String.format("%.2f",Double.parseDouble(exchangeRates) * Double.parseDouble(amounts));
    }

    public String getExchangeRate() {
        return exchangeRate;
    }


}
