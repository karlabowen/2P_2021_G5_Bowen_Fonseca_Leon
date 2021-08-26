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
        init();
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
        if (checkEntry()) {
            this.secondScene = new Scene(new NewMatch(nameField.getText()).getRoot(), 1000, 800);
            secondScene.getStylesheets().add("/style/NewMatch.css");
            primaryStage.setScene(secondScene);
            primaryStage.setTitle("Nueva partida");
            primaryStage.show();
        } else {
            showInfoAlert("Usuario vacio", "Campo vacio", "No se puede crear un jugador sin nombre");
        }

    }

    private boolean checkEntry() {
        if (nameField.getText() == "")
            return false;
        return true;
    }

    @Override
    protected void init() {
        this.title.setText("Nuevo ingreso");
        this.loginText = new Label("Escriba su nombre de jugador");
        this.nameField = new TextField();
        this.playButton = new Button("Jugar");
        this.root = new VBox(title, loginText, nameField, playButton);

    }

}
