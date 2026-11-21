package mn.edu.must.sqat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BonusAllDUPairsTest {

    @Test
    @DisplayName("DU1: Null attendance")
    void nullAttendance() {
        assertThrows(
            IllegalArgumentException.class,
            () -> DataFlowTargets.bonus(null, 0)
        );
    }

    @Test
    @DisplayName("DU2: Negative threshold")
    void negativeThreshold() {
        assertThrows(
            IllegalArgumentException.class,
            () -> DataFlowTargets.bonus(new int[]{}, -1)
        );
    }

    @Test
    @DisplayName("DU3: Empty attendance, threshold zero")
    void emptyAttendanceThresholdZero() {
        assertEquals(5, DataFlowTargets.bonus(new int[]{}, 0));
    }

    @Test
    @DisplayName("DU4: Empty attendance, threshold one")
    void emptyAttendanceThresholdOne() {
        assertEquals(0, DataFlowTargets.bonus(new int[]{}, 1));
    }

    @Test
    @DisplayName("DU5: One positive attendance")
    void onePositiveAttendance() {
        assertEquals(5, DataFlowTargets.bonus(new int[]{1}, 1));
    }

    @Test
    @DisplayName("DU6: Streak reset and shorter new streak")
    void streakReset() {
        int[] attendance = {1, 1, 0, 1};

        assertEquals(0, DataFlowTargets.bonus(attendance, 4));
    }

    @Test
    @DisplayName("DU7: Five scattered positive days")
    void scatteredAttendance() {
        int[] attendance = {1, 0, 1, 0, 1, 0, 1, 0, 1};

        assertEquals(5, DataFlowTargets.bonus(attendance, 5));
    }

    @Test
    @DisplayName("DU8: Five consecutive days below threshold")
    void fiveConsecutiveBelowThreshold() {
        int[] attendance = {1, 1, 1, 1, 1};

        assertEquals(3, DataFlowTargets.bonus(attendance, 6));
    }
}
