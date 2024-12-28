package programs;

import com.battle.heroes.army.Army;
import com.battle.heroes.army.Unit;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class GeneratePresetImplTest {

    @Test
    public void testGeneratePreset() {
        GeneratePresetImpl generatePreset = new GeneratePresetImpl();
        List<Unit> unitList = new ArrayList<>();

        // Create sample units
        unitList.add(new Unit("Archer", "Ranged", 50, 20, 10, "Ranged", null, null, 0, 0));
        unitList.add(new Unit("Knight", "Melee", 100, 30, 20, "Melee", null, null, 0, 0));
        unitList.add(new Unit("Mage", "Magic", 70, 40, 15, "Magic", null, null, 0, 0));

        int maxPoints = 100;

        // Act
        Army generatedArmy = generatePreset.generate(unitList, maxPoints);

        // Assert
        assertNotNull("Generated army should not be null", generatedArmy);
        assertNotNull("Units in generated army should not be null", generatedArmy.getUnits());
        assertTrue("Generated army should contain units", generatedArmy.getUnits().size() > 0);

        // Verify total number of units matches expected
        assertEquals("Total number of generated units should match expected count", 7, generatedArmy.getUnits().size());

        // Verify total cost does not exceed max points
        int totalCost = generatedArmy.getUnits().stream().mapToInt(Unit::getCost).sum();
        assertTrue("Total cost of units should not exceed max points", totalCost <= maxPoints);

        // Verify no duplicate positions
        long uniquePositions = generatedArmy.getUnits().stream()
                .map(unit -> unit.getxCoordinate() + "," + unit.getyCoordinate())
                .distinct().count();
        assertEquals("All units should have unique positions", generatedArmy.getUnits().size(), uniquePositions);

        // Verify unit names are unique
        long uniqueNames = generatedArmy.getUnits().stream().map(Unit::getName).distinct().count();
        assertEquals("All units should have unique names", generatedArmy.getUnits().size(), uniqueNames);

        // Check if units are sorted by efficiency (descending order)
        List<Unit> units = new ArrayList<>(generatedArmy.getUnits());
        for (int i = 1; i < units.size(); i++) {
            double efficiencyPrev = (units.get(i - 1).getBaseAttack() / (double) units.get(i - 1).getCost()) +
                    (units.get(i - 1).getHealth() / (double) units.get(i - 1).getCost());
            double efficiencyCurr = (units.get(i).getBaseAttack() / (double) units.get(i).getCost()) +
                    (units.get(i).getHealth() / (double) units.get(i).getCost());

            assertTrue("Units should be sorted by efficiency in descending order", efficiencyPrev >= efficiencyCurr);
        }

        // Verify there are exactly 6 Mages and 1 Archer in the generated army
        long mageCount = generatedArmy.getUnits().stream().filter(unit -> unit.getName().contains("Mage")).count();
        long archerCount = generatedArmy.getUnits().stream().filter(unit -> unit.getName().contains("Archer")).count();

        assertEquals("There should be exactly 6 Mages in the generated army", 6, mageCount);
        assertEquals("There should be exactly 1 Archer in the generated army", 1, archerCount);
    }

    @Test
    public void testGeneratePresetWithInsufficientPoints() {
        // Arrange
        GeneratePresetImpl generatePreset = new GeneratePresetImpl();
        List<Unit> unitList = new ArrayList<>();

        // Create sample units
        unitList.add(new Unit("Archer", "Ranged", 50, 20, 100, "Ranged", null, null, 0, 0));
        unitList.add(new Unit("Knight", "Melee", 100, 30, 200, "Melee", null, null, 0, 0));

        int maxPoints = 50;

        // Act
        Army generatedArmy = generatePreset.generate(unitList, maxPoints);

        // Assert
        assertNotNull("Generated army should not be null", generatedArmy);
        assertEquals("Generated army should not contain any units", 0, generatedArmy.getUnits().size());
        assertEquals("Remaining points should equal 0", 0, generatedArmy.getPoints());
    }
}
