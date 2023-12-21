import java.io.IOException;
import java.util.*;


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

    public static List<Position> locateGalaxies(List<String> lines, List<Integer> emptyRows, List<Integer> emptyColumns, long times) {
        List<Position> galaxies = new ArrayList<>();
        for (int row = 0; row < lines.size(); ++row) {
            String line = lines.get(row);
            for (int col = 0; col < line.length(); ++col) {
                if (line.charAt(col) == '#') {
                    final int finalRow = row;
                    final int finalColumn = col;
                    galaxies.add(new Position(finalRow + (times - 1) * emptyRows.stream().filter(x -> x < finalRow).toList().size(), finalColumn + (times - 1) * emptyColumns.stream().filter(x -> x < finalColumn).toList().size()));
                }
            }
        }
        return galaxies;
    }

    public static long partOne(List<String> lines) {
        List<Integer> emptyRows = emptyRows(lines);
        List<Integer> emptyColumns = emptyColumns(lines);
        List<Position> galaxies = locateGalaxies(lines, emptyRows, emptyColumns, 2);
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
        List<Position> galaxies = locateGalaxies(lines, emptyRows, emptyColumns,1000000);
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
