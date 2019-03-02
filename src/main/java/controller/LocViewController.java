package controller;

        import java.io.IOException;
        import java.net.URL;
        import java.util.ResourceBundle;

        import javafx.collections.FXCollections;
        import javafx.collections.ObservableList;
        import javafx.event.ActionEvent;
        import javafx.fxml.FXML;
        import javafx.fxml.FXMLLoader;
        import javafx.scene.Node;
        import javafx.scene.Parent;
        import javafx.scene.Scene;
        import javafx.scene.control.Button;
        import javafx.scene.control.ListView;
        import javafx.scene.image.ImageView;
        import javafx.scene.input.MouseEvent;
        import javafx.stage.Stage;
        import model.TaskList;
        import ui.PrintManager;

public class LocViewController {

    PrintManager pm = new PrintManager();
    TaskList opEntry = new TaskList();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ListView<String> listView;

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
        ObservableList<String> oTaskList = null;
        try {
            oTaskList = FXCollections.observableArrayList(pm.convertLocationMapAsStringList(opEntry));
        } catch (IOException e) {
            e.printStackTrace();
        }
        listView.setItems(oTaskList);
    }

}