package com.filethings.ui;

import java.io.IOException;
import java.util.logging.Logger;

import com.filethings.App;
import com.filethings.commons.StageName;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class UiManager implements Ui {

    public static final String FMXL_FILE_FOLDER = "/view/";

    private static final Logger logger = Logger.getLogger(UiManager.class.getName());
    
    private static Scene scene;

    @Override
    public void start(Stage mainStage) {
        try {
            scene = new Scene(UiManager.loadFXML(StageName.MainWindow.toString()), 640, 480);
            mainStage.setScene(scene);
            mainStage.show();
        } catch (IOException e) {
            logger.severe("Error loading main window: " + e.getMessage());
        }
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(FMXL_FILE_FOLDER + fxml + ".fxml"));
        return fxmlLoader.load();
    }

}
