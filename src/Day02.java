import java.io.IOException;
import java.util.*;
import java.util.stream.Stream;

public class Day02 {
    public static int partOne(List<String> lines) {
        int sum = 0;
        for (String line: lines) {
            int maxR = 0, maxG = 0, maxB = 0;
            int gameId = Integer.parseInt(line.split((": "))[0].replace("Game ", ""));
            List<String> draws = Stream.of(line.split(": ")[1].split("; "))
                    .map(l -> Arrays.stream(l.split(", ")).toList())
                    .flatMap(List::stream).toList();
            for (String draw: draws) {
                int numBalls = Integer.parseInt(draw.split(" ")[0]);
                switch(draw.split(" ")[1]) {
                    case "red" -> maxR = Math.max(maxR, numBalls);
                    case "green" -> maxG = Math.max(maxG, numBalls);
                    case "blue" -> maxB = Math.max(maxB, numBalls);
                }
            }
            if (maxR <= 12 && maxG <= 13 && maxB <= 14) {
                sum += gameId;
            }

        }
        return sum;
    }

    public static int partTwo(List<String> lines) {
        int sum = 0;
        for (String line: lines) {
            int maxR = 0, maxG = 0, maxB = 0;
            List<String> draws = Stream.of(line.split(": ")[1].split("; "))
                    .map(l -> Arrays.stream(l.split(", ")).toList())
                    .flatMap(List::stream).toList();
            for (String draw: draws) {
                int numBalls = Integer.parseInt(draw.split(" ")[0]);
                switch(draw.split(" ")[1]) {
                    case "red" -> maxR = Math.max(maxR, numBalls);
                    case "green" -> maxG = Math.max(maxG, numBalls);
                    case "blue" -> maxB = Math.max(maxB, numBalls);
                }
            }
            sum += (maxR * maxG * maxB);
        }
        return sum;
    }

    public static void main(String[] args) throws IOException {
        List<String> lines = inputFileReader.fileToStringList("input/day02_in.txt");
        System.out.println("Part 1: " + partOne(lines));
        System.out.println("Part 2: " + partTwo(lines));
    }
}
