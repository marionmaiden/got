package com.mhgf.domain.aggregate;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

/**
 * This class represents a response when the user registers to play
 * It contains both play and game id (used to retrieve during the match)
 * and a flag to inform if it is player 1 (which is the player responsible to set the init value)
 * @author Mario Freitas
 */
@Data
@AllArgsConstructor
public class PlayerResponse {

    private UUID playerId;
    private UUID gameId;
    private boolean isPlayer1;

}
