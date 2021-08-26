package models;

import java.io.Serializable;

public class Card implements Serializable {
    private String id;
    private int number;
    private CardModel type;
    private String imgPath;

    public Card(String id, int number) {
        this.id = id;
        this.number = number;
        this.type = CardModel.POKER;
    }

    public Card(String id, int number, CardModel type, String imgPath) {
        this.id = id;
        this.number = number;
        this.type = type;
        this.imgPath = imgPath;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public CardModel getType() {
        return type;
    }

    public void setType(CardModel type) {
        this.type = type;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + number;
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
        Card other = (Card) obj;
        if (number != other.number)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Card [id=" + id + ", imgPath=" + imgPath + ", number=" + number + ", type=" + type + "]";
    }

}
