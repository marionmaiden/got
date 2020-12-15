package com.mhgf.domain.entity;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Mario Freitas
 */
public class PlayerTest {

    @Test
    public void testPlayer1() {

        Player p = new Player(true);
        assertThat(p.getId()).isNotNull().isInstanceOf(UUID.class);
        assertThat(p.isPlayer1()).isTrue();
    }

    @Test
    public void testPlayer2() {

        Player p = new Player(false);
        assertThat(p.getId()).isNotNull().isInstanceOf(UUID.class);
        assertThat(p.isPlayer1()).isFalse();
    }

}
