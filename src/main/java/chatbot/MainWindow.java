package chatbot;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Yun yun;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/user.jpg"));
    private Image yunImage = new Image(this.getClass().getResourceAsStream("/images/yun.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /**
     * Injects the Yun chatbot instance used to process user input.
     *
     * @param y Chatbot instance used by this window.
     */
    public void setYun(Yun y) {
        yun = y;
    }

    /**
     * Processes the user's input, displays the user and chatbot messages, and clears the input field.
     * Closes the application when the user enters the {@code bye} command.
     */
    @FXML
    private void handleUserInput() {
        String userText = userInput.getText();
        String chatbotReply = yun.getResponse(userInput.getText());
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(userText, userImage),
                DialogBox.getYunDialog(chatbotReply, yunImage)
        );
        userInput.clear();
        if (userText.trim().equalsIgnoreCase("bye")
                || userText.trim().equalsIgnoreCase("Bye")) {
            Stage stage = (Stage) userInput.getScene().getWindow();
            stage.close();
        }
    }
}
