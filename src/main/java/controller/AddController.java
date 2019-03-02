package controller;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.ResourceBundle;

import exceptions.TooManyItemsException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.*;

public class AddController {
    TaskList opEntry = new TaskList();


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ImageView addTaskButton;

    @FXML
    private TextField taskTitleField;

    @FXML
    private TextField taskLocationField;

    @FXML
    private TextField timeBox;


    @FXML
    private DatePicker dueDateBox;

    @FXML
    private ImageView backButton;

    @FXML
    void initialize() {
        backButton.setOnMouseClicked(event -> {
            try {
                goToMain(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        addTaskButton.setOnMouseClicked(event -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            try {
                addTask(event);
                alert.setTitle("Add successful");
                alert.setContentText("Add was successful");
                alert.showAndWait();
                goToMain(event);
            } catch (TooManyItemsException e) {
                alert.setTitle("Add unsuccessful");
                alert.setContentText("Add was unsuccessful: too many items already added");
                alert.showAndWait();
            } catch (ParseException e) {
                alert.setTitle("Add unsuccessful");
                alert.setContentText("Add was unsuccessful: check your time format");
                alert.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void goToMain(MouseEvent event) throws IOException {
        // SOURCE: https://github.com/JaretWright/GUIDemo/blob/master/src/guidemo/ExampleOfTableViewController.java
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("main.fxml"));
        Parent parent = loader.load();

        Scene parentScene = new Scene(parent);

        //access the controller and call a method
        MainController controller = loader.getController();
        controller.setTaskList(opEntry);

        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(parentScene);
        window.show();
    }

    private void addTask(MouseEvent event) throws TooManyItemsException, ParseException {
        Task task;
        String taskDueDateString = new String();
        String taskName;
        String location;


        taskTitleField.getText();

        if (!(dueDateBox.getValue() == null) && !(timeBox.getText().trim().isEmpty())){
            task = new DueTask();
            taskDueDateString = (dueDateBox.getValue().toString()+" "+timeBox.getText());
            task.setDate(taskDueDateString);
        } else {
            task = new SimpleTask();
        }

        taskName = taskTitleField.getText();
        location = taskLocationField.getText();


        task.setName(taskName);
        task.setLocation(location);
        task.setActive(true);
        opEntry.addTask(task);
    }



//    // REQUIRES: TaskList
//    // MODIFIES: TaskList
//    // EFFECTS: provides an interface to add new tasks to a list, only if there is < 15 tasks
//    public void add() throws ParseException, TooManyItemsException {
//
//        Task task;
//        String taskDueDateString = new String();
//
//        pm.print("What would you like to do?");
//        String taskName = scanner.nextLine();
//
//        pm.print("Would you like to have a due date added to this item? (y/n)");
//        String wantsDueDate = scanner.nextLine();
//        if (wantsDueDate.equals("y")){
//            task = new DueTask();
//            while (true) {
//                try {
//                    pm.print("Date and time you would like to complete it by? [Format: MMM dd yyyy hh:mm]");
//                    taskDueDateString = scanner.nextLine();
//                    task.setDate(taskDueDateString);
//                    break;
//                } catch (ParseException e) {
//                    pm.print("Invalid date format! Try again");
//                }
//            }
//        } else {
//            task = new SimpleTask();
//        }
//
//
//        pm.print("Location this task is to be completed?");
//        String location = scanner.nextLine();
//
//
//        task.setName(taskName);
//        task.setLocation(location);
//        task.setActive(true);
//        opEntry.addTask(task);
//
//    }


    public void setTaskList(TaskList tl){
        opEntry = tl;
    }
}
