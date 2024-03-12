import java.io.IOException;
import java.util.*;

// BOO

enum Direction {
    UP, DOWN, LEFT, RIGHT
}

class Point {
    private final int x, y;

    // Boolean values to record if the beam has reached that point through a certain direction
    private boolean up, down, left, right;

    public Point(int x, int y) {
        this.x = x; this.y = y;
        up = false; down = false; left = false; right = false;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean visitedDirection(Direction direction) {
        switch (direction) {
            case UP -> {
                return up;
            }
            case DOWN -> {
                return down;
            }
            case LEFT -> {
                return left;
            }
            case RIGHT -> {
                return right;
            }
            default -> throw new IllegalArgumentException();
        }
    }

    public void visitDirection(Direction direction) {
        switch (direction) {
            case UP -> up = true;
            case DOWN -> down = true;
            case LEFT -> left = true;
            case RIGHT -> right = true;
        }
    }
}

class Beam {

    private int x, y;
    private Direction direction;

    private static final Set<Point> visitedPoints = new HashSet<>();

    public Beam(int x, int y, Direction direction) {
        this.x = x; this.y = y; this.direction = direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void move() {
        switch (direction) {
            case UP -> --y;
            case DOWN -> ++y;
            case LEFT -> --x;
            case RIGHT -> ++x;
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Direction getDirection() {
        return direction;
    }

    public void printBeamStatus() {
        String directionString = "Unknown";
        switch (direction) {
            case UP -> directionString = "UP";
            case DOWN -> directionString = "DOWN";
            case LEFT -> directionString = "LEFT";
            case RIGHT -> directionString = "RIGHT";
        }
        System.out.println("Beam Location: (" + this.x + ", " + this.y + "), Direction: " + directionString);
    }

    public Point getPoint(int x, int y) {
        // Size of visited can only either be 0 or 1
        List<Point> temp = visitedPoints.stream().filter(p -> p.getX() == x && p.getY() == y).toList();
        return temp.isEmpty() ? null : temp.get(0);
    }

    public static Set<Point> getVisitedPoints() {
        return visitedPoints;
    }

    public static void addVisitedPoints(Point point) {
        visitedPoints.add(point);
    }

    public static void clearVisitedPoints() {
        visitedPoints.clear();
    }
}

public class Day16 {

    public static int solve(List<String> lines, int startX, int startY, Direction direction) {
        List<List<Character>> map = lines.stream().map(l -> l.chars().mapToObj(ch -> (char) ch).toList()).toList();
        int xLimit = map.get(0).size();
        int yLimit = map.size();
        LinkedList<Beam> beamQueue = new LinkedList<>(List.of(new Beam(startX, startY, direction)));

        while (!(beamQueue.isEmpty())) {
            Beam beam = beamQueue.poll();
            boolean isDone = false;
            while (beam.getX() >= 0 && beam.getX() < xLimit && beam.getY() >= 0 && beam.getY() < yLimit && !isDone) {
                Point existingPoint = beam.getPoint(beam.getX(), beam.getY());
                if (existingPoint == null) {
                    Point newPoint = new Point(beam.getX(), beam.getY());
                    newPoint.visitDirection(beam.getDirection());
                    Beam.addVisitedPoints(newPoint);
                }
                else {
                    if (existingPoint.visitedDirection(beam.getDirection())) {
                        isDone = true;
                        continue;
                    }
                    existingPoint.visitDirection(beam.getDirection());
                }
                switch (map.get(beam.getY()).get(beam.getX())) {
                    case '.' -> beam.move();
                    case '/' -> {
                        switch (beam.getDirection()) {
                            case UP -> beam.setDirection(Direction.RIGHT);
                            case DOWN -> beam.setDirection(Direction.LEFT);
                            case LEFT -> beam.setDirection(Direction.DOWN);
                            case RIGHT -> beam.setDirection(Direction.UP);
                        }
                        beam.move();
                    }
                    case '\\' -> {
                        switch (beam.getDirection()) {
                            case UP -> beam.setDirection(Direction.LEFT);
                            case DOWN -> beam.setDirection(Direction.RIGHT);
                            case LEFT -> beam.setDirection(Direction.UP);
                            case RIGHT -> beam.setDirection(Direction.DOWN);
                        }
                        beam.move();
                    }
                    case '-' -> {
                        switch (beam.getDirection()) {
                            case UP, DOWN -> {
                                beamQueue.add(new Beam(beam.getX(), beam.getY(), Direction.LEFT));
                                beamQueue.add(new Beam(beam.getX(), beam.getY(), Direction.RIGHT));
                                isDone = true;
                            }
                            case LEFT, RIGHT -> beam.move();
                        }
                    }
                    case '|' -> {
                        switch (beam.getDirection()) {
                            case UP, DOWN -> beam.move();
                            case LEFT, RIGHT ->  {
                                beamQueue.add(new Beam(beam.getX(), beam.getY(), Direction.UP));
                                beamQueue.add(new Beam(beam.getX(), beam.getY(), Direction.DOWN));
                                isDone = true;
                            }
                        }
                    }
                }
            }
        }
        int numEnergizedTiles = Beam.getVisitedPoints().size();
        Beam.clearVisitedPoints();
        return numEnergizedTiles;
    }

    public static int partOne(List<String> lines) {
        return solve(lines, 0, 0, Direction.RIGHT);
    }

    public static int partTwo(List<String> lines) {
        List<List<Character>> map = lines.stream().map(l -> l.chars().mapToObj(ch -> (char) ch).toList()).toList();
        int xLimit = map.get(0).size();
        int yLimit = map.size();
        int highest = 0;

        for (int y = 0; y < yLimit; ++y) {
            for (int x = 0; x < xLimit; ++x) {
                // Top-left corner
                if (y == 0 && x == 0) {
                    highest = Math.max(highest, solve(lines, x, y, Direction.RIGHT));
                    highest = Math.max(highest, solve(lines, x, y, Direction.DOWN));
                }
                // Top-right corner
                else if (y == 0 && x == xLimit - 1) {
                    highest = Math.max(highest, solve(lines, x, y, Direction.LEFT));
                    highest = Math.max(highest, solve(lines, x, y, Direction.DOWN));
                }
                // Bottom-left corner
                else if (y == yLimit - 1 && x == 0) {
                    highest = Math.max(highest, solve(lines, x, y, Direction.RIGHT));
                    highest = Math.max(highest, solve(lines, x, y, Direction.UP));
                }
                // Bottom-right corner
                else if (y == yLimit - 1 && x == xLimit - 1) {
                    highest = Math.max(highest, solve(lines, x, y, Direction.LEFT));
                    highest = Math.max(highest, solve(lines, x, y, Direction.UP));
                }
                // First row
                else if (y == 0) {
                    highest = Math.max(highest, solve(lines, x, y, Direction.DOWN));
                }
                // Last row
                else if (y == yLimit - 1) {
                    highest = Math.max(highest, solve(lines, x, y, Direction.UP));
                }
                // First column
                else if (x == 0) {
                    highest = Math.max(highest, solve(lines, x, y, Direction.RIGHT));
                }
                // Last column
                else if (x == xLimit - 1) {
                    highest = Math.max(highest, solve(lines, x, y, Direction.LEFT));
                }
            }
        }
        return highest;
    }

    public static void main(String[] args) throws IOException {
        List<String> lines = inputFileReader.fileToStringList("input/day16_in.txt");
        System.out.println("Part 1: " + partOne(lines));
        System.out.println("Part 2: " + partTwo(lines));
    }
}
