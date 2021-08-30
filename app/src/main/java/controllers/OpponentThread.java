package controllers;

import java.util.Date;
import java.util.Iterator;
import java.util.Stack;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import models.Card;
import models.Match;
import views.NewMatch;

public class OpponentThread implements Runnable {

    @Override
    public void run() {

        try {
            Thread.sleep(1000);
            Platform.runLater(() -> stealCards());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public synchronized void stealCards() {
        if (!NewMatch.playerTurn) {

            for (int idxOp = 0; idxOp < NewMatch.opponentsCardsList.size();) {
                Card c = NewMatch.opponentsCardsList.get(idxOp);
                if (NewMatch.tableCardsList.contains(c)) {
                    int idxTable = NewMatch.tableCardsList.indexOf(c);

                    NewMatch.opponentPile.getCards().push(NewMatch.tableCardsList.get(idxTable));
                    NewMatch.opponentPile.getCards().push(c);
                    NewMatch.opponentPileImg.setImage(new Image(NewMatch.opponentPile.getCards().peek().getImgPath(),
                            NewMatch.CARDWIDTH, NewMatch.CARDHEIGHT, false, false));

                    NewMatch.tableCardsList.remove(idxTable);
                    NewMatch.tablePane.getChildren().remove(idxTable);
                    NewMatch.opponentsCardsList.remove(idxOp);
                    NewMatch.opponentPane.getChildren().remove(idxOp);

                    NewMatch.pointsOpponent++;
                    NewMatch.dinamicPointsR.setText(String.valueOf(NewMatch.pointsOpponent));
                    break;
                } else if ((!NewMatch.playerPile.getCards().empty())
                        && NewMatch.playerPile.getCards().peek().equals(c)) {
                    Iterator<Card> opIt = NewMatch.playerPile.getCards().iterator();
                    while (opIt.hasNext()) {
                        NewMatch.opponentPile.getCards().push(opIt.next());
                    }
                    NewMatch.playerPile.setCards(new Stack<>());

                    NewMatch.opponentPileImg.setImage(new Image(NewMatch.opponentPile.getCards().peek().getImgPath(),
                            NewMatch.CARDWIDTH, NewMatch.CARDHEIGHT, false, false));
                    NewMatch.playerPileImg.setImage(NewMatch.nothingImg);
                    break;
                } else {
                    NewMatch.tableCardsList.add(c);
                    NewMatch.updateTablePane();
                    NewMatch.opponentsCardsList.remove(idxOp);
                    NewMatch.opponentPane.getChildren().remove(idxOp);
                    break;
                }

            }
            checkAvailableCards();
            NewMatch.playerTurn = true;

        }
    }

    private void checkAvailableCards() {
        if (NewMatch.opponentsCardsList.isEmpty()) {
            for (int i = 0; i < 3; i++) {
                if (!NewMatch.remainCardsList.isEmpty()) {
                    NewMatch.opponentsCardsList.add(NewMatch.remainCardsList.pop());
                }
            }
            NewMatch.remainCardsLabel.setText(String.valueOf(NewMatch.remainCardsList.size()));
            updateOpponentPane();
        }

        if (NewMatch.setting.getSuggestions().equals("Si") && !NewMatch.playerCardsList.isEmpty()) {
            Thread suggestions = new Thread(new SuggestThread());
            suggestions.start();
        }

        if (NewMatch.playerCardsList.isEmpty() && NewMatch.remainCardsList.isEmpty()
                && NewMatch.opponentsCardsList.isEmpty()) {

            NewMatch.stopMatch = true;

            if (NewMatch.pointsPlayer > NewMatch.pointsOpponent)
                showInfoAlert("Fin juego", "Felicidades!", "Usted gano");
            if (NewMatch.pointsPlayer < NewMatch.pointsOpponent)
                showInfoAlert("Fin juego", "Lo sentimos!", "Vuelva a intentar");
            if (NewMatch.pointsPlayer == NewMatch.pointsOpponent)
                showInfoAlert("Fin juego", "Empate!", "Casi casi");

            Match matchInfo = new Match(NewMatch.playerName, new Date(), NewMatch.pointsPlayer, NewMatch.pointsOpponent,
                    NewMatch.pointsPlayer - NewMatch.pointsOpponent, NewMatch.time, NewMatch.steals);
            NewMatch.matches.add(matchInfo);
            FileController.serializeMatches(NewMatch.matches, "matches.ser");
        }
    }

    private void updateOpponentPane() {
        NewMatch.opponentPane.getChildren().clear();
        for (Card c : NewMatch.opponentsCardsList) {
            ImageView im = new ImageView(c.getImgPath());
            im.setFitHeight(NewMatch.CARDHEIGHT);
            im.setFitWidth(NewMatch.CARDWIDTH);
            StackPane stack = new StackPane(im);
            NewMatch.opponentPane.getChildren().add(stack);
        }
    }

    private void showInfoAlert(String title, String header, String content) {
        final Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

}
