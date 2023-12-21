import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


class Position {
    public long row, col;
    public Position(long row, long col) { this.row = row; this.col = col; }

    public long manhattanDistance(Position other) {
        return Math.abs(this.row - other.row) + Math.abs(this.col - other.col);
    }

    @Override
    public String toString() {
        return "(" + row + ", " + col + ")";
    }
}

public class Day11 {



    public static List<Integer> emptyRows(List<String> lines) {
        List<Integer> emptyRows = new ArrayList<>();
        for (int i = 0; i < lines.size(); ++i) {
            if (lines.get(i).replaceAll("\\.", "").isEmpty()) {
                emptyRows.add(i);
            }
        }
        return emptyRows;
    }
    public static List<Integer> emptyColumns(List<String> lines) {
        List<Integer> emptyColumns = new ArrayList<>();
        for (int i = 0; i < lines.get(0).length(); ++i) {
            boolean isEmpty = true;
            for (String line: lines) {
                if (line.charAt(i) == '#') {
                    isEmpty = false;
                    break;
                }
            }
            if (isEmpty) {emptyColumns.add(i);}
        }
        return emptyColumns;
    }

    public static List<Position> locateGalaxiesPartOne(List<String> lines, List<Integer> emptyRows, List<Integer> emptyColumns) {
        List<String> expandedUniverse = new ArrayList<>(lines);
        for (int i = emptyRows.size() - 1; i > -1; --i) {
            expandedUniverse.add(emptyRows.get(i), expandedUniverse.get(emptyRows.get(i)));
        }
        for (int i = 0; i < expandedUniverse.size(); ++i) {
            String temp = expandedUniverse.get(i);
            for (int j = emptyColumns.size() - 1; j > -1; --j) {
                temp = temp.substring(0, emptyColumns.get(j)) + "." + temp.substring(emptyColumns.get(j));
            }
            expandedUniverse.set(i, temp);
        }
        List<Position> galaxies = new ArrayList<>();
        for (int row = 0; row < expandedUniverse.size(); ++row) {
            String line = expandedUniverse.get(row);
            for (int col = 0; col < line.length(); ++col) {
                if (line.charAt(col) == '#') {
                    galaxies.add(new Position(row, col));
                }
            }
        }
        return galaxies;
    }

    public static List<Position> locateGalaxiesPartTwo(List<String> lines, List<Integer> emptyRows, List<Integer> emptyColumns) {
        List<Position> galaxies = new ArrayList<>();
        for (int row = 0; row < lines.size(); ++row) {
            String line = lines.get(row);
            for (int col = 0; col < line.length(); ++col) {
                if (line.charAt(col) == '#') {
                    int finalRow = row;
                    int finalColumn = col;
                    long offsetRow = row + 999999L * emptyRows.stream().filter(x -> x < finalRow).toList().size();
                    long offsetCol = col + 999999L * emptyColumns.stream().filter(x -> x < finalColumn).toList().size();
                    galaxies.add(new Position(offsetRow, offsetCol));
                }
            }
        }
        return galaxies;
    }

    public static long partOne(List<String> lines) {
        List<Integer> emptyRows = emptyRows(lines);
        List<Integer> emptyColumns = emptyColumns(lines);
        List<Position> galaxies = locateGalaxiesPartOne(lines, emptyRows, emptyColumns);
        long steps = 0;
        for (int i = 0; i < galaxies.size(); ++i) {
            for (int j = i + 1; j < galaxies.size(); ++j) {
                steps += galaxies.get(i).manhattanDistance(galaxies.get(j));
            }
        }
        return steps;
    }

    public static long partTwo(List<String> lines) {
        List<Integer> emptyRows = emptyRows(lines);
        List<Integer> emptyColumns = emptyColumns(lines);
        List<Position> galaxies = locateGalaxiesPartTwo(lines, emptyRows, emptyColumns);
        long steps = 0;
        for (int i = 0; i < galaxies.size(); ++i) {
            for (int j = i + 1; j < galaxies.size(); ++j) {
                steps += galaxies.get(i).manhattanDistance(galaxies.get(j));
            }
        }
        return steps;
    }

    public static void main(String[] args) throws IOException {
        List<String> lines = inputFileReader.fileToStringList("input/day11_in.txt");
        System.out.println("Part 1: " + partOne(lines));
        System.out.println("Part 2: " + partTwo(lines));
    }
}
