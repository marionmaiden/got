package com.mhgf.domain.service;

import com.mhgf.domain.aggregate.TurnResponse;
import com.mhgf.domain.valueobject.Operations;
import lombok.Data;

/**
 * This class represents the game logic
 * It is not aware of who is playing, that's managed by Game class
 * @author Mario Freitas
 */
@Data
public class GameLogic {

    private int value;

    /**
     * Constructor
     * I prefer casting the value to a positive string instead of returning an error to the player
     * That makes the gameplay smoother
     * @param value
     */
    public GameLogic(Integer value) {
        // Guarantee that the init value is always positive
        this.value = Math.abs(value);
    }

    /**
     * This method represents a turn in the game
     * It applies the operation over the value and divide by 3 if possible
     * If it is the final turn, returns that it was the winning move
     * @param op
     * @return
     */
    public TurnResponse turn(Operations op) {
        value+= op.getOpValue();

        if (value %3 == 0)
            value/= 3;

        return new TurnResponse(op, value, didWin());
    }

    /**
     * Simple boolean flag to check if turn reached winning condition
     * @return
     */
    public boolean didWin() {
        return value == 1;
    }
}
