package com.gnadenheimer.ddjjinformativa;

import java.io.InputStream;
import java.util.Properties;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App
        extends Application {

    public static Stage mainStage;

    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/FXMLDocument.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);

        InputStream resourceAsStream = this.getClass().getResourceAsStream("/version.properties");
        Properties prop = new Properties();
        prop.load(resourceAsStream);
        mainStage = stage;
        stage.setTitle("NIS " + prop.getProperty("project.version") + "." + prop.getProperty("project.build"));
        stage.show();

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        launch(args);
    }
}
