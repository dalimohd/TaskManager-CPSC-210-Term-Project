package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.TaskList;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.Observable;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;

public class ViewController {
    TaskList opEntry;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ListView<String> listView;

    @FXML
    private ImageView backButton;

    @FXML
    private Button removeButton;

    @FXML
    void initialize() {
        backButton.setOnMouseClicked(event -> {
            try {
                goToMain(event);
            } catch (IOException e) {
                e.printStackTrace();
            }

        });

        removeButton.setOnAction(event -> {
            try { removeItem();
            } catch (IndexOutOfBoundsException e){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setTitle("Remove unsuccessful");
                alert.setContentText("Index specified is out of bounds");
                alert.showAndWait();
            }

        });

    }

    private void removeItem(){
//        TextInputDialog inputDialog = new TextInputDialog();
//        inputDialog.setTitle("Remove Task");
//        inputDialog.setHeaderText("Please specify the task number you like to remove:");
//        inputDialog.setContentText("Task number:");
//        Optional<String> result = inputDialog.showAndWait();
//        opEntry.removeTask(Integer.parseInt(result.get()));


        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle("Item Removal");
        alert.setContentText("Are you sure you want to remove this item?");
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
            opEntry.removeTask((listView.getSelectionModel().getSelectedIndices().get(0)+1));
            setTaskList(opEntry);
        }
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

    public void setTaskList (TaskList tl) {
        opEntry = tl;
        ObservableList<String> oTaskList = FXCollections.observableArrayList(opEntry.convertTaskListToStringView());
        listView.setItems(oTaskList);
    }




}
