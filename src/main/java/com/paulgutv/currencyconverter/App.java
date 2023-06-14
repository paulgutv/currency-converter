package com.paulgutv.currencyconverter;

import com.paulgutv.currencyconverter.model.Update;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class App extends Application {

    public static final String version = "v1.2";

    @Override
    public void start(Stage stage) throws IOException {
        stage.setResizable(false);
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("CConverter " + version);
        Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/main/main.png")));
        stage.getIcons().add(icon);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() {
        System.exit(0);
    }

    public static void main(String[] args) {

        Update.checkLastVersion();
        launch();
    }
}