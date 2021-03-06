package app.controller;

import app.App;
import app.Const;
import app.customview.NoteCellFactory;
import app.data.NoteHelper;
import app.model.Note;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Dashboard implements Initializable {

    @FXML
    private Button btnAdd;
    @FXML
    private ListView<Note> listView;
    @FXML
    private ComboBox<String> cmbxShow;

    private NoteHelper noteHelper = new NoteHelper();
    private ObservableList<Note> notes = FXCollections.observableArrayList();
    private Stage formStage = new Stage();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initialize();
    }

    @FXML
    void initialize() {
        cmbxShow.setItems(FXCollections.observableArrayList(
                Const.ALL,
                Const.DONE,
                Const.UNDONE));
        cmbxShow.setValue(Const.ALL);

        setListener();
        setData(cmbxShow.getValue());

        listView.setCellFactory(new NoteCellFactory(formStage));

        listView.setItems(notes);

        listView.setFocusTraversable(false);

    }

    private void setListener() {
        btnAdd.setOnAction(actionEvent -> showFormWindow());

        cmbxShow.valueProperty().addListener((observableValue, s, t1) -> setData(t1));

        formStage.setOnHiding(windowEvent -> setData());
    }

    private void setData() {
        setData("");
    }

    private void setData(String selected) {
        try {
            ResultSet resultSet;

            if (!selected.equals("")) App.filterStatus = selected;

            switch (App.filterStatus) {
                case Const.DONE:
                    resultSet = noteHelper.notesDone();
                    break;
                case Const.UNDONE:
                    resultSet = noteHelper.notesUndone();
                    break;
                default:
                    resultSet = noteHelper.notesAll();
                    break;
            }

            notes.clear();
            while (resultSet.next()) {
                notes.add(new Note(
                                resultSet.getInt("id"),
                                resultSet.getString("title"),
                                resultSet.getString("description"),
                                resultSet.getInt("done")
                        )
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void showFormWindow() {
        try {
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(getClass().getResource("/app/view/form.fxml").openStream());
            formStage.setTitle("Add Note");
            formStage.setScene(new Scene(root));
            formStage.setResizable(false);
            formStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
