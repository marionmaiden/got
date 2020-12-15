package com.mhgf.domain.aggregate;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

/**
 * This class represents one game request with the player id and the value player
 * (can be the initial value if he is player 1 or an operation {-1, 0, 1})
 * @author Mario Freitas
 */
@AllArgsConstructor
@Getter
public class TurnRequest {
    private UUID playerId;
    private Integer value;

}
