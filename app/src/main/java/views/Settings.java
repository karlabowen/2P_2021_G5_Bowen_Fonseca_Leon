package views;

import controllers.FileController;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import models.CardModel;
import models.Setting;

public class Settings extends DefaultPage {

    private Label typeCardLabel;
    private ChoiceBox<CardModel> typeCardBox;
    private Label showOptionsLabel;
    private ChoiceBox<String> showOptionsBox;
    private HBox typeCardContainer;
    private HBox showOptionsContainer;
    private Button saveButton;
    private VBox root;

    // private Setting savedSetting;

    public Settings() {
        init();
        addOptions();
        giveActions();
    }

    @Override
    protected void giveActions() {
        this.saveButton.setOnMouseClicked(e -> this.saveSettings());
    }

    public VBox getRoot() {
        return root;
    }

    private void addOptions() {
        typeCardBox.getItems().add(CardModel.POKER);
        typeCardBox.getItems().add(CardModel.SPANISH);
        showOptionsBox.getItems().add("Si");
        showOptionsBox.getItems().add("No");

        // typeCardBox.setValue(savedSetting.getCardModel());
        // showOptionsBox.setValue(savedSetting.getSuggestions());
    }

    private void saveSettings() {
        showInfoAlert("Ajuste guardado", "Cambios guardados", "Se han guardado los ajustes");
        // FileController.serializeSettings(new Setting(typeCardBox.getValue(),
        // showOptionsBox.getValue()),"gameSettings.ser");
    }

    @Override
    protected void init() {
        // this.savedSetting = FileController.deserializeSetting("gameSettings.ser");
        this.title.setText("Ajustes");
        this.typeCardLabel = new Label("Nuevo Juego");
        this.typeCardBox = new ChoiceBox<>();
        this.showOptionsLabel = new Label("Mostrar sugerencias");
        this.showOptionsBox = new ChoiceBox<>();
        this.saveButton = new Button("Guardar");
        this.typeCardContainer = new HBox(typeCardLabel, typeCardBox);
        this.showOptionsContainer = new HBox(showOptionsLabel, showOptionsBox);
        this.root = new VBox(title, typeCardContainer, showOptionsContainer, saveButton);
        typeCardContainer.setId("cardCont");
        showOptionsContainer.setId("showCont");
    }

}
