package com.mhgf.domain.aggregate;

import com.mhgf.domain.valueobject.Operations;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private boolean isPlayer1;
    private boolean won;

}
