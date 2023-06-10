package com.paulgutv.currencyconverter.model;

import com.paulgutv.currencyconverter.controller.MainController;
import javafx.application.Platform;
import javafx.scene.paint.Color;

public class Errors {
    public static void noConexion() {
        Platform.runLater(() -> {
        int num = InputBox.getInputId();
        for (InputBox inputbox: MainController.getInputBoxes()) {
            if(num == inputbox.getId()) {
                inputbox.getLabel2().setTextFill(Color.RED);
                inputbox.setLabel2(" | Modo sin conexión ⚠");
            }
        }
    });
    }
}
