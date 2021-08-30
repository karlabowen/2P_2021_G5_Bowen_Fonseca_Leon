package controllers;

import javafx.application.Platform;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import models.Card;
import views.NewMatch;

public class SuggestThread implements Runnable {

    @Override
    public void run() {
        try {
            Thread.sleep(400);
            System.out.println("Inside suggest thread");
            int cardIdx = 0;
            final int idx;
            for (Card c : NewMatch.playerCardsList) {
                if (NewMatch.tableCardsList.contains(c) || ((!NewMatch.opponentPile.getCards().empty())
                        && NewMatch.opponentPile.getCards().peek().equals(c))) {
                    System.out.println(cardIdx);
                    idx = cardIdx;
                    Platform.runLater(() -> ((StackPane) NewMatch.playerPane.getChildren().get(idx)).getChildren()
                            .add(new ImageView(NewMatch.suggestImg)));
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Platform.runLater(
                            () -> ((StackPane) NewMatch.playerPane.getChildren().get(idx)).getChildren().remove(1));

                    break;
                }
                cardIdx++;
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
