package mn.edu.must.sqat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BonusAllDefsTest {

    @Test
    @DisplayName("B-AD1: Cover all variable definitions")
    void bonusAllDefsCoversEveryDefinition() {
        int[] attendance = {1, 1, 0, 1, 1, 1, 1, 1};

        assertEquals(8, DataFlowTargets.bonus(attendance, 7));
    }

    @Test
    @DisplayName("B-AD2: Cover score definition at line 48")
    void scoreZeroDefinitionReachesReturn() {
        assertEquals(0, DataFlowTargets.bonus(new int[]{}, 1));
    }
}
