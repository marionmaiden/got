package com.mhgf.domain.valueobject;

import org.junit.jupiter.api.Test;

import static com.mhgf.domain.valueobject.Operations.*;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Mario Freitas
 */
public class OperationsTest {

    @Test
    public void testOperationValues() {
        assertThat(Operations.values()).hasSize(3).containsExactlyInAnyOrder(SUM, PASS, SUB);
    }

    @Test
    public void testFromValue() {

        assertThat(Operations.fromValue(1)).isEqualByComparingTo(SUM);
        assertThat(Operations.fromValue(0)).isEqualByComparingTo(PASS);
        assertThat(Operations.fromValue(-1)).isEqualByComparingTo(SUB);

        assertThat(Operations.fromValue(-100)).isEqualByComparingTo(PASS);
        assertThat(Operations.fromValue(100)).isEqualByComparingTo(PASS);

    }
}
