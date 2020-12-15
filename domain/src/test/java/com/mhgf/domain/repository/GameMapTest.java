package com.mhgf.domain.repository;

import com.mhgf.domain.entity.Game;
import com.mhgf.domain.entity.Player;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * @author Mario Freitas
 */
@SpringBootTest
public class GameMapTest {

    @Autowired
    GameMap gameMap;

    @Test
    public void getNonExistentGameTest() {
        assertThatThrownBy(() -> gameMap.getGame(UUID.randomUUID()))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Game not found! It might be already ended");
    }

    @Test
    public void addGameTest() {
        Game g = new Game(new Player(true));

        gameMap.addGame(g);
    }

    @Test
    public void addNullGameTest() {
        assertThatThrownBy(() -> gameMap.addGame(null))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Trying to add an invalid Game. Please check content.");
    }

    @Test
    public void addGetGameTest() {
        Game g = new Game(new Player(true));
        gameMap.addGame(g);

        Game g2 = gameMap.getGame(g.getId());

        assertThat(g).isEqualTo(g2);
    }

    @SpringBootApplication
    static class TestConfiguration {
    }
}
