package com.paulgutv.currencyconverter.model;

import com.paulgutv.currencyconverter.App;
import javafx.application.Platform;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class Update {
    public static void checkLastVersion() {

        try {
            URL url = new URL("https://github.com/paulgutv/currency-converter/releases/latest");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setInstanceFollowRedirects(false);
            int responseCode = connection.getResponseCode();

            if (responseCode >= 300 && responseCode <= 399) {
                String redirectUrl = connection.getHeaderField("Location");
                System.out.println(redirectUrl);
                String afterTag = redirectUrl.substring(redirectUrl.lastIndexOf("/") + 1);
                System.out.println(afterTag);
                if (!afterTag.equals(App.version)){
                    callingLauncher();
                }

            } else {
                System.out.println("No se produjo ninguna redirección");
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void callingLauncher() {
        try {
            String rutaSegundoJar = System.getProperty("user.dir") + File.separator + "Updater.jar";
            String directorioActual = System.getProperty("user.dir");
            String comando = "java -jar " + rutaSegundoJar;

            ProcessBuilder pb = new ProcessBuilder(comando.split(" "));
            pb.directory(new File(directorioActual));
            pb.start();
            Platform.exit();
            System.exit(0);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
