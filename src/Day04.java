import java.io.IOException;
import java.util.*;
import java.util.stream.IntStream;

public class Day04 {

    public static List<Integer> intersection(List<Integer> xs, List<Integer> ys) {
        List<Integer> list = new ArrayList<>();
        for (Integer t : xs) {
            if(ys.contains(t)) {
                list.add(t);
            }
        }
        return list;
    }

    public static List<List<Integer>> parseLine(String line) {
        List<List<Integer>> ret = new ArrayList<>();
        List<String> modified = new ArrayList<>(Arrays.stream(line.substring(10).split(" ")).toList());
        modified.removeAll(Collections.singleton(""));
        ret.add(modified.subList(0, modified.indexOf("|")).stream().map(Integer::parseInt).toList());
        ret.add(modified.subList(modified.indexOf("|") + 1, modified.size()).stream().map(Integer::parseInt).toList());
        return ret;
    }

    public static int partOne(List<String> lines) {
        int sum = 0;
        for (String line: lines) {
            List<String> modified = new ArrayList<>(Arrays.stream(line.substring(10).split(" ")).toList());
            modified.removeAll(Collections.singleton(""));
            List<Integer> winningNums = modified.subList(0, modified.indexOf("|")).stream().map(Integer::parseInt).toList();
            List<Integer> numList = modified.subList(modified.indexOf("|") + 1, modified.size()).stream().map(Integer::parseInt).toList();
            int intersectionSize = intersection(winningNums, numList).size();
            sum += intersectionSize > 0 ? (int) Math.pow(2, intersectionSize - 1) : 0;
        }
        return sum;
    }

    public static int partTwo(List<String> lines) {
        List<Integer> instances = new ArrayList<>(IntStream.range(0, lines.size()).map(x -> 1).boxed().toList());
        for (int i = 0; i < lines.size(); ++i) {
            for (int j = 0; j < instances.get(i); ++j) {
                List<List<Integer>> dummy = parseLine(lines.get(i));
                int intersectionSize = intersection(dummy.get(0), dummy.get(1)).size();
                if (intersectionSize > 0) {
                    for (int k = 1; k <= intersectionSize; ++k) {
                        instances.set(i+k, instances.get(i+k) + 1);
                    }
                }
            }
        }
        return instances.stream().reduce(0,Integer::sum);
    }

    public static void main(String[] args) throws IOException {
        List<String> lines = inputFileReader.fileToStringList("input/day04_in.txt");
        System.out.println("Part 1: " + partOne(lines));
        System.out.println("Part 2: " + partTwo(lines));
    }
}
