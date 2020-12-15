package com.mhgf.domain.entity;

import lombok.Getter;

import java.util.UUID;

/**
 * This class represents a player
 * The player is simply its id and a flag informing if it is player 1
 * @author Mario Freitas
 */
@Getter
public class Player {

    private UUID id;
    private boolean isPlayer1;

    public Player(boolean isPlayer1) {
        this.id = UUID.randomUUID();
        this.isPlayer1 = isPlayer1;
    }
}
