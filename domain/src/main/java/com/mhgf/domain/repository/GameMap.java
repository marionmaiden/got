package com.mhgf.domain.repository;

import com.mhgf.domain.entity.Game;
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
    // Ps.: I'm not removing the finished games from the map at runtime to make the project simpler
    private HashMap<UUID, Game> gameMap = new HashMap<>();

    /**
     * Simply add a game to map
     * @param game
     */
    public void addGame(Game game) {
        if (game == null || game.getId() == null)
            throw new RuntimeException("Trying to add an invalid Game. Please check content.");
        gameMap.put(game.getId(), game);
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
