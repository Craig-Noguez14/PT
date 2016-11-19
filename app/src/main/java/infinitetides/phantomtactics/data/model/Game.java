package infinitetides.phantomtactics.data.model;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class Game {
    private List<Player> players;
    private Map<Player,Action> actionHistory;
    private Board gameBoard;
    private int currentPlayer;
    private int currentWave;
    private Date lastTimePlayed;
}
