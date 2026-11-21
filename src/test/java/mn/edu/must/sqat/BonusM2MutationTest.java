package mn.edu.must.sqat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BonusM2MutationTest {

    @Test
    @DisplayName("M2: Total attendance must not replace longest streak")
    void totalAttendanceMustNotReplaceBestStreak() {
        int[] attendance = {1, 1, 1, 0, 1, 1, 1, 1};

        assertEquals(5, DataFlowTargets.bonus(attendance, 7));
    }
}
