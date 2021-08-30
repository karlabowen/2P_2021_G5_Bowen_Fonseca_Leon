package controllers;

import javafx.application.Platform;
import views.NewMatch;

public class TimeThread implements Runnable {

    @Override
    public void run() {
        while (!NewMatch.stopMatch) {
            try {
                Thread.sleep(1000);
                NewMatch.time++;
                Platform.runLater(() -> NewMatch.timeLabel.setText(String.valueOf(NewMatch.time)));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}
