package views;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;

public abstract class DefaultPage {
    protected Label title;

    public DefaultPage() {
        this.title = new Label();
    }

    protected abstract void giveActions();

    protected abstract void init();

    protected void showInfoAlert(String title, String header, String content) {
        final Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
