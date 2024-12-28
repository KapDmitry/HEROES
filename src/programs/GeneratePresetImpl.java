package programs;

import com.battle.heroes.army.Army;
import com.battle.heroes.army.Unit;
import com.battle.heroes.army.programs.GeneratePreset;

import java.util.*;

public class GeneratePresetImpl implements GeneratePreset {

    @Override
    public Army generate(List<Unit> unitList, int maxPoints) {
        System.out.println("Generating Preset");
        System.out.println(unitList);
        System.out.println(maxPoints);

        unitList.sort((u1, u2) -> {
            double efficiency1 = (u1.getBaseAttack() / (double) u1.getCost()) + (u1.getHealth() / (double) u1.getCost());
            double efficiency2 = (u2.getBaseAttack() / (double) u2.getCost()) + (u2.getHealth() / (double) u2.getCost());
            return Double.compare(efficiency2, efficiency1);
        });

        Army computerArmy = new Army();
        int remainingPoints = maxPoints;

        List<Unit> units = new ArrayList<>();
        Set<String> occupiedCoordinates = new HashSet<>();

        for (Unit unit : unitList) {
            int count = Math.min(11, remainingPoints / unit.getCost());

            for (int i = 0; i < count; i++) {
                String iStr = " " + Integer.toString(i);

                int[] coordinates = generateUniqueCoordinates(occupiedCoordinates);
                if (coordinates == null) {
                    System.out.println("Failed to generate unique coordinates after 100 attempts.");
                    break;
                }

                int x = coordinates[0];
                int y = coordinates[1];

                units.add(new Unit(unit.getName() + iStr, unit.getUnitType(), unit.getHealth(), unit.getBaseAttack(), unit.getCost(), unit.getAttackType(), unit.getAttackBonuses(), unit.getDefenceBonuses(), x, y));
            }

            remainingPoints -= count * unit.getCost();
            if (remainingPoints <= 0) {
                break;
            }
        }

        computerArmy.setUnits(units);
        computerArmy.setPoints(maxPoints - remainingPoints);

        System.out.println("Computer Army: " + computerArmy.getUnits().size());
        System.out.println("Computer Army points: " + computerArmy.getPoints());



        /*Army computerArmy = new Army();
        Unit unit = unitList.get(0);
        computerArmy.getUnits().add(new Unit(unit.getName()+" 1", unit.getUnitType(), unit.getHealth(), unit.getBaseAttack(), unit.getCost(), unit.getAttackType(), unit.getAttackBonuses(), unit.getDefenceBonuses(), 1, 10));
        computerArmy.getUnits().add(new Unit(unit.getName()+" 1", unit.getUnitType(), unit.getHealth(), unit.getBaseAttack(), unit.getCost(), unit.getAttackType(), unit.getAttackBonuses(), unit.getDefenceBonuses(), 1, 11));
        computerArmy.getUnits().add(new Unit(unit.getName()+" 1", unit.getUnitType(), unit.getHealth(), unit.getBaseAttack(), unit.getCost(), unit.getAttackType(), unit.getAttackBonuses(), unit.getDefenceBonuses(), 1, 12));
        computerArmy.setPoints(unit.getCost());*/
        return computerArmy;

    }

    private int[] generateUniqueCoordinates(Set<String> occupiedCoordinates) {
        Random random = new Random();
        int x, y;
        String coordinateKey;
        int attempts = 0;

        do {
            x = random.nextInt(3);
            y = random.nextInt(21);
            coordinateKey = x + "," + y;
            attempts++;
        } while (occupiedCoordinates.contains(coordinateKey) && attempts < 100);

        if (attempts >= 100) {
            return null;
        }

        occupiedCoordinates.add(coordinateKey);
        return new int[]{x, y};
    }
}
