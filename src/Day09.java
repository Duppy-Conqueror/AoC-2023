import java.io.IOException;
import java.util.*;

public class Day09 {
    public static List<List<Integer>> composeEvolution(String historyLine) {
        List<List<Integer>> evolution = new ArrayList<>();
        List<Integer> history = new ArrayList<>(Arrays.stream(historyLine.split(" ")).map(Integer::parseInt).toList());
        evolution.add(history);
        while (evolution.get(evolution.size() - 1).stream().reduce(0, Integer::sum) != 0) {
            List<Integer> lastSequence = evolution.get(evolution.size() - 1);
            List<Integer> nextSequence = new ArrayList<>();
            for (int i = 0; i < lastSequence.size() - 1; ++i) {
                nextSequence.add(lastSequence.get(i + 1) - lastSequence.get(i));
            }
            evolution.add(nextSequence);
        }
        return evolution;
    }

    public static long partOne(List<String> lines) {
        long ret = 0;
        for (String line: lines) {
            List<List<Integer>> evolution = composeEvolution(line);
            for (int i = evolution.size() - 1; i > -1; --i) {
                if (i == evolution.size() - 1) {
                    evolution.get(i).add(0);
                }
                else {
                    int base = evolution.get(i).get(evolution.get(i).size() - 1);
                    int offset = evolution.get(i + 1).get(evolution.get(i + 1).size() - 1);
                    evolution.get(i).add(base + offset);
                }
            }
            ret += evolution.get(0).get(evolution.get(0).size() - 1);
        }
        return ret;
    }

    public static long partTwo(List<String> lines) {
        long ret = 0;
        for (String line: lines) {
            List<List<Integer>> evolution = composeEvolution(line);
            for (int i = evolution.size() - 1; i > -1; --i) {
                if (i == evolution.size() - 1) {
                    evolution.get(i).add(0,0);
                }
                else {
                    int base = evolution.get(i).get(0);
                    int offset = evolution.get(i + 1).get(0);
                    evolution.get(i).add(0, base - offset);
                }
            }
            ret += evolution.get(0).get(0);
        }
        return ret;
    }

    public static void main(String[] args) throws IOException {
        List<String> lines = inputFileReader.fileToStringList("input/day09_in.txt");
        System.out.println("Part 1: " + partOne(lines));
        System.out.println("Part 2: " + partTwo(lines));
    }
}
