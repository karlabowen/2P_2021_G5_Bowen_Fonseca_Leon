package views;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Stack;

import controllers.FileController;
import controllers.OpponentThread;
import controllers.SuggestThread;
import controllers.TimeThread;
import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import models.Card;
import models.Match;
import models.Pile;
import models.Role;
import models.Setting;

public class NewMatch extends DefaultPage {

    public static String playerName = "";
    public static boolean stopMatch = false;
    public static int pointsPlayer = 0;
    public static int steals = 0;
    public static int pointsOpponent = 0;
    public static int time = 0;
    public static int remainCards = 0;
    public static final int CARDWIDTH = 90;
    public static final int CARDHEIGHT = 100;
    public static boolean playerTurn = true;
    public static Pile opponentPile = new Pile(new Stack<>(), Role.OPPONENT);
    public static Pile playerPile = new Pile(new Stack<>(), Role.PLAYER);
    public static List<Card> opponentsCardsList = new ArrayList<>();
    public static List<Card> tableCardsList = new ArrayList<>();
    public static List<Card> playerCardsList = new ArrayList<>();
    public static Stack<Card> remainCardsList = new Stack<>();
    public static List<Match> matches = FileController.deserializeMatches("matches.ser");
    public static Setting setting = FileController.deserializeSetting("gameSettings.ser");

    private Label welcomeLabel;
    private Label fixedTextL;
    private Label fixedPointsR;
    private Label fixedPointsL;
    private Label fixedStealsL;
    private Label fixedRemainingLabel;
    private Label fixedTimLabel;
    private Label dinamicPointsL;
    private Label dinamicStealsL;
    private Label opWelcLabel;
    private Label fixedTextR;
    private HBox pointsLBox;
    private HBox pointsRBox;
    private HBox stealedLBox;
    private HBox top;
    private VBox upperLBox;
    private VBox upperRBox;
    private VBox lowerLBox;
    private VBox lowerRBox;
    private VBox left;
    private VBox right;
    private VBox center;
    private BorderPane root;
    public static Label dinamicPointsR = new Label("0");
    public static Label timeLabel = new Label("0");
    public static Label remainCardsLabel = new Label();
    public static ImageView playerPileImg = new ImageView();
    public static ImageView opponentPileImg = new ImageView();
    public static FlowPane opponentPane = new FlowPane(Orientation.HORIZONTAL);
    public static FlowPane tablePane = new FlowPane(Orientation.HORIZONTAL);
    public static FlowPane playerPane = new FlowPane(Orientation.HORIZONTAL);
    public static Image backCardImg = new Image("/images/poker/back1.png", CARDWIDTH, CARDHEIGHT, false, false);
    public static Image nothingImg = new Image("/images/nothing.png", CARDWIDTH, CARDHEIGHT, false, false);
    public static Image suggestImg = new Image("/images/suggest.png", CARDWIDTH, CARDHEIGHT, false, false);

    public NewMatch(String name) {
        playerName = name;
        init();
        giveActions();
    }

    @Override
    protected void giveActions() {
        shuffleOnce();
        loadCardBoard();
    }

    public BorderPane getRoot() {
        return root;
    }

    @Override
    protected void init() {

        System.out.println(setting);

        this.welcomeLabel = new Label("Bienvenid@ " + playerName);
        this.fixedTextL = new Label("Tu informacion");
        this.fixedPointsR = new Label("Puntos ");
        this.fixedPointsL = new Label("Puntos ");
        this.fixedStealsL = new Label("Robadas");
        this.fixedRemainingLabel = new Label("Restantes: ");
        this.fixedTimLabel = new Label("Tiempo: ");
        this.dinamicPointsL = new Label("0");
        this.dinamicStealsL = new Label("0");
        this.opWelcLabel = new Label("Computador");
        this.fixedTextR = new Label("Oponente");

        this.pointsLBox = new HBox(fixedPointsL, dinamicPointsL);
        this.pointsRBox = new HBox(fixedPointsR, dinamicPointsR);
        this.stealedLBox = new HBox(fixedStealsL, dinamicStealsL);
        this.top = new HBox(fixedTimLabel, timeLabel, fixedRemainingLabel, remainCardsLabel);
        this.upperLBox = new VBox(welcomeLabel, fixedTextL, pointsLBox, stealedLBox);
        this.upperRBox = new VBox(opWelcLabel, fixedTextR, pointsRBox);
        this.lowerLBox = new VBox(playerPileImg);
        this.lowerRBox = new VBox(opponentPileImg);
        this.left = new VBox(upperLBox, lowerLBox);
        this.right = new VBox(upperRBox, lowerRBox);
        this.center = new VBox(opponentPane, tablePane, playerPane);
        this.root = new BorderPane();
        root.setTop(top);
        root.setLeft(left);
        root.setRight(right);
        root.setCenter(center);

        pointsPlayer = 0;
        steals = 0;

        playerCardsList = new ArrayList<>();

        playerPane.setId("playerPane");
        opponentPane.setId("opponentPane");
        tablePane.setId("tablePane");

        new Thread(new TimeThread()).start();

    }

    private void shuffleOnce() {
        System.out.println("Inside shuffle");
        List<Card> allCards = FileController.getAllCards(setting.getCardModel());
        List<Integer> pickedNumbers = new ArrayList<>();
        Random rand = new Random();
        int r;
        Card randomCard;
        for (int i = 0; i < allCards.size(); i++) {
            r = rand.nextInt(allCards.size());
            while (pickedNumbers.contains(r)) {
                r = rand.nextInt(allCards.size());
            }
            randomCard = allCards.get(r);
            if (randomCard.getId().equals("back1.png")) {
                System.out.println("backCard");
            } else if (playerCardsList.size() < 3) {
                playerCardsList.add(randomCard);
            } else if (opponentsCardsList.size() < 3) {
                opponentsCardsList.add(randomCard);
            } else if (tableCardsList.size() < 4) {
                tableCardsList.add(randomCard);
            } else {
                remainCardsList.push(randomCard);
            }
            pickedNumbers.add(r);

        }
        System.out.println("player cards size: " + playerCardsList.size());
        System.out.println("oponent cards size: " + opponentsCardsList.size());
        System.out.println("table cards size: " + tableCardsList.size());
        System.out.println("remain  cards size: " + remainCardsList.size());

    }

    private void loadCardBoard() {
        playerPane.getChildren().clear();
        opponentPane.getChildren().clear();
        tablePane.getChildren().clear();
        for (Card c : playerCardsList) {
            ImageView im = new ImageView(c.getImgPath());
            im.setFitHeight(CARDHEIGHT);
            im.setFitWidth(CARDWIDTH);
            StackPane stack = new StackPane(im);
            stack.setOnMouseClicked(e -> stealCards(c));
            playerPane.getChildren().add(stack);
        }
        for (Card c : opponentsCardsList) {
            ImageView im = new ImageView(c.getImgPath());
            im.setFitHeight(CARDHEIGHT);
            im.setFitWidth(CARDWIDTH);
            StackPane stack = new StackPane(im);
            opponentPane.getChildren().add(stack);
        }
        for (Card c : tableCardsList) {
            ImageView im = new ImageView(c.getImgPath());
            im.setFitHeight(CARDHEIGHT);
            im.setFitWidth(CARDWIDTH);
            StackPane stack = new StackPane(im);
            tablePane.getChildren().add(stack);
        }
        playerPileImg.setImage(nothingImg);
        opponentPileImg.setImage(nothingImg);
        remainCardsLabel.setText(String.valueOf(remainCardsList.size()));
        if (setting.getSuggestions().equals("Si")) {
            Thread suggestions = new Thread(new SuggestThread());
            suggestions.start();
        }
    }

    private void stealCards(Card clickedCard) {
        if (playerTurn) {
            if (tableCardsList.contains(clickedCard)) {
                System.out.println("player card is in table!");

                int idxTable = tableCardsList.indexOf(clickedCard);
                int idxPlayer = playerCardsList.indexOf(clickedCard);

                System.out.println("player card: " + playerCardsList.get(idxPlayer));
                System.out.println("table card: " + tableCardsList.get(idxTable));
                System.out.println("clicked card: " + clickedCard);

                playerPile.getCards().push(tableCardsList.get(idxTable));
                playerPile.getCards().push(playerCardsList.get(idxPlayer));
                System.out.println("player pile size: " + playerPile.getCards().size());

                playerPileImg.setImage(
                        new Image(playerPile.getCards().peek().getImgPath(), CARDWIDTH, CARDHEIGHT, false, false));

                tableCardsList.remove(idxTable);
                tablePane.getChildren().remove(idxTable);
                playerCardsList.remove(idxPlayer);
                playerPane.getChildren().remove(idxPlayer);

                pointsPlayer++;
                dinamicPointsL.setText(String.valueOf(pointsPlayer));

            } else if ((!opponentPile.getCards().empty()) && opponentPile.getCards().peek().equals(clickedCard)) {
                System.out.println("player card is in oponent pile!");
                System.out.println("clicked card: " + clickedCard);
                System.out.println("oponent pile card: " + opponentPile.getCards().peek());

                Iterator<Card> opIt = opponentPile.getCards().iterator();
                while (opIt.hasNext()) {
                    playerPile.getCards().push(opIt.next());
                }
                opponentPile.setCards(new Stack<>());

                playerPileImg.setImage(
                        new Image(playerPile.getCards().peek().getImgPath(), CARDWIDTH, CARDHEIGHT, false, false));
                opponentPileImg.setImage(nothingImg);
                steals++;
                dinamicStealsL.setText(String.valueOf(steals));
            } else {
                System.out.println("player card is not in table nor in oponent pile");
                System.out.println("clicked card: " + clickedCard);
                int idxPlayer = playerCardsList.indexOf(clickedCard);
                tableCardsList.add(clickedCard);
                updateTablePane();
                playerCardsList.remove(idxPlayer);
                playerPane.getChildren().remove(idxPlayer);
            }
            checkAvailableCards();
            playerTurn = false;
            Thread opponentTurnThread = new Thread(new OpponentThread());
            opponentTurnThread.start();
        }
    }

    private void checkAvailableCards() {
        System.out.println("Checking if need to shuffle");
        if (playerCardsList.isEmpty()) {
            for (int i = 0; i < 3; i++) {
                if (!remainCardsList.isEmpty()) {
                    playerCardsList.add(remainCardsList.pop());
                }
            }
            remainCardsLabel.setText(String.valueOf(remainCardsList.size()));
            updatePlayerPane();
        }

    }

    private void updatePlayerPane() {
        System.out.println("updating player pane");
        playerPane.getChildren().clear();
        for (Card c : playerCardsList) {
            ImageView im = new ImageView(c.getImgPath());
            im.setFitHeight(CARDHEIGHT);
            im.setFitWidth(CARDWIDTH);
            StackPane stack = new StackPane(im);
            stack.setOnMouseClicked(e -> stealCards(c));
            playerPane.getChildren().add(stack);
        }
    }

    public static void updateTablePane() {
        System.out.println("updating table pane");
        tablePane.getChildren().clear();
        for (Card c : tableCardsList) {
            ImageView im = new ImageView(c.getImgPath());
            im.setFitHeight(CARDHEIGHT);
            im.setFitWidth(CARDWIDTH);
            StackPane stack = new StackPane(im);
            tablePane.getChildren().add(stack);
        }
    }

}