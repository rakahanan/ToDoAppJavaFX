package app.controller;

import app.data.NoteHelper;
import app.model.Note;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
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
    @FXML
    private CheckBox ckbxDone;

    private NoteHelper noteHelper = new NoteHelper();

    private int noteId = 0;

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

    public void setNote(Note note) {
        if (note != null) {
            noteId = note.getId();
            fieldTitle.setText(note.getTitle());
            areaDesc.setText(note.getDescription());
            if (note.getDone() == 1) ckbxDone.setSelected(true);
        }
    }

    private void addNote() {
        if (!fieldTitle.getText().equals("") && !areaDesc.getText().equals("")) {
            int done = ckbxDone.isSelected() ? 1 : 0;
            Note note = new Note(noteId, fieldTitle.getText(), areaDesc.getText(), done);

            if (noteId == 0) noteHelper.saveNote(note);
            else noteHelper.updateNote(note);

            Stage stage = (Stage) btnSave.getScene().getWindow();
            stage.close();
        } else {
            lblUncomplete.setVisible(true);
        }
    }
}
