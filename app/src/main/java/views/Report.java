package views;

import java.util.Date;
import java.util.List;

import controllers.FileController;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import models.Match;

public class Report extends DefaultPage {

    private Label filterByLabel;
    private ChoiceBox<String> filterByBox;
    private TableView<Match> reportTable;
    private HBox filterByContainer;
    private VBox root;

    private List<Match> matches;
    private ObservableList<Match> tableData;

    public Report() {
        init();
        addOptions();
        loadTable();
        giveActions();
    }

    @Override
    protected void giveActions() {
        filterByBox.getSelectionModel().selectedItemProperty()
                .addListener((ObservableValue<? extends String> ov, String old_val, String new_val) -> {
                    tableData.sort((Match m1, Match m2) -> filter(new_val, m1, m2));
                });

    }

    private int filter(String filter, Match m1, Match m2) {
        int result = 0;
        switch (filter) {
            case "Fecha":
                result = m2.getDate().compareTo(m1.getDate());
                break;
            case "Jugador":
                result = m1.getName().compareTo(m2.getName());
                break;
            case "Puntos":
                result = m1.getPointsPlayer().compareTo(m2.getPointsPlayer());
                break;
        }
        return result;
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

        this.matches = FileController.deserializeMatches("matches.ser");
        this.tableData = FXCollections.observableArrayList(matches);

        filterByContainer.setId("filterCont");

    }

    private void loadTable() {
        TableColumn<Match, String> playerCol = new TableColumn<>("Jugador");
        playerCol.setMinWidth(200);
        // playerCol.setSortable(false);
        playerCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Match, Date> dateCol = new TableColumn<>("Fecha");
        dateCol.setMinWidth(200);
        // dateCol.setSortable(false);
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));

        TableColumn<Match, Integer> plPointsCol = new TableColumn<>("Puntos jugador");
        plPointsCol.setMinWidth(100);
        // plPointsCol.setSortable(false);
        plPointsCol.setCellValueFactory(new PropertyValueFactory<>("pointsPlayer"));

        TableColumn<Match, Integer> opPointsCol = new TableColumn<>("Puntos oponente");
        opPointsCol.setMinWidth(100);
        // opPointsCol.setSortable(false);
        opPointsCol.setCellValueFactory(new PropertyValueFactory<>("pointsOponent"));

        TableColumn<Match, Integer> diffCol = new TableColumn<>("Diferencia");
        diffCol.setMinWidth(100);
        // plPointsCol.setSortable(false);
        diffCol.setCellValueFactory(new PropertyValueFactory<>("pointsDiff"));

        TableColumn<Match, Integer> timeCol = new TableColumn<>("Segundos");
        timeCol.setMinWidth(100);
        // timeCol.setSortable(false);
        timeCol.setCellValueFactory(new PropertyValueFactory<>("seconds"));

        TableColumn<Match, Integer> stolenCol = new TableColumn<>("Robadas");
        stolenCol.setMinWidth(100);
        stolenCol.setCellValueFactory(new PropertyValueFactory<>("stolenPiles"));

        reportTable.getColumns().addAll(playerCol, dateCol, plPointsCol, opPointsCol, diffCol, stolenCol);
        reportTable.setItems(tableData);
    }
}
