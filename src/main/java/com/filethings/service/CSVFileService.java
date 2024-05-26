package com.filethings.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.filethings.model.CSVCellData;
import com.filethings.model.FileType;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
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

        TableView<List<CSVCellData>> csvTable = new TableView<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String headerLine = reader.readLine();
            if (headerLine == null) {
                return null;
            }

            List<String> headers = Arrays.asList(headerLine.split(","));
            setColumns(csvTable, headers);

            reader.lines()
                .map(line -> Arrays.stream(line.split(","))
                    .map(value -> new CSVCellData(value))
                    .collect(Collectors.toList()))
                .forEach(lineArray ->  csvTable.getItems().add(lineArray));

            return csvTable;
        } catch (IOException e) {
            logger.severe("Error reading file: " + e.getMessage());
            throw new IOException("Error reading file: " + e.getMessage());
        }
    }

    private void setColumns(TableView<List<CSVCellData>> csvTable, List<String> headers) {
        for (int i = 0; i < headers.size(); i++) {
            TableColumn<List<CSVCellData>, CSVCellData> column = new TableColumn<>(headers.get(i));
            final int colIndex = i;
            column.setCellValueFactory(data -> {
                CSVCellData cellValue = data.getValue().size() > colIndex ? data.getValue().get(colIndex) : new CSVCellData("");
                return new SimpleObjectProperty<>(cellValue);
            });
            column.setCellFactory(tc -> {
                TableCell<List<CSVCellData>, CSVCellData> cell = new TableCell<>();
                Tooltip tooltip = new Tooltip();
                cell.setTooltip(tooltip);
                cell.itemProperty().addListener((obs, oldItem, newItem) -> {
                    if (newItem != null) {
                        tooltip.setText(newItem.getType().toString());
                        cell.setText(newItem.getValue());
                    } else {
                        tooltip.setText(null);
                        cell.setText(null);
                    }
                });
                return cell;
            });
            csvTable.getColumns().add(column);
        }
    }
    
    /**
     * Analyzes a CSV file and displays the number of words and letters in the file.
     * 
     * @param file the file to analyze
     * @param numWordsLabel the label to display the number of words
     * @param numLettersLabel the label to display the number of letters
     * @throws IOException if an error occurs while reading the file
     */
    @Override
    public void analyzeFile(File file, Label numWordsLabel, Label numLettersLabel) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String headerLine = reader.readLine();
            if (headerLine == null) {
                return;
            }

            int numWords = 0;
            int numLetters = 0;
            String line;
            while ((line = reader.readLine()) != null) {
                String[] words = line.split(",");
                for (String word : words) {
                    numWords++;
                    numLetters += word.length();
                }
            }

            numWordsLabel.setText("Number of Words: " + numWords);
            numLettersLabel.setText("Number of Letters: " + numLetters);
        } catch (IOException e) {
            logger.severe("Error reading file: " + e.getMessage());
            throw new IOException("Error reading file: " + e.getMessage());
        }
    }

}
