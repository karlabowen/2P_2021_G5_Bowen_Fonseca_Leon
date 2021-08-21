package views;

import javafx.scene.control.Label;

public abstract class DefaultPage {
    protected Label title;

    public DefaultPage() {
        this.title = new Label();
    }

    protected abstract void giveActions();
}
