package com.mhgf.domain.repository;

import com.mhgf.domain.aggregate.PlayerResponse;
import com.mhgf.domain.entity.Game;
import com.mhgf.domain.entity.Player;
import lombok.Synchronized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * This class maintains the players that are waiting for an opponent
 * They are not persisted by simplicity of project
 * @author Mario Freitas
 */
@Component
public class PlayersPoll {

    @Autowired
    private GameMap gameMap;

    // Games waiting for player 2
    ConcurrentLinkedQueue<Game> pendingGames;

    /**
     * Constructor
     */
    public PlayersPoll() {
        pendingGames = new ConcurrentLinkedQueue<>();
    }

    /**
     * Add a player
     * If pendingGames is empty, add the newly created game to the Queue
     * If pendingGames is not empty, retrieve the game from there and assign player 2 to it
     * @return
     */
    @Synchronized(value = "pendingGames")
    public PlayerResponse addPlayer() {

        Player player;
        Game game;
        boolean isPlayer1 = true;

        // If pendingGames is empty, player should wait for an opponent
        if (pendingGames.isEmpty()) {
            player = new Player(true);
            game = new Game(player);
            pendingGames.offer(game);
            gameMap.addGame(game);
        }
        else {
            game = pendingGames.poll();
            player = new Player(false);
            game.setPlayer2(player);
            isPlayer1 = false;
        }

        return new PlayerResponse(player.getId(), game.getId(), isPlayer1);
    }

}
