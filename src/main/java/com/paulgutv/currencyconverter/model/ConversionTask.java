package com.paulgutv.currencyconverter.model;

import javafx.application.Platform;
import javafx.scene.paint.Color;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

public class ConversionTask implements Runnable {
    private final InputBox inputBox;
    private final String baseTextField;
    private final String baseComboBox;
    private boolean isFound;

    public static Properties properties = new Properties();

    public ConversionTask(InputBox inputBox, String baseTextField, String baseComboBox) {
        this.inputBox = inputBox;
        this.baseTextField = baseTextField;
        this.baseComboBox = baseComboBox;


        //Importar propiedades guardadas
        try {
            FileInputStream fis = new FileInputStream("app.properties");
            properties.load(fis);
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        Conversion conversion = new Conversion();
        String result = conversion.convert(baseTextField, baseComboBox, inputBox.getComboBox().getValue());
        String exchangeRate = conversion.getExchangeRate();

        //Agregar etiqueta con tipo de cambio
        if (!result.equals("offline")) {
            //Guardar propiedades
            properties.setProperty(baseComboBox + "->" +inputBox.getComboBox().getValue(), exchangeRate + "," + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
            try (OutputStream outputStream = new FileOutputStream("app.properties")) {
                properties.store(outputStream, "Application Properties");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            Platform.runLater(() -> {
                    inputBox.setTextField(result);
                    inputBox.setLabel2(" | Tipo de cambio: " + baseComboBox + " -> " + inputBox.getComboBox().getValue() + " : " + exchangeRate);
                }
            );
        } else {
            Platform.runLater(() -> {
                properties.forEach((key, value) -> {
                    if (key.toString().startsWith(baseComboBox) && key.toString().endsWith(inputBox.getComboBox().getValue())) {
                        int commaIndex = value.toString().indexOf(",");
                        String exchangeRateOffline = value.toString().substring(0, commaIndex);
                        String date = value.toString().substring(commaIndex + 1);
                        String resultOffline = conversion.convertOffline(exchangeRateOffline, baseTextField);

                        inputBox.setTextField(resultOffline);
                        inputBox.getLabel2().setTextFill(Color.RED);
                        inputBox.setLabel2(" | Tipo de cambio: " + exchangeRateOffline + " (" + date + " ) ⚠");
                        isFound = true;
                    }});
                if (!isFound) {
                        inputBox.setTextField("");
                        inputBox.getLabel2().setTextFill(Color.RED);
                        inputBox.setLabel2(" | No hay datos guardados ⚠");
                }
            });
        }
    }
}
