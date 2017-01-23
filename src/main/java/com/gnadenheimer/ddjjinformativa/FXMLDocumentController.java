/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gnadenheimer.ddjjinformativa;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * FXML Controller class
 *
 * @author AdminLocal
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private TextField txtFile;
    @FXML
    private Button cmdFile;
    @FXML
    private ComboBox<?> cboAnoMes;
    @FXML
    private ComboBox<?> cboTipo;
    @FXML
    private Button cmdGenerar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void cmdFile(ActionEvent event) {
        try {
            FileChooser fileChooser = new FileChooser();
            File selectedFile = fileChooser.showOpenDialog(App.mainStage);
            if (selectedFile != null) {
                txtFile.setText(selectedFile.getAbsolutePath());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    XSSFSheet getSheet() {
        try {
            InputStream ExcelFileToRead = new FileInputStream(txtFile.getText());
            XSSFWorkbook wb = new XSSFWorkbook(ExcelFileToRead);

            XSSFWorkbook test = new XSSFWorkbook();

            return wb.getSheetAt(0);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @FXML
    private void cmdGenerar(ActionEvent event) {
        try {

            String cabecera = "1\t";

            XSSFSheet sheet = getSheet();

            String[] sFecha = sheet.getRow(1).getCell(0).getStringCellValue().split(" ");

            System.out.print(sheet.getPhysicalNumberOfRows());

            cabecera += sFecha[1].split("/")[2] + sFecha[1].split("/")[1] + "\t";

            cabecera += "1\t";

            if (sheet.getRow(0).getCell(0).getStringCellValue().contains("VENTAS")) {
                cabecera += "921\t221\t";
            } else {
                cabecera += "911\t211\t";
            }

            cabecera += "3605563\t8\tDOROTEHA GABRIELLE AGANETHA TOEWS HARDER\t\t0\t\t";

            Integer totalRows = sheet.getPhysicalNumberOfRows() - 8 + 1;

            cabecera += totalRows.toString() + "\t";

            Integer montoTotal = ((Double) sheet.getRow(sheet.getPhysicalNumberOfRows()).getCell(9).getNumericCellValue()).intValue();

            cabecera += montoTotal.toString() + "\t";

            cabecera += "2\n";

            String detalle = "";

            SimpleDateFormat sm = new SimpleDateFormat("dd/MM/yyyy");

            for (int i = 7; i < totalRows + 7; i++) {
                XSSFRow row = sheet.getRow(i);
                detalle += "2\t";

                detalle += row.getCell(1).getStringCellValue().split("-")[0] + "\t";
                detalle += row.getCell(1).getStringCellValue().split("-")[1] + "\t";

                detalle += row.getCell(2).getStringCellValue() + "\t";

                detalle += "1\t";

                detalle += row.getCell(3).getStringCellValue() + "\t";

                detalle += sm.format(row.getCell(0).getDateCellValue()) + "\t";

                detalle += ((Integer) ((Double) row.getCell(6).getNumericCellValue()).intValue()).toString() + "\t";
                detalle += ((Integer) ((Double) row.getCell(7).getNumericCellValue()).intValue()).toString() + "\t";
                detalle += "0\t0\t0\t";
                detalle += ((Integer) ((Double) row.getCell(9).getNumericCellValue()).intValue()).toString() + "\t";
                detalle += "1\t0\t";
                detalle += row.getCell(4).getStringCellValue() + "\n";
            }

            cabecera += detalle;

            String home = System.getProperty("user.home");
            File file = new File(home + "/Downloads/DJI" + sFecha[1].split("/")[2] + sFecha[1].split("/")[1] + "_3605563_921.txt");

            try (BufferedWriter writer = Files.newBufferedWriter(file.toPath())) {
                writer.write(cabecera);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
