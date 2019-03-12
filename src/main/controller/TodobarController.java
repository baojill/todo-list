package controller;

import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXPopup;
import com.jfoenix.controls.JFXRippler;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import model.Task;
import ui.ListView;
import ui.PomoTodoApp;
import utility.JsonFileIO;
import utility.Logger;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

// Controller class for Todobar UI
public class TodobarController implements Initializable {
    private static final String todoOptionsPopUpFXML = "resources/fxml/TodoOptionsPopUp.fxml";
    private static final String todoActionsPopUpFXML = "resources/fxml/TodoActionsPopUp.fxml";
    private File todoOptionsFile = new File(todoOptionsPopUpFXML);
    private File todoActionsFile = new File(todoActionsPopUpFXML);

    @FXML
    private Label descriptionLabel;
    @FXML
    private JFXHamburger todoActionsPopUpBurger;
    @FXML
    private StackPane todoActionsPopUpContainer;
    @FXML
    private JFXRippler todoOptionsPopUpRippler;
    @FXML
    private StackPane todoOptionsPopUpBurger;
    
    private Task task;

    private JFXPopup optionsPopUp;
    private JFXPopup actionsPopUp;
    
    // REQUIRES: task != null
    // MODIFIES: this
    // EFFECTS: sets the task in this Todobar
    //          updates the Todobar UI label to task's description
    public void setTask(Task task) {
        this.task = task;
        descriptionLabel.setText(task.getDescription());
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadTodoOptionsPopUp();
        loadTodoOptionsPopUpActionListener();
        loadTodoActionsPopUp();
        loadTodoActionPopUpActionListener();
    }

    private void loadTodoOptionsPopUp() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(todoOptionsFile.toURI().toURL());
            fxmlLoader.setController(new TodoOptionsPopUpController());
            optionsPopUp = new JFXPopup(fxmlLoader.load());
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    private void loadTodoActionsPopUp() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(todoActionsFile.toURI().toURL());
            fxmlLoader.setController(new TodoActionsPopUpController());
            actionsPopUp = new JFXPopup(fxmlLoader.load());
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    // EFFECTS: show view selector pop up when its icon is clicked
    private void loadTodoOptionsPopUpActionListener() {
        todoOptionsPopUpBurger.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                optionsPopUp.show(todoOptionsPopUpBurger,
                        JFXPopup.PopupVPosition.TOP,
                        JFXPopup.PopupHPosition.LEFT,
                        12,
                        15);
            }
        });
    }

    // EFFECTS: show options pop up when its icon is clicked
    private void loadTodoActionPopUpActionListener() {
        todoActionsPopUpBurger.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                actionsPopUp.show(todoActionsPopUpBurger,
                        JFXPopup.PopupVPosition.TOP,
                        JFXPopup.PopupHPosition.RIGHT,
                        -12,
                        15);
            }
        });
    }

    // Inner class: view selector pop up controller
    class TodoOptionsPopUpController {
        @FXML
        private JFXListView<?> optionPopUpList;

        @FXML
        private void submit() {
            int selectedIndex = optionPopUpList.getSelectionModel().getSelectedIndex();
            switch (selectedIndex) {
                case 0:
                    Logger.log("TodoOptionsPopUpController", "1No action is implemented for the selected option");
                    break;
                case 1:
                    Logger.log("TodoOptionsPopUpController", "Task has been deleted");
                    List<Task> tasks = JsonFileIO.read();
                    tasks.remove(task);
                    JsonFileIO.write(tasks);
                    break;
                default:
                    Logger.log("TodoOptionsPopUpController", "4No action is implemented for the selected option");
            }
            returnToListView();
            optionsPopUp.hide();
        }
    }

    private void returnToListView() {
        Logger.log("TodoOptionsPopUpController", "Return to the list view UI.");
        PomoTodoApp.setScene(new ListView(PomoTodoApp.getTasks()));
    }

    // Inner class: option pop up controller
    class TodoActionsPopUpController {
        @FXML
        private JFXListView<?> actionPopUpList;

        @FXML
        private void submit() {
            int selectedIndex = actionPopUpList.getSelectionModel().getSelectedIndex();
            switch (selectedIndex) {
                case 0:
                    Logger.log("TodoActionsPopUpController", "5No action is implemented for the selected option");
                    break;
                case 1:
                    Logger.log("TodoActionsPopUpController", "6No action is implemented for the selected option");
//                    Platform.exit();
                    break;
                default:
                    Logger.log("TodoActionsPopUpController", "7No action is implemented for the selected option");
            }
            actionsPopUp.hide();
        }
    }
}
