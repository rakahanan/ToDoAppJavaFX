package app.controller;

import app.data.NoteHelper;
import app.model.Note;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class Form implements Initializable {

    @FXML
    private TextField fieldTitle;
    @FXML
    private TextArea areaDesc;
    @FXML
    private Button btnSave;
    @FXML
    private Label lblUncomplete;

    private NoteHelper noteHelper = new NoteHelper();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initialize();
    }

    @FXML
    void initialize() {
        setListener();
    }

    private void setListener() {
        btnSave.setOnAction(actionEvent -> addNote());

        fieldTitle.textProperty().addListener((observableValue, s, t1) -> {
            lblUncomplete.setVisible(false);
        });

        areaDesc.textProperty().addListener((observableValue, s, t1) -> {
            lblUncomplete.setVisible(false);
        });
    }

    void setTitle(String jancok) {
        fieldTitle.setText(jancok);
    }

    private void addNote() {
        if (!fieldTitle.getText().equals("") && !areaDesc.getText().equals("")) {
            Note note = new Note(0, fieldTitle.getText(), areaDesc.getText(), 0);
            noteHelper.saveNote(note);
            Stage stage = (Stage) btnSave.getScene().getWindow();
            stage.close();
        } else {
            lblUncomplete.setVisible(true);
        }
    }
}
