package programs;

import com.battle.heroes.army.Army;
import com.battle.heroes.army.Unit;
import com.battle.heroes.army.programs.PrintBattleLog;
import com.battle.heroes.army.programs.SimulateBattle;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SimulateBattleImpl implements SimulateBattle {
    private PrintBattleLog printBattleLog;

    @Override
    public void simulate(Army playerArmy, Army computerArmy) throws InterruptedException {
        List<Unit> playerUnits = playerArmy.getUnits();
        List<Unit> computerUnits = computerArmy.getUnits();

        List<Unit> allUnits = new ArrayList<>();
        allUnits.addAll(playerUnits);
        allUnits.addAll(computerUnits);

        allUnits.sort(Comparator.comparingInt(Unit::getBaseAttack).reversed());

        while (hasAliveUnits(playerUnits) && hasAliveUnits(computerUnits)) {
            for (Unit unit : allUnits) {
                if (!unit.isAlive()) continue;

                System.out.println(unit.getName()+ " attacking");

                Unit target = unit.getProgram().attack();

                printBattleLog.printBattleLog(unit, target);
            }
        }

        System.out.println("Битва завершена");

    }

    private boolean hasAliveUnits(List<Unit> units) {
        return units.stream().anyMatch(Unit::isAlive);
    }
}