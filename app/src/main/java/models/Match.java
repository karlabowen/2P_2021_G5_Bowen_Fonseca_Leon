package models;

import java.io.Serializable;
import java.util.Date;

public class Match implements Serializable {
    private String name;
    private Date date;
    private Integer pointsPlayer;
    private int pointsOpponent;
    private int pointsDiff;
    private int seconds;
    private int stolenPiles;

    public Match(String name, Date date) {
        this.name = name;
        this.date = date;
    }

    public Match(String name, Date date, int pointsPlayer, int pointsOpponent, int pointsDiff, int seconds,
            int stolenPiles) {
        this.name = name;
        this.date = date;
        this.pointsPlayer = pointsPlayer;
        this.pointsOpponent = pointsOpponent;
        this.pointsDiff = pointsDiff;
        this.seconds = seconds;
        this.stolenPiles = stolenPiles;
    }

    public Match() {
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getPointsPlayer() {
        return pointsPlayer;
    }

    public void setPointsPlayer(int pointsPlayer) {
        this.pointsPlayer = pointsPlayer;
    }

    public int getPointsOponent() {
        return pointsOpponent;
    }

    public void setPointsOponent(int pointsOponent) {
        this.pointsOpponent = pointsOponent;
    }

    public int getPointsDiff() {
        return pointsDiff;
    }

    public void setPointsDiff(int pointsDiff) {
        this.pointsDiff = pointsDiff;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public int getStolenPiles() {
        return stolenPiles;
    }

    public void setStolenPiles(int stolenPiles) {
        this.stolenPiles = stolenPiles;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((date == null) ? 0 : date.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + pointsDiff;
        result = prime * result + pointsOpponent;
        result = prime * result + pointsPlayer;
        result = prime * result + seconds;
        result = prime * result + stolenPiles;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Match other = (Match) obj;
        if (date == null) {
            if (other.date != null)
                return false;
        } else if (!date.equals(other.date))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (pointsDiff != other.pointsDiff)
            return false;
        if (pointsOpponent != other.pointsOpponent)
            return false;
        if (pointsPlayer != other.pointsPlayer)
            return false;
        if (seconds != other.seconds)
            return false;
        if (stolenPiles != other.stolenPiles)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Match [date=" + date + ", name=" + name + ", pointsDiff=" + pointsDiff + ", pointsOponent="
                + pointsOpponent + ", pointsPlayer=" + pointsPlayer + ", seconds=" + seconds + ", stolenPiles="
                + stolenPiles + "]";
    }

}
