package com.filethings;

import javafx.application.Application;
import javafx.stage.Stage;

import java.util.logging.Logger;

import com.filethings.ui.Ui;
import com.filethings.ui.UiManager;

/**
 * JavaFX App
 */
public class App extends Application {

    private static final Logger logger = Logger.getLogger(App.class.getName());

    protected Ui ui;

    @Override
    public void start(Stage stage) {
        logger.info("------- Starting file analyzer ------");

        ui = new UiManager();
        ui.start(stage);
    }

    public static void main(String[] args) {
        launch();
    }

}