package com.mhgf.domain.aggregate;

import com.mhgf.domain.valueobject.Operations;
import lombok.*;

import java.util.UUID;

/**
 * This class contains a game turn response, with the operation taken by the opponent, the resulting value
 * and if the opponent won the game or not
 * @author Mario Freitas
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TurnResponse {

    private Operations operation;
    private int resultingValue;
    @Setter
    private boolean isPlayer1;
    @Setter
    private UUID gameId;
    private boolean won;

    public TurnResponse(Operations operation, int resultingValue, boolean won) {
        this.operation = operation;
        this.resultingValue = resultingValue;
        this.won = won;
    }

}
