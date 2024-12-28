package programs;
import com.battle.heroes.army.Unit;
import com.battle.heroes.army.programs.Edge;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

public class UnitTargetPathFinderImplTest {

    private UnitTargetPathFinderImpl unitTargetPathFinder;

    @BeforeEach
    public void setUp() {
        unitTargetPathFinder = new UnitTargetPathFinderImpl();
    }

    @Test
    public void testGetTargetPath_noObstacles() {
        Unit attackUnit = new Unit("A1", "A1", 100, 50, 20, "A1", null, null, 0, 0);
        Unit targetUnit = new Unit("A2", "A2", 100, 50, 20, "A2", null, null, 5, 5);

        List<Unit> existingUnits = new ArrayList<>();

        List<Edge> path = unitTargetPathFinder.getTargetPath(attackUnit, targetUnit, existingUnits);

        assertNotNull(path);
        assertFalse(path.isEmpty());
        assertEquals(attackUnit.getxCoordinate(), path.get(0).getX());
        assertEquals(attackUnit.getyCoordinate(), path.get(0).getY());
    }

    @Test
    public void testGetTargetPath_withObstacles() {
        Unit attackUnit = new Unit("A1", "A1", 100, 50, 20, "A1", null, null, 0, 0);
        Unit targetUnit = new Unit("A2", "A2", 100, 50, 20, "A2", null, null, 5, 5);

        Unit obstacleUnit1 = new Unit("A3", "A3", 100, 50, 20, "A3", null, null, 2, 2);
        Unit obstacleUnit2 = new Unit("A4", "A4", 100, 50, 20, "A4", null, null, 3, 2);

        List<Unit> existingUnits = new ArrayList<>();
        existingUnits.add(obstacleUnit1);
        existingUnits.add(obstacleUnit2);

        List<Edge> path = unitTargetPathFinder.getTargetPath(attackUnit, targetUnit, existingUnits);

        assertNotNull(path);
        assertFalse(path.isEmpty());

        assertEquals(targetUnit.getxCoordinate(), path.get(path.size() - 1).getX());
        assertEquals(targetUnit.getyCoordinate(), path.get(path.size() - 1).getY());
    }

    @Test
    public void testGetTargetPath_noPath() {
        Unit attackUnit = new Unit("A1", "A1", 100, 50, 20, "A1", null, null, 0, 0);
        Unit targetUnit = new Unit("A2", "A2", 100, 50, 20, "A2", null, null, 5, 5);

        Unit obstacleUnit1 = new Unit("A3", "A3", 100, 50, 20, "A3", null, null, 0, 1);
        Unit obstacleUnit2 = new Unit("A4", "A4", 100, 50, 20, "A4", null, null, 1, 0);


        List<Unit> existingUnits = new ArrayList<>();
        existingUnits.add(obstacleUnit1);
        existingUnits.add(obstacleUnit2);

        List<Edge> path = unitTargetPathFinder.getTargetPath(attackUnit, targetUnit, existingUnits);

        assertTrue(path.isEmpty());
    }

    @Test
    public void testGetTargetPath_emptyGrid() {
        Unit attackUnit = new Unit("A1", "A1", 100, 50, 20, "A1", null, null, 0, 0);
        Unit targetUnit = new Unit("A2", "A2", 100, 50, 20, "A2", null, null, 26, 20);

        List<Unit> existingUnits = new ArrayList<>();

        List<Edge> path = unitTargetPathFinder.getTargetPath(attackUnit, targetUnit, existingUnits);

        assertNotNull(path);
        assertFalse(path.isEmpty());
        assertEquals(targetUnit.getxCoordinate(), path.get(path.size() - 1).getX());
        assertEquals(targetUnit.getyCoordinate(), path.get(path.size() - 1).getY());
    }

    @Test
    public void testGetTargetPath_pathLength() {
        Unit attackUnit = new Unit("A1", "A1", 100, 50, 20, "A1", null, null, 0, 0);
        Unit targetUnit = new Unit("A2", "A2", 100, 50, 20, "A2", null, null, 4, 4);

        Unit obstacleUnit1 = new Unit("A3", "A3", 100, 50, 20, "A3", null, null, 2, 2);

        List<Unit> existingUnits = new ArrayList<>();
        existingUnits.add(obstacleUnit1);

        List<Edge> path = unitTargetPathFinder.getTargetPath(attackUnit, targetUnit, existingUnits);

        assertNotNull(path);
        assertFalse(path.isEmpty());
        assertEquals(9, path.size());  // Ожидаем 9 шагов (0,0 -> 1,0 -> 2,0 -> 2,1 -> 3,1 -> 4,1 -> 4,2 -> 4,3 -> 4,4)
    }
}
