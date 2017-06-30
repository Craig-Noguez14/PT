package infinitetides.phantomtactics.data.model;

import infinitetides.phantomtactics.util.Event;

public class Card {

    public Character Hero;

    public void drawnFrom(Deck source) {
        source.CardDrawnEvent.addWatcher((Event<Card> event, Card arg) -> {
            //TODO: ADD CARD TO HAND
        });
    }
}
