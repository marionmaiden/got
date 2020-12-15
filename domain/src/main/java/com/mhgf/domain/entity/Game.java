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
        players.put(p2.getId(), p2);
    }

    /**
     * Check if both players have been assigned
     * @return
     */
    private boolean hasPlayers() {
        return players.size() == 2;
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
        return new TurnResponse(null, turn.getValue(), player1, logic.didWin());
    }

    /**
     * Plays a turn
     * First checks if the player corresponds to the right turn
     * @param turn
     * @return
     */
    public TurnResponse play(TurnRequest turn) {
        if (!checkPlayer(turn.getPlayerId()))
            return new TurnResponse();

        TurnResponse response;

        if (logic == null)
           response = start(turn);
        else {
            if (!hasPlayers())
                throw new RuntimeException("Trying to start a game without all players!");

            response = logic.turn(Operations.fromValue(turn.getValue()), player1);
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
        return (player1 && players.get(playerUUID).isPlayer1()) ||
                (!player1 && !players.get(playerUUID).isPlayer1());
    }

}
