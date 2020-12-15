package com.mhgf.domain.entity;

import com.mhgf.domain.aggregate.TurnRequest;
import com.mhgf.domain.aggregate.TurnResponse;
import com.mhgf.domain.service.GameLogic;
import com.mhgf.domain.valueobject.Operations;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * This class represents a game, with the players assigned and the turns handler
 * @author Mario Freitas
 */
public class Game {

    @Getter
    private UUID id;
    private Map<UUID,Player> players;
    private GameLogic logic;
    // Controls if it is player's 1 turn
    private boolean player1 = true;

    /**
     * Constructor
     * Creates an empty game and assigns player 1
     * @param p1
     */
    public Game(Player p1) {
        id = UUID.randomUUID();
        players = new HashMap<>();
        players.put(p1.getId(), p1);
    }

    /**
     * Assigns player 2
     * @param p2
     */
    public void setPlayer2(Player p2) {
        if (p2.isPlayer1())
            throw new RuntimeException("Player 2 is assigned as Player 1");
        players.put(p2.getId(), p2);
    }


    /**
     * Start a game from a turn request
     * First checks if both players have been assigned
     * Then start the game logic with the initial value and return the Turn response
     * @param turn
     * @return
     */
    private TurnResponse start(TurnRequest turn) {
        logic = new GameLogic(turn.getValue());
        return new TurnResponse(null, turn.getValue(), player1, id, logic.didWin());
    }

    /**
     * Plays a turn
     * First checks if the player corresponds to the right turn
     * @param turn
     * @return
     */
    public TurnResponse play(TurnRequest turn) {
        // If the player trying to play is not in current turn, returns an empty response
        // Yeah, I know I could have done it better as a custom or error response but I didn't want spend too much time
        // on it
        if (!checkPlayer(turn.getPlayerId()))
            return new TurnResponse();

        TurnResponse response;

        if (logic == null)
           response = start(turn);
        else {
            response = logic.turn(Operations.fromValue(turn.getValue()));
            response.setPlayer1(player1);
            response.setGameId(id);
        }

        // Before ending, change the player's turn
        player1 = !player1;
        return response;
    }

    /**
     * Check if it is player 1 or player 2 turn
     * @param playerUUID
     * @return
     */
    private boolean checkPlayer(UUID playerUUID) {
        if (logic != null && players.size() < 2)
            throw new RuntimeException("The game requires two players to proceed");

        else if (!players.containsKey(playerUUID))
            throw new RuntimeException("Player not found in game");

        return (player1 && players.get(playerUUID).isPlayer1()) ||
                (!player1 && !players.get(playerUUID).isPlayer1());
    }

    /**
     * Return the value if already assigned
     * This is used mainly when player 1 starts the game but there isn't a player 2 yet
     * Player 2 when created needs to know which value was assigned by player 1
     * @return
     */
    public Integer getValue() {
        if (logic == null)
            return null;
        return logic.getValue();
    }

}
