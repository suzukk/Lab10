package mn.edu.must.sqat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GcdTest {

    @Test
    @DisplayName("A2, A6: gcd(7, 0)")
    void gcdWithZeroSecondArgument() {
        assertEquals(7, DataFlowTargets.gcd(7, 0));
    }

    @Test
    @DisplayName("A1, A3-A5, A7-A13: gcd(12, 8)")
    void gcdWithMultipleIterations() {
        assertEquals(4, DataFlowTargets.gcd(12, 8));
    }
}
