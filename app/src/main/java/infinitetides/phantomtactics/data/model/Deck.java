package infinitetides.phantomtactics.data.model;

import infinitetides.phantomtactics.util.Event;

public class Deck {
    public Event<Card> CardDrawnEvent = new Event<>();

    public Card draw() {
        Card drawn_card = new Card();
        drawn_card.drawnFrom(this);
        CardDrawnEvent.fire(drawn_card);
        return drawn_card;
    }
}
