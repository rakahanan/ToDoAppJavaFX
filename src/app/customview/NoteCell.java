package app.customview;

import app.App;
import app.Const;
import app.controller.Form;
import app.data.NoteHelper;
import app.model.Note;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public final class NoteCell extends ListCell<Note> {
    @FXML
    private CheckBox ckbxStatus;
    @FXML
    private Label lblTitle;
    @FXML
    private Button btnEdit;
    @FXML
    private Button btnDelete;

    private ListView<Note> listView;

    private Stage formStage;

    NoteCell(ListView<Note> listView, Stage formStage) {
        this.listView = listView;
        this.formStage = formStage;
        loadFXML();
    }

    private void loadFXML() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/view/item.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateItem(Note note, boolean empty) {
        super.updateItem(note, empty);
        setEditable(false);
        if (empty || note == null) {
            setText(null);
            setContentDisplay(ContentDisplay.TEXT_ONLY);
        } else {
            ckbxStatus.setSelected(note.getDone() == 1);
            lblTitle.setText(note.getTitle().length() > 50 ? note.getTitle().substring(0, 50) : note.getTitle());

            btnEdit.setOnAction(actionEvent -> {
                try {
                    FXMLLoader loader = new FXMLLoader();
                    Parent root = loader.load(getClass().getResource("/app/view/form.fxml").openStream());
                    Form formController = loader.getController();
                    formController.setNote(note);
                    formStage.setTitle("Edit Note");
                    formStage.setScene(new Scene(root));
                    formStage.setResizable(false);
                    formStage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            btnDelete.setOnAction(actionEvent -> {
                NoteHelper noteHelper = new NoteHelper();
                noteHelper.deleteNote(note);

                listView.getItems().remove(note);
            });

            ckbxStatus.setOnAction(actionEvent -> {
                int done = ckbxStatus.isSelected() ? 1 : 0;
                note.setDone(done);

                NoteHelper noteHelper = new NoteHelper();
                noteHelper.updateNote(note);

                if (App.filterStatus.equals(Const.DONE) && done == 0 || App.filterStatus.equals(Const.UNDONE) && done == 1)
                    listView.getItems().remove(note);
            });

            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        }
    }
}
