package com.filethings.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import com.filethings.model.CSVCellData;

import javafx.scene.Parent;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(ApplicationExtension.class)
public class CSVFileServiceTest {

    private FileChooser csvFileChooser;
    private CSVFileService csvFileService;

    @Start
    private void start(Stage stage) {
        csvFileChooser = new FileChooser();
        csvFileService = new CSVFileService(csvFileChooser);
    }
    
    @Test
    void test_view_non_csv_file() {
        File file = new File(System.getProperty("user.dir") + "/examples/fake.txt");
        assertThrows(IllegalArgumentException.class, () -> csvFileService.viewFile(file));
    }

    @Test
    void test_view_good_csv_file() {
        File file = new File(System.getProperty("user.dir") + "/src/test/resources/test.csv");
        Parent view = assertDoesNotThrow(() -> csvFileService.viewFile(file));
        assertNotNull(view);

        assertEquals(TableView.class, view.getClass());
        TableView<List<CSVCellData>> tableView = (TableView<List<CSVCellData>>) view;
        assertEquals("header1", tableView.getColumns().get(0).getText());
        assertEquals("header2", tableView.getColumns().get(1).getText());

        List<List<CSVCellData>> items = tableView.getItems();
        assertEquals(5, items.size());
        assertEquals("1", items.get(0).get(0).getValue());
        assertEquals("", items.get(0).get(1).getValue());
    }

    @Test
    void test_bad_csv_file() {
        File file = new File(System.getProperty("user.dir") + "/src/test/resources/missing.csv");
        assertThrows(IOException.class, () -> csvFileService.viewFile(file));
    }
        
}
