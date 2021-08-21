package views;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Login extends DefaultPage {

    private Label loginText;
    private TextField nameField;
    private Button playButton;
    private VBox root;
    private Scene secondScene;
    private Stage primaryStage;

    public Login(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.title.setText("Nuevo ingreso");
        this.loginText = new Label("Escriba su nombre de jugador");
        this.nameField = new TextField();
        this.playButton = new Button("Jugar");
        this.root = new VBox(title, loginText, nameField, playButton);
        giveActions();
    }

    @Override
    protected void giveActions() {
        this.playButton.setOnMouseClicked(e -> this.goToPlay());
    }

    public VBox getRoot() {
        return root;
    }

    private void goToPlay() {
        this.secondScene = new Scene(new NewMatch().getRoot(), 600, 600);
        secondScene.getStylesheets().add("/style/NewMatch.css");
        primaryStage.setScene(secondScene);
        primaryStage.setTitle("Nueva partida");
        primaryStage.show();
    }

}
