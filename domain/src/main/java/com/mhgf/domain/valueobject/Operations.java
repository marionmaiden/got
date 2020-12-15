package com.mhgf.domain.valueobject;

import lombok.Getter;

/**
 * This enum represents the possible operations over the game {-1, 0, 1}
 * @author Mario Freitas
 */
public enum Operations {
    SUM(1),
    PASS(0),
    SUB(-1);

    @Getter
    private int opValue;

    Operations(int opValue) {
        this.opValue = opValue;
    }

    /**
     * Converts an integer into an operation
     * Anything different from 1 or -1 is PASS
     * I chose this logic to make gameplay more smooth (although it will be controlled by frontend too)
     * @param i
     * @return
     */
    public static Operations fromValue(Integer i) {
        switch (i) {
            case -1:
                return SUB;
            case 1:
                return SUM;
            default:
                return PASS;
        }
    }

}
