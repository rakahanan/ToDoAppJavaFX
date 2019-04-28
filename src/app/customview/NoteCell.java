package app.customview;

import app.data.NoteHelper;
import app.model.Note;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;

import java.io.IOException;

public final class NoteCell extends ListCell<Note> {
    @FXML
    private Label lblTitle;
    @FXML
    private Button btnEdit;
    @FXML
    private Button btnDelete;

    private ListView<Note> listView;

    public NoteCell(ListView<Note> listView) {
        this.listView = listView;
        loadFXML();
    }

    private void loadFXML() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/view/list.fxml"));
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
            lblTitle.setText(note.getTitle());
            btnEdit.setOnAction(actionEvent -> {
                System.out.print(note.getId());
            });
            btnDelete.setOnAction(actionEvent -> {
                NoteHelper noteHelper = new NoteHelper();
                noteHelper.deleteNote(note);
                listView.getItems().remove(note);
            });

            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        }
    }
}
