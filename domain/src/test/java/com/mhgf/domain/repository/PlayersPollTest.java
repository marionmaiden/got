package com.mhgf.domain.repository;

import com.mhgf.domain.aggregate.PlayerResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Mario Freitas
 */
@SpringBootTest
public class PlayersPollTest {

    @Autowired
    PlayersPoll playersPoll;

    @Test
    public void testAddPlayer() {
        PlayerResponse p1 = playersPoll.addPlayer();
        PlayerResponse p2 = playersPoll.addPlayer();
        PlayerResponse p3 = playersPoll.addPlayer();

        // Checking if player 1 and 2 share the same game but player 3 don't
        assertThat(p1.getGameId()).isEqualByComparingTo(p2.getGameId());
        assertThat(p1.getGameId()).isNotEqualByComparingTo(p3.getGameId());

        // p1 and p3 are player 1 but not p2
        assertThat(p1.isPlayer1()).isTrue();
        assertThat(p2.isPlayer1()).isFalse();
        assertThat(p3.isPlayer1()).isTrue();

        // All players have valid UUID
        assertThat(p1.getPlayerId()).isNotNull().isInstanceOf(UUID.class);
        assertThat(p2.getPlayerId()).isNotNull().isInstanceOf(UUID.class);
        assertThat(p3.getPlayerId()).isNotNull().isInstanceOf(UUID.class);
    }


    @SpringBootApplication
    static class TestConfiguration {
    }
}
