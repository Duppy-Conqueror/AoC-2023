import java.io.IOException;
import java.util.*;
import java.util.stream.IntStream;

public class Day07 {
    public static final HashMap<Character, Integer> cardValueMap = new HashMap<>(){{
        put('A', 12);
        put('K', 11);
        put('Q', 10);
        put('J', 9);
        put('T', 8);
        put('9', 7);
        put('8', 6);
        put('7', 5);
        put('6', 4);
        put('5', 3);
        put('4', 2);
        put('3', 1);
        put('2', 0);
    }};

    public static final HashMap<Character, Integer> cardValueMapJokerVariation = new HashMap<>(){{
        put('A', 12);
        put('K', 11);
        put('Q', 10);
        put('T', 9);
        put('9', 8);
        put('8', 7);
        put('7', 6);
        put('6', 5);
        put('5', 4);
        put('4', 3);
        put('3', 2);
        put('2', 1);
        put('J', 0);
    }};

    public static int getHandType(String hand) {
        List<Character> cards = hand.chars().mapToObj(e -> (char) e).toList();
        List<Integer> cardFrequencies = new ArrayList<>(IntStream.generate(() -> 0).limit(cardValueMap.size()).boxed().toList());
        for (Character card: cards) {
            int cardValue = cardValueMap.get(card);
            cardFrequencies.set(cardValue, cardFrequencies.get(cardValue) + 1);
        }
        Collections.sort(cardFrequencies);
        Collections.reverse(cardFrequencies);
        int maxFrequency = cardFrequencies.get(0);
        int secondMaxFrequency = cardFrequencies.get(1);
        if (maxFrequency == 5) {return 7;}
        else if (maxFrequency == 4) {return 6;}
        else if (maxFrequency == 3) {
            if (secondMaxFrequency == 2) {return 5;}
            else {return 4;}
        }
        else if (maxFrequency == 2) {
            if (secondMaxFrequency == 2) {return 3;}
            else {return 2;}
        }
        else {return 1;}
    }

    public static int getHandTypeJokerVariation(String hand) {
        List<Character> cards = hand.chars().mapToObj(e -> (char) e).toList();
        List<Integer> cardFrequencies = new ArrayList<>(IntStream.generate(() -> 0).limit(cardValueMapJokerVariation.size()).boxed().toList());
        int numJokers = 0;
        for (Character card: cards) {
            int cardValue = cardValueMapJokerVariation.get(card);
            if (cardValue > 0) {cardFrequencies.set(cardValue, cardFrequencies.get(cardValue) + 1);}
            else {++numJokers;}
        }

        Collections.sort(cardFrequencies);
        Collections.reverse(cardFrequencies);
        int maxFrequency = cardFrequencies.get(0) + numJokers;
        int secondMaxFrequency = cardFrequencies.get(1);
        if (maxFrequency == 5) {return 7;}
        else if (maxFrequency == 4) {return 6;}
        else if (maxFrequency == 3) {
            if (secondMaxFrequency == 2) {return 5;}
            else {return 4;}
        }
        else if (maxFrequency == 2) {
            if (secondMaxFrequency == 2) {return 3;}
            else {return 2;}
        }
        else {return 1;}
    }

    public static long partOne(List<String> lines) {
        List<Long> handValues = new ArrayList<>();
        for (String line: lines) {
            String hand = line.substring(0, 5);
            long handValue = getHandType(hand);
            for (int i = 0; i < hand.length(); ++i) {
                handValue *= 100;
                handValue += cardValueMap.get(hand.charAt(i));
            }
            handValues.add(handValue * 10000 + Integer.parseInt(line.substring(6)));
        }
        handValues.sort(Comparator.naturalOrder());
        return handValues.stream().map(x -> (handValues.indexOf(x) + 1) * (x % 10000)).reduce((long) 0, Long::sum);
    }

    public static long partTwo(List<String> lines) {
        List<Long> handValues = new ArrayList<>();
        for (String line: lines) {
            String hand = line.substring(0, 5);
            long handValue = getHandTypeJokerVariation(hand);
            for (int i = 0; i < hand.length(); ++i) {
                handValue *= 100;
                handValue += cardValueMapJokerVariation.get(hand.charAt(i));
            }
            handValues.add(handValue * 10000 + Integer.parseInt(line.substring(6)));
        }
        handValues.sort(Comparator.naturalOrder());
        return handValues.stream().map(x -> (handValues.indexOf(x) + 1) * (x % 10000)).reduce((long) 0, Long::sum);
    }
    public static void main(String[] args) throws IOException {
        List<String> lines = inputFileReader.fileToStringList("input/day07_in.txt");
        System.out.println("Part 1: " + partOne(lines));
        System.out.println("Part 2: " + partTwo(lines));
    }
}
