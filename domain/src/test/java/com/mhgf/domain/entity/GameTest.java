package com.mhgf.domain.entity;

import com.mhgf.domain.aggregate.TurnRequest;
import com.mhgf.domain.aggregate.TurnResponse;
import com.mhgf.domain.valueobject.Operations;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * @author Mario Freitas
 */
public class GameTest {

    @Test
    public void testConstructor() {
        Game g = new Game(new Player(false));

        assertThat(g.getId()).isNotNull().isInstanceOf(UUID.class);
        assertThat(g.getValue()).isNull();
    }

    @Test
    public void testPlayWrongUser() {
        Game g = new Game(new Player(true));
        // Turn request with random player id (it will assume the wrong player is playing)
        TurnRequest request = new TurnRequest(UUID.randomUUID(), g.getId(), 56);

        assertThatThrownBy(() -> {
            g.play(request);
        })
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Player not found in game");
    }

    @Test
    public void testPlay() {
        Player p = new Player(true);
        Game g = new Game(p);
        TurnRequest request = new TurnRequest(p.getId(), g.getId(), 56);

        TurnResponse response = g.play(request);
        assertThat(response.isPlayer1()).isTrue();
        assertThat(response.getResultingValue()).isEqualTo(56);
        assertThat(response.getOperation()).isNull();
        assertThat(response.isWon()).isFalse();
        assertThat(g.getValue()).isEqualTo(56);
    }

    @Test
    public void testPlayWithOnePlayer() {
        Player p = new Player(true);
        Game g = new Game(p);
        TurnRequest request = new TurnRequest(p.getId(), g.getId(), 56);

        // First move
        TurnResponse response = g.play(request);

        // Second move
        assertThatThrownBy(() -> {
            TurnRequest request2 = new TurnRequest(p.getId(), g.getId(), 1);
            g.play(request2);
        })
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("The game requires two players to proceed");
    }

    @Test
    public void testSetTwoPlayer1() {
        Player p = new Player(true);
        Player p2 = new Player(true);
        Game g = new Game(p);

        assertThatThrownBy(() -> g.setPlayer2(p2))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Player 2 is assigned as Player 1");
    }

    @Test
    public void testPlayWithTwoPlayers() {
        Player p = new Player(true);
        Player p2 = new Player(false);
        Game g = new Game(p);

        TurnRequest request = new TurnRequest(p.getId(), g.getId(), 56);

        // First move
        TurnResponse response = g.play(request);
        g.setPlayer2(p2);

        // Second move
        request = new TurnRequest(p2.getId(), g.getId(), 1);
        response = g.play(request);
        assertThat(response.getResultingValue()).isEqualTo(19);
        assertThat(response.isWon()).isFalse();
        assertThat(response.getOperation()).isEqualByComparingTo(Operations.SUM);
        assertThat(response.isPlayer1()).isFalse();
    }

    @Test
    public void testPlayTwice() {
        Player p = new Player(true);
        Player p2 = new Player(false);
        Game g = new Game(p);

        TurnRequest request = new TurnRequest(p.getId(), g.getId(), 56);

        // First move
        TurnResponse response = g.play(request);
        g.setPlayer2(p2);

        // Second move
        request = new TurnRequest(p.getId(), g.getId(), 1);
        response = g.play(request);
        assertThat(response.getResultingValue()).isEqualTo(0);
        assertThat(response.isWon()).isFalse();
        assertThat(response.getOperation()).isNull();
        assertThat(response.isPlayer1()).isFalse();
    }

}
