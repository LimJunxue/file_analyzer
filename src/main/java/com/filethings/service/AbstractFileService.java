package com.filethings.service;

import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import com.filethings.model.FileType;

/**
 * AbstractFileService is an abstract class that provides a template for file services.
 */
public abstract class AbstractFileService {
    private static final Logger logger = Logger.getLogger(AbstractFileService.class.getName());
    
    private final FileChooser fileChooser;

    /**
     * Constructs a new AbstractFileService with the given FileType and FileChooser.
     * 
     * @param fileType the FileType of the file
     * @param fileChooser the FileChooser to use
     */
    public AbstractFileService(FileType fileType, FileChooser fileChooser) {
        File repoPath = new File(System.getProperty("user.dir") + "/examples");
        fileChooser.setInitialDirectory(repoPath);
        fileChooser.setTitle("Open " + fileType + " File");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter(fileType + " Files", "*." + fileType)
        );
        this.fileChooser = fileChooser;
    }

    /**
     * Opens a file chooser dialog and returns the selected file.
     * 
     * @return the selected File or null if no file was selected
     */
    public File selectFile() {
        Stage stage = new Stage();
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile == null) {
            logger.info("No file selected");
            return null;
        }
        logger.info("File selected: " + selectedFile.getAbsolutePath());
        return selectedFile;
    }

    /**
     * Opens a file and returns a view of the file catered to its type.
     * 
     * @param file the file to view
     * @return a Parent view of the file
     */
    public abstract Parent viewFile(File file) throws IOException;

    /**
     * Analyzes a file and updates the given labels with the results.
     * 
     * @param file the file to analyze
     * @param numWordsLabel the label to update with the number of words
     * @param numLettersLabel the label to update with the number of letters
     */
    public abstract void analyzeFile(File file, Label numWordsLabel, Label numLettersLabel) throws IOException;
}
