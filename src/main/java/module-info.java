module com.paulgutv.currencyconverter {
    requires javafx.controls;
    requires javafx.fxml;
    requires okhttp3;
    requires org.json;

    exports com.paulgutv.currencyconverter.controller;
    opens com.paulgutv.currencyconverter.controller to javafx.fxml;
    exports com.paulgutv.currencyconverter.model;
    opens com.paulgutv.currencyconverter.model to javafx.fxml;


    opens com.paulgutv.currencyconverter to javafx.fxml;
    exports com.paulgutv.currencyconverter;
}