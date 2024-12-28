package programs;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import com.battle.heroes.army.Unit;

import java.util.*;

public class SuitableForAttackUnitsFinderImplTest {

    private SuitableForAttackUnitsFinderImpl suitableForAttackUnitsFinder;

    @BeforeEach
    public void setUp() {
        suitableForAttackUnitsFinder = new SuitableForAttackUnitsFinderImpl();
    }

    @Test
    public void testGetSuitableUnits_leftArmyTarget() {
        List<List<Unit>> unitsByRow = new ArrayList<>();
        unitsByRow.add(Arrays.asList(
                new Unit("A1", "A", 100, 30, 20, "A", null, null, 0, 0),
                new Unit("A2", "B", 70, 40, 20, "B", null, null, 0, 1)
        ));
        unitsByRow.add(Arrays.asList(
                new Unit("A3", "C", 60, 50, 25, "C", null, null, 1, 0),
                new Unit("A4", "D", 120, 35, 30, "D", null, null, 1, 1)
        ));

        unitsByRow.get(0).get(0).setAlive(true);
        unitsByRow.get(0).get(1).setAlive(true);
        unitsByRow.get(1).get(0).setAlive(false);
        unitsByRow.get(1).get(1).setAlive(true);

        List<Unit> suitableUnits = suitableForAttackUnitsFinder.getSuitableUnits(unitsByRow, true);

        assertEquals(2, suitableUnits.size());
        assertTrue(suitableUnits.contains(unitsByRow.get(0).get(0)));
        assertTrue(suitableUnits.contains(unitsByRow.get(1).get(1)));
    }

    @Test
    public void testGetSuitableUnits_rightArmyTarget() {
        List<List<Unit>> unitsByRow = new ArrayList<>();
        unitsByRow.add(Arrays.asList(
                new Unit("A1", "A", 100, 30, 20, "A", null, null, 0, 0),
                new Unit("A2", "B", 70, 40, 20, "B", null, null, 0, 1)
        ));
        unitsByRow.add(Arrays.asList(
                new Unit("A3", "C", 60, 50, 25, "C", null, null, 1, 0),
                new Unit("A4", "D", 120, 35, 30, "D", null, null, 1, 1)
        ));

        unitsByRow.get(0).get(0).setAlive(true);
        unitsByRow.get(0).get(1).setAlive(true);
        unitsByRow.get(1).get(0).setAlive(false);
        unitsByRow.get(1).get(1).setAlive(true);

        List<Unit> suitableUnits = suitableForAttackUnitsFinder.getSuitableUnits(unitsByRow, false);

        assertEquals(2, suitableUnits.size());
        assertTrue(suitableUnits.contains(unitsByRow.get(1).get(1)));
        assertTrue(suitableUnits.contains(unitsByRow.get(0).get(1)));
    }

    @Test
    public void testGetSuitableUnits_noSuitableUnits() {
        List<List<Unit>> unitsByRow = new ArrayList<>();
        unitsByRow.add(Arrays.asList(
                new Unit("A1", "A", 100, 30, 20, "A", null, null, 0, 0),
                new Unit("A2", "B", 70, 40, 20, "V", null, null, 0, 1)
        ));
        unitsByRow.add(Arrays.asList(
                new Unit("A3", "C", 60, 50, 25, "C", null, null, 1, 0),
                new Unit("A4", "D", 120, 35, 30, "D", null, null, 1, 1)
        ));

        unitsByRow.get(0).get(0).setAlive(false);
        unitsByRow.get(0).get(1).setAlive(false);
        unitsByRow.get(1).get(0).setAlive(false);
        unitsByRow.get(1).get(1).setAlive(false);

        List<Unit> suitableUnits = suitableForAttackUnitsFinder.getSuitableUnits(unitsByRow, true);

        assertTrue(suitableUnits.isEmpty());
    }

    @Test
    public void testGetSuitableUnits_positionBlocked() {
        List<List<Unit>> unitsByRow = new ArrayList<>();
        unitsByRow.add(Arrays.asList(
                new Unit("A1", "A", 100, 30, 20, "A", null, null, 0, 0),
                new Unit("A2", "A", 70, 40, 20, "A", null, null, 0, 1)
        ));


        unitsByRow.get(0).get(0).setAlive(true);
        unitsByRow.get(0).get(1).setAlive(true);


        List<Unit> suitableUnits = suitableForAttackUnitsFinder.getSuitableUnits(unitsByRow, true);

        assertEquals(1, suitableUnits.size());
        assertTrue(suitableUnits.contains(unitsByRow.get(0).get(0)));
    }
}

