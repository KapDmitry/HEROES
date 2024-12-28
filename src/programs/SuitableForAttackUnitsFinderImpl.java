package programs;

import com.battle.heroes.army.Unit;
import com.battle.heroes.army.programs.SuitableForAttackUnitsFinder;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SuitableForAttackUnitsFinderImpl implements SuitableForAttackUnitsFinder {

    @Override
    public List<Unit> getSuitableUnits(List<List<Unit>> unitsByRow, boolean isLeftArmyTarget) {
        List<Unit> suitableUnits = new ArrayList<>();

        Set<String> aliveUnitPositions = new HashSet<>();

        for (List<Unit> row : unitsByRow) {
            for (Unit unit : row) {
                if (unit != null && unit.isAlive()) {
                    aliveUnitPositions.add(getPositionKey(unit.getxCoordinate(), unit.getyCoordinate()));
                }
            }
        }

        for (List<Unit> row : unitsByRow) {
            int size = row.size();
            for (int i = 0; i < size; i++) {
                Unit currentUnit = row.get(i);

                if (currentUnit == null) continue;

                if (!currentUnit.isAlive()) continue;

                int x = currentUnit.getxCoordinate();
                int y = currentUnit.getyCoordinate();

                if (isLeftArmyTarget) {
                    // Для атакующей армии игрока (цель: левая армия — компьютер)
                    // Подходит, если юнит не закрыт слева
                    if (y == 0 || checkPos(aliveUnitPositions, x,y-1)) {
                        suitableUnits.add(currentUnit);
                    }
                } else {
                    // Для атакующей армии компьютера (цель: правая армия — игрок)
                    // Подходит, если юнит не закрыт справа
                    if (y == size - 1 || checkPos(aliveUnitPositions, x,y+1)) {
                        suitableUnits.add(currentUnit);
                    }
                }
            }
        }

        if (suitableUnits.isEmpty()) {
            System.out.println("No suitable units found");
        }

        return suitableUnits;

    }

    private boolean checkPos(Set<String> aliveUnitPositions, int x, int y) {
        if (y < 0 || y >= 21) {
            return false;
        }
        return !aliveUnitPositions.contains(getPositionKey(x, y));
    }

    private String getPositionKey(int x, int y) {
        return x + "," + y;
    }
}


