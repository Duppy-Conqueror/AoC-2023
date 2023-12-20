import java.io.IOException;
import java.util.*;

public class Day05 {

    public static List<Long> parseSeedLine(String seedLine) {
        return Arrays.stream(seedLine.replace("seeds: ", "").split(" ")).map(Long::parseLong).toList();
    }

    public static List<List<Long>> parseMap(List<String> inputMap) {
        return inputMap.stream().map(x -> Arrays.stream(x.split(" ")).map(Long::parseLong).toList()).toList();
    }

    public static Long map(Long input, List<List<Long>> mappings) {
        List<Long> outputs = mappings.stream().map(x -> {
                                if (input < x.get(1) || input > (x.get(1) + x.get(2))) return input;
                                else return x.get(0) + (input - x.get(1));
                            }).filter(x -> !x.equals(input)).toList();
        return outputs.isEmpty() ? input : Collections.min(outputs);
    }

    public static List<List<Long>> mapInterval(List<List<Long>> inputIntervals, List<List<Long>> mappings) {
        System.out.println(inputIntervals);
        List<List<Long>> outputIntervals = new ArrayList<>();

        // Format of mappingScheme: [lowerBound, upperBound
        List<List<Long>> mappingScheme = new ArrayList<>();
        for (List<Long> mapping: mappings) {
            mappingScheme.add(List.of(mapping.get(1), mapping.get(1) + mapping.get(2) - 1));
        }
        // Sort the scheme by lowerBound
        mappingScheme.sort(Comparator.comparingLong(x -> x.get(0)));

//        System.out.println(mappingScheme);
        for (List<Long> inputInterval: inputIntervals) {
            long current = inputInterval.get(0);
            for (List<Long> mappingInterval: mappingScheme) {
                if (current > mappingInterval.get(1)) { continue; }
                else if (!(current < mappingInterval.get(0))) {
                    if (inputInterval.get(1) <= mappingInterval.get(1)) {
                        outputIntervals.add(List.of(current, inputInterval.get(1)));
                        break;
                    }
                    else {
                        outputIntervals.add(List.of(current, mappingInterval.get(1)));
                        current = mappingInterval.get(1) + 1;
                    }
                }
                else {
                    if (inputInterval.get(1) < mappingInterval.get(0)) {
                        outputIntervals.add(List.of(current, inputInterval.get(1)));
                        break;
                    }
                    else {
                        outputIntervals.add(List.of(current, mappingInterval.get(0) - 1));
                        current = mappingInterval.get(0);
                    }
                }
            }
        }
//        System.out.println(outputIntervals);
        return outputIntervals.stream().map(x -> List.of(map(x.get(0), mappings), map(x.get(1), mappings))).toList();
    }

    public static long partOne(List<String> lines) {
        List<Long> seeds = parseSeedLine(lines.get(0));

        List<List<Long>> seedToSoilMap = parseMap(lines.subList(3,23));
        List<List<Long>> soilToFertilizerMap = parseMap(lines.subList(25,53));
        List<List<Long>> fertilizerToWaterMap = parseMap(lines.subList(55,87));
        List<List<Long>> waterToLightMap = parseMap(lines.subList(89,121));
        List<List<Long>> lightToTemperatureMap = parseMap(lines.subList(123,166));
        List<List<Long>> temperatureToHumidityMap = parseMap(lines.subList(168,202));
        List<List<Long>> humidityToLocationMap = parseMap(lines.subList(204,219));

        List<Long> locations = new ArrayList<>();

        for (Long seed: seeds) {
            locations.add(map(map(map(map(map(map(map(seed, seedToSoilMap), soilToFertilizerMap), fertilizerToWaterMap), waterToLightMap),lightToTemperatureMap),temperatureToHumidityMap),humidityToLocationMap));
        }

        return Collections.min(locations);
    }
    public static long partTwo(List<String> lines) {
        List<Long> longValues = parseSeedLine(lines.get(0));
        List<List<Long>> seedIntervals = new ArrayList<>();
        for (int i = 0; i < longValues.size(); i += 2) {
            seedIntervals.add(List.of(longValues.get(i), longValues.get(i) + longValues.get(i+1) - 1));
        }
        List<List<Long>> seedToSoilMap = parseMap(lines.subList(3,23));
        List<List<Long>> soilToFertilizerMap = parseMap(lines.subList(25,53));
        List<List<Long>> fertilizerToWaterMap = parseMap(lines.subList(55,87));
        List<List<Long>> waterToLightMap = parseMap(lines.subList(89,121));
        List<List<Long>> lightToTemperatureMap = parseMap(lines.subList(123,166));
        List<List<Long>> temperatureToHumidityMap = parseMap(lines.subList(168,202));
        List<List<Long>> humidityToLocationMap = parseMap(lines.subList(204,219));

        List<Long> locations = new ArrayList<>();
//        for (List<Long> seedInterval: seedIntervals) {
//            List<List<Long>> mapIntervals = mapInterval(mapInterval(mapInterval(mapInterval(mapInterval(mapInterval(mapInterval(List.of(seedInterval), seedToSoilMap), soilToFertilizerMap),fertilizerToWaterMap),waterToLightMap),lightToTemperatureMap),temperatureToHumidityMap),humidityToLocationMap);
//            List<Long> flattenedMapIntervals = mapIntervals.stream().flatMap(List::stream).toList();
//            locations.add(Collections.min(flattenedMapIntervals));
//        }

        for (List<Long> seedInterval: seedIntervals) {
            System.out.println("Current interval: " + seedInterval);
            Long nominated = null;
            for (long i = seedInterval.get(0); i <= seedInterval.get(1); ++i) {
                Long candidate = map(map(map(map(map(map(map(i, seedToSoilMap), soilToFertilizerMap), fertilizerToWaterMap), waterToLightMap),lightToTemperatureMap),temperatureToHumidityMap),humidityToLocationMap);
                nominated = nominated == null ? candidate : candidate < nominated ? candidate : nominated;
            }
            locations.add(nominated);
        }
        return Collections.min(locations);
    }

    public static void main(String[] args) throws IOException {
        List<String> lines = inputFileReader.fileToStringList("input/day05_in.txt");
        System.out.println("Part 1: " + partOne(lines));
        System.out.println("Part 2: " + partTwo(lines));
    }
}
