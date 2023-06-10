package com.paulgutv.currencyconverter.model;

import com.paulgutv.currencyconverter.controller.MainController;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class InputBox {
    private final TextField textField;
    private final ComboBox<String> comboBox;
    private final VBox vBox;
    private final Label label1;
    private final Label label2;
    private final List<InputBox> inputBoxes = MainController.getInputBoxes();
    static int inputId;
    static boolean inputIdInit = false;

    private Timer timer;
    private TimerTask timerTask;
    private int id;
    private static int counter = 0;
    private boolean initialConversion = false;
    private static int baseIndex;
    public String baseComboBox = "USD";
    public String baseTextField = "0";

    public InputBox() {
        textField = new TextField();
        comboBox = new ComboBox<>();
        vBox = new VBox();
        HBox hBox = new HBox();
        HBox hBoxLabels = new HBox();
        label1 = new Label();
        label2 = new Label();

        counter++;
        setId(counter);

        if (counter > 2) {
            Button deleteButton = new Button();
            hBox.getChildren().add(deleteButton);

            deleteButton.setPrefHeight(25.0);
            deleteButton.setPrefWidth(25.0);
            deleteButton.setStyle("-fx-background-color: purple; -fx-background-radius: 25;");
            deleteButton.setTextFill(Color.WHITE);
            deleteButton.setText("-");
            deleteButton.setCursor(Cursor.HAND);
            deleteButton.setOnAction(e -> handleDeleteButton());
            HBox.setMargin(deleteButton, new Insets(0.0, 10.0, 0.0, 0.0));
            HBox.setMargin(comboBox, new Insets(0.0, 35.0, 0.0, 0.0));
        }


        // Agregar los elementos al HBox
        hBox.getChildren().add(textField);
        hBox.getChildren().add(comboBox);

        // Agregar el HBox al VBox
        vBox.getChildren().add(hBox);
        vBox.getChildren().add(hBoxLabels);

        // Actualizar el texto del Label
        label1.setFont(Font.font(15));
        label1.setTextFill(Color.PURPLE);
        label1.setWrapText(true);
        label1.setPadding(new Insets(0.0, 0.0, 0.0, 55.0));
        label1.setText(Utils.addSymbol(baseComboBox) + "0");
        label2.setFont(Font.font(15));
        label2.setTextFill(Color.DIMGRAY);
        label2.setWrapText(true);

        // Agregar el Label al VBox
        hBoxLabels.getChildren().add(label1);
        hBoxLabels.getChildren().add(label2);

        hBox.setAlignment(Pos.CENTER);
        hBox.setPadding(new Insets(20.0, 0.0, 0.0, 0.0));

        textField.setFocusTraversable(false);
        textField.setMinHeight(50.0);
        textField.setOnKeyTyped(e -> startConversion());
        textField.setPrefHeight(54.0);
        textField.setPrefWidth(272.0);
        textField.setStyle("-fx-focus-traversable: false;");
        textField.setText("");
        textField.setFont(Font.font(27));

        comboBox.setId("currencyButton1");
        comboBox.setOnAction(e -> startConversion());
        comboBox.setPrefHeight(50.0);
        comboBox.setPrefWidth(156.0);
        comboBox.setStyle("-fx-font-size: 18; -fx-background-color: #ffffff; -fx-border-color: gray;");
        comboBox.setVisibleRowCount(5);
        comboBox.setValue("USD");

        List<String> currencies = Utils.addCurrencies();

        comboBox.setCellFactory(param -> new ListCell<>() {
            private final ImageView imageView = new ImageView();

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(item);
                    String countryCode = Utils.obtenerCodigoPais(item);
                    String imagePath = "/img/flags/" + countryCode + ".png";
                    Image flagImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream(imagePath)));
                    imageView.setImage(flagImage);
                    setGraphic(imageView);
                }
            }
        });

        comboBox.setButtonCell(new ListCell<>() {
            private final ImageView imageView = new ImageView();

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(item);
                    String countryCode = Utils.obtenerCodigoPais(item);
                    String imagePath = "/img/flags/" + countryCode + ".png";
                    Image flagImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream(imagePath)));
                    imageView.setImage(flagImage);
                    setGraphic(imageView);
                }
            }
        });
        comboBox.getItems().addAll(currencies);

        TextFormatter<String> amountFormatter = new TextFormatter<>(change -> {
            String text = change.getControlNewText();
            if (text.matches("^\\d*|^\\d+\\.\\d*")) {
                return change;
            }
            return null;
        });
        textField.setTextFormatter(amountFormatter);


        //listeners
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            baseIndex = getId();
            baseTextField = textField.getText();
            addThousandSeparator();
            inputIdInit = false;
        });

        comboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            baseComboBox = comboBox.getValue();
            baseIndex = getId();
            addThousandSeparator();
        });
    }

    private void addThousandSeparator() {
        if (!textField.getText().equals("")) {
            String numberString = textField.getText().replace(",", ""); // Eliminar las comas
            double number2 = Double.parseDouble(numberString); // Convertir la cadena en un número
            DecimalFormat decimalFormat = new DecimalFormat("#,###.###");
            String formattedNumber = decimalFormat.format(number2);
            label1.setText(Utils.addSymbol(baseComboBox) + formattedNumber);
        }
    }


    private void handleDeleteButton() {
        String activeInput = String.valueOf(this.getId());
        for (Node node : MainController.getVBox().getChildren()) {
            if (activeInput.equals(node.getId())) {
                MainController.getVBox().getChildren().remove(node);
                break;
            }
        }
    }

    public void startConversion() {
        Platform.runLater(() -> {
            textField.requestFocus();
            textField.deselect();
            textField.positionCaret(textField.getText().length());
        });

        if (timerTask != null) {
            timerTask.cancel();
        }
        if (timer != null) {
            timer.cancel();
            timer.purge();
        }

        timer = new Timer();
        timerTask = new TimerTask() {

            public void run() {
                if (!baseTextField.equals("0") && !baseTextField.equals("") && !baseTextField.endsWith(".")) {
                    addCorrectConversion();
                } else {
                    addWrongConversion();
                }
            }
        };
        if (initialConversion) {
            timer.schedule(timerTask, 0);
        } else {
            timer.schedule(timerTask, 400);
        }
    }

    private void addCorrectConversion() {
        inputId = baseIndex;
        ExecutorService executorService = Executors.newFixedThreadPool(inputBoxes.size());
        for (InputBox inputBox : inputBoxes) {
            if (inputId != inputBox.getId()) {
                ConversionTask conversionTask = new ConversionTask(inputBox, baseTextField, baseComboBox);
                executorService.submit(conversionTask);
            } else {
                Platform.runLater(() -> inputBox.setLabel2(""));
            }
        }
        executorService.shutdown();
    }

    private void addWrongConversion() {
        inputId = baseIndex;
        for (InputBox inputBox : inputBoxes) {
            if (inputId != inputBox.getId()) {
                Platform.runLater(() -> {
                    inputBox.setTextField("");
                    inputBox.setLabel2("");
                    inputBox.setLabel1(Utils.addSymbol(inputBox.comboBox.getValue()) + "0");
                });
            } else {
                Platform.runLater(() -> {
                    inputBox.setLabel1(Utils.addSymbol(inputBox.comboBox.getValue()) + "0");
                    inputBox.setLabel2("");
                });
            }
        }
    }

    public TextField getTextField() {
        return textField;
    }

    public void setTextField(String textField) {
        this.textField.setText(textField);
    }

    public ComboBox<String> getComboBox() {
        return comboBox;
    }

    public VBox getVBox() {
        return vBox;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return this.id;
    }

    public static void setBaseIndex(int baseIndex) {
        InputBox.baseIndex = baseIndex;
    }

    public void setLabel1(String text) {
        this.label1.setText(text);
    }

    public void setLabel2(String text) {
        this.label2.setText(text);
    }

    public Label getLabel2() {
        return label2;
    }


    public static int getInputId() {
        return inputId;
    }


    public void setInitialConversion(boolean initialConversion) {
        this.initialConversion = initialConversion;
    }

}
