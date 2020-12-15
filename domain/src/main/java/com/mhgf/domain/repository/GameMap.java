package com.mhgf.domain.repository;

import com.mhgf.domain.aggregate.TurnRequest;
import com.mhgf.domain.aggregate.TurnResponse;
import com.mhgf.domain.entity.Game;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.UUID;

/**
 * This Class handles the ongoing games
 * They are not persisted by simplicity of project
 * @author Mario Freitas
 */
@Component
public class GameMap {

    // HashMap containing games. Retrieved by Game Id
    HashMap<UUID, Game> gameMap = new HashMap<>();

    /**
     * Simply add a game to map
     * @param game
     */
    public void addGame(Game game) {
        gameMap.put(game.getId(), game);
    }

    /**
     * End a game by removing it from the map
     * @param uuid
     */
    private void endGame(UUID uuid) {
        gameMap.remove(uuid);
    }

    /**
     * Retrieve a game by id. Checks if the game exists
     * @param uuid
     * @return
     */
    public Game getGame(UUID uuid) {
       if (!gameMap.containsKey(uuid))
           throw new RuntimeException("Game not found! It might be already ended");

        return gameMap.get(uuid);
    }
}
