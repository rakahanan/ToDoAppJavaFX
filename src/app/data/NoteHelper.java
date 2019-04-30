package app.data;

import app.model.Note;

import java.sql.*;

public class NoteHelper {

    private Connection connection;

    public NoteHelper() {
        connection = SQLiteHelper.getConnection();
        if (connection == null) System.exit(1);
    }

    public ResultSet notesAll() {
        try {
            Statement statement = connection.createStatement();
            return statement.executeQuery("SELECT * FROM notes");
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ResultSet notesDone() {
        try {
            Statement statement = connection.createStatement();
            return statement.executeQuery("SELECT * FROM notes WHERE done = 1");
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ResultSet notesUndone() {
        try {
            Statement statement = connection.createStatement();
            return statement.executeQuery("SELECT * FROM notes WHERE done = 0");
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void saveNote(Note note) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO notes(title, description, done) values(?,?,?);");
            preparedStatement.setString(1, note.getTitle());
            preparedStatement.setString(2, note.getDescription());
            preparedStatement.setInt(3, note.getDone());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateNote(Note note) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE notes SET title = ?, description = ?, done = ? WHERE id = ?;");
            preparedStatement.setString(1, note.getTitle());
            preparedStatement.setString(2, note.getDescription());
            preparedStatement.setInt(3, note.getDone());
            preparedStatement.setInt(4, note.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteNote(Note note) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM notes WHERE id = ?");
            preparedStatement.setInt(1, note.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
