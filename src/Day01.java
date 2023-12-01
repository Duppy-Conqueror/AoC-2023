import java.io.IOException;
import java.util.*;

public class Day01 {
    public static List<String> keywords = new ArrayList<>(List.of("1", "2", "3", "4", "5", "6", "7", "8", "9",
            "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"));

    public static int partOne(List<String> lines) {
//        List<String> digitsOnly = lines.stream().map(s -> s.replaceAll("[^0-9]", "")).toList();
//        List<Integer> integers = new ArrayList<>();
//        for (String s: digitsOnly) {
//            integers.add(Integer.parseInt(s.charAt(0) + s.substring(s.length() - 1)));
//        }
//        return integers.stream().reduce(0, Integer::sum);
        return lines.stream().map(s -> s.replaceAll("[^0-9]", ""))
                .map(s -> Integer.parseInt(s.charAt(0) + s.substring(s.length() - 1)))
                .reduce(0, Integer::sum);
    }

    public static int partTwo(List<String> lines) {
//        List<String> digitsOnly = new ArrayList<>();
//        List<String> keywords = new ArrayList<>(List.of("1", "2", "3", "4", "5", "6", "7", "8", "9",
//            "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"));
//        for (String s: lines) {
//            String temp = s;
//            String digits = "";
//            while (true) {
//                List<Integer> indices = keywords.stream().map(temp::indexOf).toList();
//                if (indices.stream().reduce(0, Integer::sum) == -keywords.size()) {
//                    break;
//                }
//                int sliceStart = Collections.min(indices.stream().filter(i -> i > -1).toList());
//                int smallest = indices.indexOf(sliceStart);
//                digits += keywords.get(smallest % 9);
//                temp = temp.substring(sliceStart + 1);
//            }
//            digitsOnly.add(digits);
//        }
//        return digitsOnly.stream().map(s -> s.replaceAll("[^0-9]", "")).map(s -> Integer.parseInt(s.charAt(0) + s.substring(s.length() - 1))).reduce(0, Integer::sum);
        return lines.stream().map(s -> {
                            String temp = s;
                            String digits = "";
                            while (true) {
                                List<Integer> indices = keywords.stream().map(temp::indexOf).toList();
                                if (indices.stream().reduce(0, Integer::sum) == -keywords.size()) {
                                    break;
                                }
                                int sliceStart = Collections.min(indices.stream().filter(i -> i > -1).toList());
                                int smallest = indices.indexOf(sliceStart);
                                digits += keywords.get(smallest % (keywords.size() / 2));
                                temp = temp.substring(sliceStart + 1);
                            }
                            return digits;
                        }
                ).map(s -> s.replaceAll("[^0-9]", ""))
                .map(s -> Integer.parseInt(s.charAt(0) + s.substring(s.length() - 1)))
                .reduce(0, Integer::sum);
    }


    public static void main(String[] args) throws IOException {
        List<String> lines = inputFileReader.fileToStringList("input/day01_in.txt");
        System.out.println("Part 1: " + partOne(lines));
        System.out.println("Part 2: " + partTwo(lines));
    }
}
