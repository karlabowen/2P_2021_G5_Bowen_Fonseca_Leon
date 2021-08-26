package views;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import models.Match;

public class Report extends DefaultPage {

    private Label filterByLabel;
    private ChoiceBox<String> filterByBox;
    private TableView<Match> reportTable;
    private HBox filterByContainer;
    private VBox root;

    public Report() {
        init();
        addOptions();
        giveActions();
    }

    @Override
    protected void giveActions() {
        // TODO Auto-generated method stub

    }

    private void addOptions() {
        filterByBox.getItems().add("Fecha");
        filterByBox.getItems().add("Jugador");
        filterByBox.getItems().add("Puntos");
    }

    public VBox getRoot() {
        return root;
    }

    @Override
    protected void init() {
        this.title.setText("Reportes");
        this.filterByLabel = new Label("Filtrar por");
        this.filterByBox = new ChoiceBox<>();
        this.reportTable = new TableView<>();
        this.filterByContainer = new HBox(filterByLabel, filterByBox);
        this.root = new VBox(title, filterByContainer, reportTable);
        filterByContainer.setId("filterCont");
    }

}
