import java.io.IOException;
import java.util.*;

public class Day08 {
    public static HashMap<String, List<String>> parsePaths(List<String> lines) {
        HashMap<String, List<String>> pathMap = new HashMap<>();
        for (String line: lines) {
            pathMap.put(line.substring(0,3), List.of(line.substring(7,10), line.substring(12,15)));
        }
        return pathMap;
    }

    public static long leastCommonMultiple(List<Integer> list) {
        long ret = 1;
        int divisor = 2;

        while (true) {
            int counter = 0;
            boolean divisible = false;

            for (int i = 0; i < list.size(); i++) {
                if (list.get(i) == 0) {
                    return 0;
                } else if (list.get(i) < 0) {
                    list.set(i, list.get(i) * -1);
                }
                if (list.get(i) == 1) {
                    counter++;
                }
                if (list.get(i) % divisor == 0) {
                    divisible = true;
                    list.set(i, list.get(i) / divisor);
                }
            }
            if (divisible) {
                ret = ret * divisor;
            } else {
                divisor++;
            }

            if (counter == list.size()) {
                return ret;
            }
        }
    }

    public static int partOne(List<String> lines) {
        String instructions = lines.get(0);
        HashMap<String, List<String>> pathMap = parsePaths(lines.subList(2, lines.size()));
        String current = "AAA";
        int steps = 0;
        while (!current.equals("ZZZ")) {
            char direction = instructions.charAt(steps % instructions.length());
            if (direction == 'L') {
                current = pathMap.get(current).get(0);
            }
            else {
                current = pathMap.get(current).get(1);
            }
            ++steps;
        }
        return steps;
    }

    public static long partTwo(List<String> lines) {
        String instructions = lines.get(0);
        HashMap<String, List<String>> pathMap = parsePaths(lines.subList(2, lines.size()));
        List<String> currents = new ArrayList<>(pathMap.keySet().stream().filter(x -> x.charAt(2) == 'A').toList());
        List<Integer> steps = new ArrayList<>();
        for (String current: currents) {
            int numSteps = 0;
            while (current.charAt(2) != 'Z') {
                char direction = instructions.charAt(numSteps % instructions.length());
                if (direction == 'L') {
                    current = pathMap.get(current).get(0);
                }
                else {
                    current = pathMap.get(current).get(1);
                }
                ++numSteps;
            }
            steps.add(numSteps);
        }
        return leastCommonMultiple(steps);
    }

    public static void main(String[] args) throws IOException {
        List<String> lines = inputFileReader.fileToStringList("input/day08_in.txt");
        System.out.println("Part 1: " + partOne(lines));
        System.out.println("Part 2: " + partTwo(lines));
    }
}
