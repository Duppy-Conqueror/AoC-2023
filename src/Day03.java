import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Day03 {
    public static List<List<Character>> characterMap(List<String> lines) {
        return lines.stream().map(line -> line.chars().mapToObj(e -> (char)e).collect(Collectors.toList())).toList();
    }

    public static boolean isSymbol(char ch) {
        return ch != '\0' && ch != '.' && !Character.isDigit(ch);
    }

    public static int partOne(List<String> lines) {
        List<List<Character>> characterMap = characterMap(lines);
        int sum = 0;
        for (int i = 0; i < characterMap.size(); ++i) {
            int lineSum = 0;
            String numStr = "";
            boolean isAdjToSymbol = false;
            int lineLength = characterMap.get(i).size();
            for (int j = 0; j <= lineLength; ++j) {
                if (j == lineLength) {
                    if (!numStr.isEmpty() && isAdjToSymbol) {
                        lineSum += Integer.parseInt(numStr);
                    }
                    continue;
                }
                char currentChar = characterMap.get(i).get(j);
                if (currentChar == '.' || isSymbol(currentChar)) {
                    if (!numStr.isEmpty() && isAdjToSymbol) {
                        lineSum += Integer.parseInt(numStr);
                    }
                    numStr = "";
                    isAdjToSymbol = false;
                }
                else if (Character.isDigit(currentChar)) {
                    numStr += currentChar;
                    if (isAdjToSymbol) {
                        continue;
                    }
                    char topLeft = i - 1 >= 0 && j - 1 >= 0 ? characterMap.get(i-1).get(j-1) : '\0';
                    char top = i - 1 >= 0 ? characterMap.get(i-1).get(j) : '\0';
                    char topRight = i - 1 >= 0 && j + 1 < characterMap.get(i).size() ? characterMap.get(i-1).get(j+1) : '\0';
                    char left = j - 1 >= 0 ? characterMap.get(i).get(j-1) : '\0';
                    char right = j + 1 < characterMap.get(i).size() ? characterMap.get(i).get(j+1) : '\0';
                    char bottomLeft = i + 1 < characterMap.size() && j - 1 >= 0 ? characterMap.get(i+1).get(j-1) : '\0';
                    char bottom = i + 1 < characterMap.size() ? characterMap.get(i+1).get(j) : '\0';
                    char bottomRight = i + 1 < characterMap.size() && j + 1 < characterMap.get(i).size() ? characterMap.get(i+1).get(j+1) : '\0';
                    isAdjToSymbol = isSymbol(topLeft) || isSymbol(top) || isSymbol(topRight) || isSymbol(left) || isSymbol(right) || isSymbol(bottomLeft) || isSymbol(bottom) || isSymbol(bottomRight);
                }
            }
            sum += lineSum;
        }
        return sum;
    }

    public static int patternCode(char left, char middle, char right) {
        int code = 0;
        code += Character.isDigit(left) ? 100 : 0;
        code += Character.isDigit(middle) ? 10 : 0;
        code += Character.isDigit(right) ? 1 : 0;
        return code;
    }

    public static List<Integer> getListOfPartNumber(List<Character> line, int patternCode, int asteriskIndex) {
        int lineLength = line.size();
        switch (patternCode) {
            // 000
            case 0 -> { return List.of(); }
            // 001
            case 1 -> {
                String right = "";
                int temp = asteriskIndex + 1;
                while (temp < lineLength && Character.isDigit(line.get(temp))) {
                    right += line.get(temp);
                    ++temp;
                }
                return List.of(Integer.parseInt(right));
            }
            // 010
            case 10 -> {
                String middle = "" + line.get(asteriskIndex);
                return List.of(Integer.parseInt(middle));
            }
            // 011
            case 11 -> {
                String midRight = "" + line.get(asteriskIndex);
                int temp = asteriskIndex + 1;
                while (temp < lineLength && Character.isDigit(line.get(temp))) {
                    midRight += line.get(temp);
                    ++temp;
                }
                return List.of(Integer.parseInt(midRight));
            }
            // 100
            case 100 -> {
                String left = "";
                int temp = asteriskIndex - 1;
                while (temp >= 0 && Character.isDigit(line.get(temp))) {
                    left = line.get(temp) + left;
                    --temp;
                }
                return List.of(Integer.parseInt(left));
            }
            // 101
            case 101 -> {
                String left = "";
                String right = "";
                int temp = asteriskIndex - 1;
                while (temp >= 0 && Character.isDigit(line.get(temp))) {
                    left = line.get(temp) + left;
                    --temp;
                }
                temp = asteriskIndex + 1;
                while (temp < lineLength && Character.isDigit(line.get(temp))) {
                    right += line.get(temp);
                    ++temp;
                }
                return List.of(Integer.parseInt(left), Integer.parseInt(right));
            }
            // 110
            case 110 -> {
                String midLeft = "" + line.get(asteriskIndex);
                int temp = asteriskIndex - 1;
                while (temp >= 0 && Character.isDigit(line.get(temp))) {
                    midLeft = line.get(temp) + midLeft;
                    --temp;
                }
                return List.of(Integer.parseInt(midLeft));
            }
            // 111
            case 111 -> {
                // By observation, all number are in the range [0, 999] (i.e., no numbers more than 3 digits)
                String midLeftRight = "" + line.get(asteriskIndex - 1) + line.get(asteriskIndex) + line.get(asteriskIndex + 1);
                return List.of(Integer.parseInt(midLeftRight));
            }
        }
        // Not supposed to reach here
        return List.of();
    }

    public static long partTwo(List<String> lines) {
        List<List<Character>> characterMap = characterMap(lines);
        long sum = 0;
        for (int i = 0; i < characterMap.size(); ++i) {
            int lineLength = characterMap.get(i).size();
            for (int j = 0; j < lineLength; ++j) {
                if (characterMap.get(i).get(j) == '*') {
                    int partNumberCount = 0;
                    long product = 1;

                    // Same line
                    char left = j > 0 ? characterMap.get(i).get(j - 1) : '\0';
                    char right = j < lineLength - 1 ? characterMap.get(i).get(j + 1) : '\0';
                    if (Character.isDigit(left)) {
                        ++partNumberCount;
                        String leftString = "";
                        int temp = j - 1;
                        while (temp >= 0 && Character.isDigit(characterMap.get(i).get(temp))) {
                            leftString = characterMap.get(i).get(temp) + leftString;
                            --temp;
                        }
                        product *= Integer.parseInt(leftString);
                    }
                    if (Character.isDigit(right)) {
                        ++partNumberCount;
                        String rightString = "";
                        int temp = j + 1;
                        while (temp < lineLength && Character.isDigit(characterMap.get(i).get(temp))) {
                            rightString += characterMap.get(i).get(temp);
                            ++temp;
                        }
                        product *= Integer.parseInt(rightString);
                    }

                    int patternCode;
                    List<Integer> partNumbers;

                    // Previous line
                    if (i > 0) {
                        char topLeft = j > 0 ? characterMap.get(i - 1).get(j - 1) : '\0';
                        char top = characterMap.get(i - 1).get(j);
                        char topRight = j < lineLength - 1 ? characterMap.get(i - 1).get(j + 1) : '\0';
                        patternCode = patternCode(topLeft, top, topRight);
                        partNumbers = getListOfPartNumber(characterMap.get(i - 1), patternCode, j);
                        if (!partNumbers.isEmpty()) {
                            partNumberCount += partNumbers.size();
                            for (int partNumber : partNumbers) {
                                product *= partNumber;
                            }
                        }
                    }

                    // Next line
                    if (i < characterMap.size()) {
                        char bottomLeft = j > 0 ? characterMap.get(i + 1).get(j - 1) : '\0';
                        char bottom = characterMap.get(i + 1).get(j);
                        char bottomRight = j < lineLength - 1 ? characterMap.get(i + 1).get(j + 1) : '\0';
                        patternCode = patternCode(bottomLeft, bottom, bottomRight);
                        partNumbers = getListOfPartNumber(characterMap.get(i + 1), patternCode, j);
                        if (!partNumbers.isEmpty()) {
                            partNumberCount += partNumbers.size();
                            for (int partNumber : partNumbers) {
                                product *= partNumber;
                            }
                        }
                    }

                    if (partNumberCount == 2) {
                        sum += product;
                    }
                }

            }
        }
        return sum;
    }

    public static void main(String[] args) throws IOException {
        List<String> lines = inputFileReader.fileToStringList("input/day03_in.txt");
        System.out.println("Part 1: " + partOne(lines));
        System.out.println("Part 2: " + partTwo(lines));
    }
}