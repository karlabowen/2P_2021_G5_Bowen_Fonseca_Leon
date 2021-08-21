package views;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class StartPage extends DefaultPage {

    private Button newGameButton;
    private Button settingsButton;
    private Button reportButton;
    private VBox root;
    private Stage secondStage;
    private Scene secondScene;

    public StartPage() {
        this.title.setText("RUMBAMAZZO");
        this.secondStage = new Stage();
        this.newGameButton = new Button("Nuevo Juego");
        this.settingsButton = new Button("Ajustes");
        this.reportButton = new Button("Reportes");
        this.root = new VBox(this.title, this.newGameButton, this.settingsButton, this.reportButton);
        giveActions();
    }

    public VBox getRoot() {
        return root;
    }

    @Override
    protected void giveActions() {
        this.newGameButton.setOnMouseClicked(e -> this.goToNewGame());
        this.settingsButton.setOnMouseClicked(e -> this.goToSettings());
        this.reportButton.setOnMouseClicked(e -> this.goToReports());
    }

    private void goToNewGame() {
        this.secondScene = new Scene(new Login(secondStage).getRoot(), 400, 400);
        secondScene.getStylesheets().add("/style/Login.css");
        secondStage.setScene(secondScene);
        secondStage.setTitle("Nuevo juego");
        secondStage.show();
    }

    private void goToSettings() {
        this.secondScene = new Scene(new Settings().getRoot(), 400, 400);
        secondScene.getStylesheets().add("/style/Settings.css");
        secondStage.setScene(secondScene);
        secondStage.setTitle("Ajustes");
        secondStage.show();
    }

    private void goToReports() {
        this.secondScene = new Scene(new Report().getRoot(), 500, 500);
        secondScene.getStylesheets().add("/style/Report.css");
        secondStage.setScene(secondScene);
        secondStage.setTitle("Reportes");
        secondStage.show();
    }
}
