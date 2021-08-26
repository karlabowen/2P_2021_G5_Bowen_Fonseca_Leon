package models;

import java.io.Serializable;
import java.util.Stack;

public class Pile implements Serializable {
    private Stack<Card> cards;
    private Role role;

    public Pile(Stack<Card> cards, Role role) {
        this.cards = cards;
        this.role = role;
    }

    public Pile() {
        this.cards = new Stack<>();
        this.role = Role.PLAYER;
    }

    public Stack<Card> getCards() {
        return cards;
    }

    public void setCards(Stack<Card> cards) {
        this.cards = cards;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((cards == null) ? 0 : cards.hashCode());
        result = prime * result + ((role == null) ? 0 : role.hashCode());
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
        Pile other = (Pile) obj;
        if (cards == null) {
            if (other.cards != null)
                return false;
        } else if (!cards.equals(other.cards))
            return false;
        if (role != other.role)
            return false;
        return true;
    }

}
