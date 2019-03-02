package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.TaskList;

public class MainController {
    TaskList opEntry = new TaskList();
    ui.FileManager fm = new ui.FileManager();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ImageView addButton;

    @FXML
    private ImageView viewButton;

    @FXML
    private ImageView locationViewButton;

    @FXML
    private ImageView loadButton;

    @FXML
    private ImageView saveButton;

    @FXML
    void initialize() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);

        loadButton.setOnMouseClicked(event -> {
            try {
                opEntry = fm.load("saveState");
                alert.setTitle("Load successful");
                alert.setContentText("State was successfully loaded");
                alert.showAndWait();
            } catch (IOException e) {
                alert.setTitle("Load unsuccessful");
                alert.setContentText("load was unsuccessful: IOException");
                alert.showAndWait();
            } catch (ClassNotFoundException e) {
                alert.setTitle("Load unsuccessful");
                alert.setContentText("load was unsuccessful: ClassNotFoundException");
                alert.showAndWait();
            }
        });

        saveButton.setOnMouseClicked(event -> {
            try {
                fm.save("saveState", opEntry);
                alert.setTitle("Save successful");
                alert.setContentText("State was successfully saved");
                alert.showAndWait();
            } catch (IOException e) {
                alert.setTitle("Save unsuccessful");
                alert.setContentText("Save was unsuccessful: IOException");
                alert.showAndWait();
            }
        });

        addButton.setOnMouseClicked(event -> {
            try {
                goToAdd(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        viewButton.setOnMouseClicked(event -> {
            try {
                goToView(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        locationViewButton.setOnMouseClicked(event -> {
            try {
                goToLocView(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }


    private void goToAdd(MouseEvent event) throws IOException {
        // SOURCE: https://github.com/JaretWright/GUIDemo/blob/master/src/guidemo/ExampleOfTableViewController.java
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("add.fxml"));
        Parent parent = loader.load();

        Scene parentScene = new Scene(parent);

        //access the controller and call a method
        AddController controller = loader.getController();
        controller.setTaskList(opEntry);

        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(parentScene);
        window.show();
    }


    private void goToView(MouseEvent event) throws IOException {
        // SOURCE: https://github.com/JaretWright/GUIDemo/blob/master/src/guidemo/ExampleOfTableViewController.java
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("view.fxml"));
        Parent parent = loader.load();

        Scene parentScene = new Scene(parent);

        //access the controller and call a method
        ViewController controller = loader.getController();
        controller.setTaskList(opEntry);

        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(parentScene);
        window.show();
    }


    private void goToLocView(MouseEvent event) throws IOException {
        // SOURCE: https://github.com/JaretWright/GUIDemo/blob/master/src/guidemo/ExampleOfTableViewController.java
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("locView.fxml"));
        Parent parent = loader.load();

        Scene parentScene = new Scene(parent);

        //access the controller and call a method
        LocViewController controller = loader.getController();
        controller.setTaskList(opEntry);

        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(parentScene);
        window.show();
    }


    public void setTaskList(TaskList tl){
        opEntry = tl;
    }


}
