package com.filethings.ui;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import com.filethings.service.CSVFileService;
import com.filethings.service.AbstractFileService;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

public class MainWindow {

    private AbstractFileService fileService = new CSVFileService(new FileChooser());

    @FXML
    private AnchorPane contentPane;

    @FXML
    private Label numWordsLabel;

    @FXML
    private Label numLettersLabel;

    /**
     * Opens a file chooser dialog and displays the selected file.
     * 
     * @throws IOException if an error occurs while reading the file
     */
    @FXML
    private void openFile() throws IOException {
        File file = fileService.selectFile();
        if (file == null) {
            return;
        }
        Parent view = fileService.viewFile(file);
        contentPane.getChildren().setAll(view);
        AnchorPane.setTopAnchor(view, 0.0);
        AnchorPane.setBottomAnchor(view, 0.0);
        AnchorPane.setLeftAnchor(view, 0.0);
        AnchorPane.setRightAnchor(view, 0.0);

        fileService.analyzeFile(file, numWordsLabel, numLettersLabel);
    }

}
