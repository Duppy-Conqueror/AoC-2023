import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day14 {

    public static int partOne(List<String> lines) {
        int sum = 0;
        List<List<Character>> columns = new ArrayList<>();
        int lineLength = lines.get(0).length();
        for (int j = 0; j < lineLength; ++j) {
            List<Character> col = new ArrayList<>();
            for (String line : lines) {
                col.add(line.charAt(j));
            }
            columns.add(col);
        }
        for (List<Character> column: columns) {
            int landingIndex = 0;
            for (int i = 0; i < column.size(); ++i) {
                switch (column.get(i)) {
                    case '.' -> {}
                    case '#' -> landingIndex = i + 1;
                    case 'O' -> {
                        sum += lines.size() - landingIndex;
                        ++landingIndex;
                    }
                }

            }
        }
        return sum;
    }

    public static void slideNorth(List<List<Character>> map) {
        for (int j = 0; j < map.get(0).size(); ++j) {
            int landingIndex = 0;
            for (int i = 0; i < map.size(); ++i) {
                switch (map.get(i).get(j)) {
                    case '.' -> {}
                    case '#' -> landingIndex = i + 1;
                    case 'O' -> {
                        if (landingIndex < i) {
                            map.get(landingIndex).set(j, 'O');
                            map.get(i).set(j, '.');
                        }
                        ++landingIndex;
                    }
                }
            }
        }
    }
    public static void slideWest(List<List<Character>> map) {
        for (List<Character> row: map) {
            int landingIndex = 0;
            for (int i = 0; i < row.size(); ++i) {
                switch (row.get(i)) {
                    case '.' -> {}
                    case '#' -> landingIndex = i + 1;
                    case 'O' -> {
                        if (landingIndex < i) {
                            row.set(landingIndex, 'O');
                            row.set(i, '.');
                        }
                        ++landingIndex;
                    }
                }
            }
        }
    }
    public static void slideSouth(List<List<Character>> map) {
        for (int j = 0; j < map.get(0).size(); ++j) {
            int landingIndex = map.size() - 1;
            for (int i = landingIndex; i >= 0; --i) {
                switch (map.get(i).get(j)) {
                    case '.' -> {}
                    case '#' -> landingIndex = i - 1;
                    case 'O' -> {
                        if (landingIndex > i) {
                            map.get(landingIndex).set(j, 'O');
                            map.get(i).set(j, '.');
                        }
                        --landingIndex;
                    }
                }
            }
        }
    }
    public static void slideEast(List<List<Character>> map) {
        for (List<Character> row: map) {
            int landingIndex = row.size() - 1;
            for (int i = landingIndex; i >= 0; --i) {
                switch (row.get(i)) {
                    case '.' -> {}
                    case '#' -> landingIndex = i - 1;
                    case 'O' -> {
                        if (landingIndex > i) {
                            row.set(landingIndex, 'O');
                            row.set(i, '.');
                        }
                        --landingIndex;
                    }
                }
            }
        }
    }

    public static int calculateScore(List<List<Character>> map) {
        int sum = 0;
        int numLines = map.size();
        for (int i = 0; i < numLines; ++i) {
            sum += (numLines - i) * map.get(i).stream().filter(x -> x == 'O').toList().size();
        }
        return sum;
    }

    public static void printMap(List<List<Character>> map) {
        for (List<Character> row: map) {
            for (char ch: row) {
                System.out.print(ch);
            }
            System.out.println();
        }
        System.out.println();
    }

    public static boolean isIdentical(List<List<Character>> map, List<List<Character>> historyMap) {
        for (int i = 0; i < map.size(); ++i) {
            for (int j = 0; j < map.get(i).size(); ++j) {
                if (map.get(i).get(j) != historyMap.get(i).get(j)) {
                    return false;
                }
            }
        }
        return true;
    }

    public static int partTwo(List<String> lines) {
        List<List<Character>> map = new ArrayList<>();
        for (String line: lines) {
            map.add(new ArrayList<>(line.chars().mapToObj(x -> (char) x).toList()));
        }
        List<List<List<Character>>> log = new ArrayList<>();
        int startOfCycle = -1;
        int period = 0;
        for (int times = 0; times < 1000000000; ++times) {
            slideNorth(map);
            slideWest(map);
            slideSouth(map);
            slideEast(map);
            for (int i = log.size() - 1; i >= 0; --i) {
                if (isIdentical(map, log.get(i))) {
                    startOfCycle = i;
                    period = times - i;
                    break;
                }
            }
            if (period > 0) {
                int offset = (1000000000 - times) % period;
                map = log.get(startOfCycle + offset - 1);
                break;
            }
            else {
                List<List<Character>> record = new ArrayList<>();
                for (List<Character> row: map) {
                    record.add(new ArrayList<>(row));
                }
                log.add(record);
            }
        }
        return calculateScore(map);
    }

    public static void main(String[] args) throws IOException {
        List<String> lines = inputFileReader.fileToStringList("input/day14_in.txt");
        System.out.println("Part 1: " + partOne(lines));
        System.out.println("Part 2: " + partTwo(lines));
    }
}
