
package mn.edu.must.sqat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GcdTest {

    @Test
    @DisplayName("A1-A2-A5-A6: x13, y13 -> use20 and use15")
    void gcdWithZeroSecondArgument() {
        assertEquals(7, DataFlowTargets.gcd(7, 0));
    }

    @Test
    @DisplayName("A1-A3-A4-A5-A7-A8-A9-A10-A11-A12-A13: loop DU pairs")
    void gcdWithMultipleIterations() {
        assertEquals(4, DataFlowTargets.gcd(12, 8));
    }
}

