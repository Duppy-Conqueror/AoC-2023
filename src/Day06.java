import java.io.IOException;
import java.util.*;

public class Day06 {

    public static List<Integer> parseLinePartOne(String line) {
        List<String> inputs = new ArrayList<>(Arrays.stream(line.replaceAll("\\s+"," ").split(" ")).toList());
        inputs.remove(0);
        return inputs.stream().map(Integer::parseInt).toList();
    }

    public static Long parseLinePartTwo(String line) {
        List<String> inputs = new ArrayList<>(Arrays.stream(line.replaceAll("\\s+"," ").split(" ")).toList());
        inputs.remove(0);
        return Long.parseLong(inputs.stream().reduce("", (x, y) -> x + y));
    }

    public static int partOne(List<String> lines) {
        List<Integer> times = parseLinePartOne(lines.get(0));
        List<Integer> distances = parseLinePartOne(lines.get(1));
        List<Integer> ways = new ArrayList<>();
        for (int i = 0; i < times.size(); ++i) {
            int numWays = 0;
            int time = times.get(i);
            for (int j = 0; j <= time; ++j) {
                int distanceTravelled = j * (time - j);
                if (distanceTravelled > distances.get(i)) {
                    ++numWays;
                }
            }
            ways.add(numWays);
        }
        return ways.stream().reduce(1, (x, y) -> x * y);
    }

    public static int partTwo(List<String> lines) {
        long time = parseLinePartTwo(lines.get(0));
        long distance = parseLinePartTwo(lines.get(1));
        int numWays = 0;
        for (int i = 0; i < time; ++i) {
            long distanceTravelled = i * (time - i);
            if (distanceTravelled > distance) {
                ++numWays;
            }
        }
        return numWays;
    }

    public static void main(String[] args) throws IOException {
        List<String> lines = inputFileReader.fileToStringList("input/day06_in.txt");
        System.out.println("Part 1: " + partOne(lines));
        System.out.println("Part 2: " + partTwo(lines));
    }
}
