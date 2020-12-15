package com.mhgf.domain.service;

import com.mhgf.domain.aggregate.TurnResponse;
import com.mhgf.domain.valueobject.Operations;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Mario Freitas
 */
public class GameLogicTest {

    @Test
    public void testDidWin() {

        GameLogic g = new GameLogic(1);
        assertThat(g.didWin()).isTrue();

        g = new GameLogic(-1);
        assertThat(g.didWin()).isTrue();

        g = new GameLogic(10);
        assertThat(g.didWin()).isFalse();

        g = new GameLogic(-10);
        assertThat(g.didWin()).isFalse();
    }

    @Test
    public void testTurn() {

        GameLogic g = new GameLogic(1);

        TurnResponse response =  g.turn(Operations.PASS);
        assertThat(response.getOperation()).isEqualByComparingTo(Operations.PASS);
        assertThat(response.isWon()).isTrue();
        assertThat(response.getResultingValue()).isEqualTo(1);

        response =  g.turn(Operations.SUM);
        assertThat(response.getOperation()).isEqualByComparingTo(Operations.SUM);
        assertThat(response.isWon()).isFalse();
        assertThat(response.getResultingValue()).isEqualTo(2);

        response =  g.turn(Operations.SUB);
        assertThat(response.getOperation()).isEqualByComparingTo(Operations.SUB);
        assertThat(response.isWon()).isTrue();
        assertThat(response.getResultingValue()).isEqualTo(1);
    }
}
