package models;

import java.io.Serializable;

public class Setting implements Serializable {
    private CardModel cardModel;
    private String suggestions;

    public Setting(CardModel cardModel, String suggestions) {
        this.cardModel = cardModel;
        this.suggestions = suggestions;
    }

    public CardModel getCardModel() {
        return cardModel;
    }

    public void setCardModel(CardModel cardModel) {
        this.cardModel = cardModel;
    }

    public String getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(String suggestions) {
        this.suggestions = suggestions;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((cardModel == null) ? 0 : cardModel.hashCode());
        result = prime * result + ((suggestions == null) ? 0 : suggestions.hashCode());
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
        Setting other = (Setting) obj;
        if (cardModel != other.cardModel)
            return false;
        if (suggestions == null) {
            if (other.suggestions != null)
                return false;
        } else if (!suggestions.equals(other.suggestions))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Setting [cardModel=" + cardModel + ", suggestions=" + suggestions + "]";
    }

}
