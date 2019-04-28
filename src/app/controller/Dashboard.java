package app.controller;

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
import javafx.scene.control.TableView;
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
    private TableView<?> tableView;
    @FXML
    private ComboBox<String> cmbxShow;


    private NoteHelper noteHelper = new NoteHelper();
    private ObservableList<Note> notes = FXCollections.observableArrayList();
    private String status;
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

        listView.setCellFactory(new NoteCellFactory());

        listView.setItems(notes);
    }

    private void setListener() {
        btnAdd.setOnAction(actionEvent -> showAddWindow());

        cmbxShow.valueProperty().addListener((observableValue, s, t1) -> setData(t1));

        formStage.setOnHiding(windowEvent -> {
            setData();
        });

        listView.setOnMouseClicked(mouseEvent -> {
            Note note = listView.getSelectionModel().getSelectedItem();
            System.out.print(note.getId());
        });
    }

    void setData() {
        setData("");
    }

    void setData(String selected) {
        try {
            ResultSet resultSet;

            if (!selected.equals("")) status = selected;

            switch (status) {
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
                notes.add(new Note(resultSet.getInt("id"), resultSet.getString("title"), resultSet.getString("description"), resultSet.getInt("done")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void showAddWindow() {
        try {
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(getClass().getResource("/app/view/form.fxml").openStream());
            Form form = loader.getController();

            formStage.setTitle("Add Note");
            formStage.setScene(new Scene(root));
            formStage.setResizable(false);
            formStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
