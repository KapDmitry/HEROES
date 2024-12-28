package programs;

import com.battle.heroes.army.Unit;
import com.battle.heroes.army.programs.Edge;
import com.battle.heroes.army.programs.UnitTargetPathFinder;

import java.util.*;

public class UnitTargetPathFinderImpl implements UnitTargetPathFinder {
    private static final int WIDTH = 27;
    private static final int HEIGHT = 21;

    @Override
    public List<Edge> getTargetPath(Unit attackUnit, Unit targetUnit, List<Unit> existingUnitList) {
        boolean[][] visited = new boolean[WIDTH][HEIGHT];
        Queue<Edge> queue = new LinkedList<>();
        Map<Edge, Edge> parentMap = new HashMap<>();

        for (Unit unit : existingUnitList) {
            if (unit.isAlive()) {
                visited[unit.getxCoordinate()][unit.getyCoordinate()] = true;
            }
        }

        visited[targetUnit.getxCoordinate()][targetUnit.getyCoordinate()] = false;

        Edge start = new Edge(attackUnit.getxCoordinate(), attackUnit.getyCoordinate());
        Edge target = new Edge(targetUnit.getxCoordinate(), targetUnit.getyCoordinate());
        queue.add(start);
        visited[start.getX()][start.getY()] = true;

        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        while (!queue.isEmpty()) {
            Edge current = queue.poll();

            if (current.getX() == target.getX() && current.getY() == target.getY()) {
                List<Edge> path = new ArrayList<>();

                Edge step = current;
                step = parentMap.get(step);

                path.add(target);

                while (step != null) {
                    path.add(step);
                    step = parentMap.get(step);
                }

                Collections.reverse(path);

                return path;
            }

            for (int[] dir : directions) {
                int newX = current.getX() + dir[0];
                int newY = current.getY() + dir[1];

                if (isValid(newX, newY, visited)) {
                    Edge neighbor = new Edge(newX, newY);
                    queue.add(neighbor);
                    visited[newX][newY] = true;
                    parentMap.put(neighbor, current);
                }
            }

        }

        System.out.println(attackUnit.getName() + " No path found");
        return new ArrayList<Edge>();


    }

    private boolean isValid(int x, int y, boolean[][] visited) {
        return x >= 0 && x < WIDTH && y >= 0 && y < HEIGHT && !visited[x][y];
    }
}
