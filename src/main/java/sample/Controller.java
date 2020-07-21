package sample;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import logic.Conditions;
import logic.Converter;

public class Controller {


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField catalogField;

    @FXML
    private Button observeButton;

    @FXML
    private Button runConvertingButton;

    @FXML
    private volatile Label conditionField;

    @FXML
    private Button closeButton;

    @FXML
    void initialize() {

        observeButton.setOnAction(event -> {
            catalogField.clear();
            //catalogField.setText("ZZZ");
            Scene scene = ((Node)event.getTarget()).getScene();
            File dir = new DirectoryChooser().showDialog(scene.getWindow());
            if (dir != null) {
                catalogField.setText(dir.getAbsolutePath());
            } else {
                catalogField.setText(null);
            }

        });

        runConvertingButton.setOnAction(event -> {
            conditionField.setText("");
            String s = catalogField.getText();
            Converter.pathToDirectoryJPG = s +"/JPG";

                Thread thread1 = new Thread() {
                    @Override
                    public void run(){

                        try {
                            new Converter().run(s);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                };
                thread1.start();
            conditionField.setText("Преобразование . . .");
        });

//        closeButton.setOnAction(event -> {
//            Conditions.isClosing = true;
//
//
//        });

    }

    @FXML
    public void handleCloseButtonAction(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        Conditions.isClosing = true;
        stage.close();
    }


}
