package app.customview;

import app.model.Note;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class NoteCellFactory implements Callback<ListView<Note>, ListCell<Note>> {
    @Override
    public ListCell<Note> call(ListView<Note> noteListView) {
        return new NoteCell(noteListView);
    }
}
