package com.filethings.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import com.filethings.commons.FileType;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;

/**
 * CSVFileService is a service class that provides functionality for viewing CSV files.
 */
public class CSVFileService extends AbstractFileService {

    private static final Logger logger = Logger.getLogger(CSVFileService.class.getName());

    public CSVFileService(FileChooser fileChooser) throws IllegalArgumentException {
        super(FileType.CSV, fileChooser);
    }

    /**
     * Opens a CSV file and returns a TableView of the file.
     * 
     * @param file the file to view
     * @return a TableView of the file
     * @throws IOException if an error occurs while reading the file
     * @throws IllegalArgumentException if the file is not a CSV file
     */
    @Override
    public Parent viewFile(File file) throws IOException, IllegalArgumentException {
        if (!file.getName().endsWith(".csv")) {
            throw new IllegalArgumentException("File is not a CSV file");
        }

        TableView<List<String>> csvTable = new TableView<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String headerLine = reader.readLine();
            if (headerLine == null) {
                return null;
            }

            List<String> headers = Arrays.asList(headerLine.split(","));
            for (int i = 0; i < headers.size(); i++) {
                TableColumn<List<String>, String> column = new TableColumn<>(headers.get(i));
                final int colIndex = i;
                column.setCellValueFactory(data -> {
                    String cellValue = data.getValue().size() > colIndex ? data.getValue().get(colIndex).trim() : " ";
                    return new SimpleStringProperty(cellValue);
                });
                csvTable.getColumns().add(column);
            }

            reader.lines()
                .map(line -> Arrays.asList(line.split(",")))
                .forEach(lineArray -> csvTable.getItems().add(lineArray));

            return csvTable;
        } catch (IOException e) {
            logger.severe("Error reading file: " + e.getMessage());
            throw new IOException("Error reading file: " + e.getMessage());
        }
    }
    
}
