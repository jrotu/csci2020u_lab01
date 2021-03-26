package sample;

// Adapted From: http://www.java2s.com/Code/Java/JavaFX/UsingFXMLtocreateaUI.htm

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.fxml.Initializable;
import javafx.scene.control.MenuBar;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Controller implements Initializable {
    @FXML
    private TableView<StudentRecord> tableView;
    @FXML
    private TableColumn<? extends Object, ? extends Object> studentID;
    @FXML
    private TableColumn<? extends Object, ? extends Object> assignments;
    @FXML
    private TableColumn<? extends Object, ? extends Object> midterm;
    @FXML
    private TableColumn<? extends Object, ? extends Object> finalExam;
    @FXML
    private TableColumn<? extends Object, ? extends Object> finalMark;
    @FXML
    private TableColumn<? extends Object, ? extends Object> letterGrade;
    @FXML
    private MenuBar menuBar;

    private File selectedFile;
    private String currentFilename = "./lab08.csv";

    private Stage primaryStage;

    @FXML
    public void initialize(java.net.URL arg0, ResourceBundle arg1) {
        menuBar.setFocusTraversable(true);
        studentID.setCellValueFactory(new PropertyValueFactory<>("studentID"));
        assignments.setCellValueFactory(new PropertyValueFactory<>("assignments"));
        midterm.setCellValueFactory(new PropertyValueFactory<>("midterm"));
        finalExam.setCellValueFactory(new PropertyValueFactory<>("finalExam"));
        finalMark.setCellValueFactory(new PropertyValueFactory<>("finalMark"));
        letterGrade.setCellValueFactory(new PropertyValueFactory<>("letterGrade"));
        tableView.setItems(DataSource.getAllMarks());
    }

    /**
     * Handle action related to input (in this case specifically only responds to
     * keyboard event CTRL-A).
     *
     * @param event Input event.
     */
    @FXML
    private void handleKeyInput(final InputEvent event)
    {
        if (event instanceof KeyEvent)
        {
            final KeyEvent keyEvent = (KeyEvent) event;
            if (keyEvent.isControlDown() && keyEvent.getCode() == KeyCode.A)
            {
                provideAboutFunctionality();
            }
        }
    }

    /**
     * Perform functionality associated with "About" menu selection or CTRL-A.
     */
    private void provideAboutFunctionality()
    {
        System.out.println("You clicked on About!");
    }

    @FXML
    private void save() throws IOException {
        FileWriter writer = new FileWriter(currentFilename);
        for ( StudentRecord sr : tableView.getItems() ) {
            writer.write(String.format(
                "%s,%s,%s,%s\n",
                sr.getStudentID(),
                sr.getAssignments(),
                sr.getMidterm(),
                sr.getFinalExam()
            ));
        }
        writer.close();
    }

    // Adapted From: https://docs.oracle.com/javase/8/javafx/api/javafx/stage/FileChooser.html
    private File fileChooser() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open CSV File");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("CSV Files", "*.csv"),
            new FileChooser.ExtensionFilter("All Files", "*.*"));
        return fileChooser.showOpenDialog(primaryStage);
    }

    // Adapted From:
    // Answer: https://stackoverflow.com/a/63667531/10827766
    // User: https://stackoverflow.com/users/2065017/blagae
    @FXML
    private void saveAs() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, IOException {
        if ((selectedFile = fileChooser()) != null) { currentFilename = selectedFile.getAbsolutePath(); save(); }
    }

    @FXML
    private void load() throws IOException {
        ObservableList<StudentRecord> marks = FXCollections.observableArrayList();
        Scanner scanner = new Scanner(new File(currentFilename));
        while ( scanner.hasNextLine() ) {
            String[] values = scanner.nextLine().split(",");
            marks.add(new StudentRecord(
                values[0],
                Float.parseFloat(values[1]),
                Float.parseFloat(values[2]),
                Float.parseFloat(values[3])
            ));
        }
        tableView.setItems(marks);
    }

    // Adapted From:
    // Answer: https://stackoverflow.com/a/63667531/10827766
    // User: https://stackoverflow.com/users/2065017/blagae
    @FXML
    private void open() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, IOException {
        if ((selectedFile = fileChooser()) != null) { currentFilename = selectedFile.getAbsolutePath(); load(); }
    }

    // Adapted From:
    // Answer: https://stackoverflow.com/a/52770465/10827766
    // User: https://stackoverflow.com/users/5875610/kevin-hernandez
    @FXML
    private void _new() { tableView.getItems().clear(); }

    // Adapted From:
    // Answer: https://stackoverflow.com/a/40299547/10827766
    // User: https://stackoverflow.com/users/2299040/sahil-manchanda
    public void setStage(Stage stage){
        this.primaryStage = stage;
    }

    @FXML
    private void exit() { primaryStage.close(); }
}
