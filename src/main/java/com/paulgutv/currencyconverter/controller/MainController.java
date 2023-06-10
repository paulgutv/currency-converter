package com.paulgutv.currencyconverter.controller;

import com.paulgutv.currencyconverter.model.InputBox;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class MainController implements Initializable {

    @FXML
    public VBox container;
    @FXML
    public static VBox mainContainer;
    private static final VBox subContainer = new VBox();
    private static List<InputBox> inputBoxes;
    private int counter = 0;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        container.getChildren().add(subContainer);
        inputBoxes = new ArrayList<>();

        //Crear la primera instancia de InputBox con el valor de ComboBox "USD"
        counter++;
        InputBox inputBox1 = new InputBox();
        inputBox1.setId(counter);
        inputBox1.getComboBox().setValue("USD");
        inputBox1.getTextField().setText("1");
        inputBoxes.add(inputBox1);
        subContainer.getChildren().add(inputBox1.getVBox());

        // Crear la segunda instancia de InputBox con el valor de ComboBox "PEN"
        counter++;
        InputBox inputBox2 = new InputBox();
        inputBox2.setId(counter);
        inputBox2.getComboBox().setValue("PEN");
        inputBoxes.add(inputBox2);
        subContainer.getChildren().add(inputBox2.getVBox());

        // Realizar la conversión inicial
        inputBox1.setInitialConversion(true);
        inputBox1.startConversion();
        inputBox1.setInitialConversion(false);
        InputBox.setBaseIndex(1);
    }

    @FXML
    protected void addButton1() {
        InputBox newComponent = new InputBox();
        counter++;
        newComponent.setId(counter);
        newComponent.getVBox().setId(String.valueOf(counter));
        inputBoxes.add(newComponent);
        subContainer.getChildren().add(newComponent.getVBox());
    }

    public static List<InputBox> getInputBoxes() {
        return inputBoxes;
    }

    public static VBox getVBox() {
        return subContainer;
    }
}
