import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Lens {
    String label;
    int focalLength;
    public Lens(String label, int focalLength) {
        this.label = label;
        this.focalLength = focalLength;
    }

    @Override
    public String toString() {
        return label + " " + focalLength;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Lens) {
            return ((Lens) obj).label.equals(this.label) && ((Lens) obj).focalLength == this.focalLength;
        }
        return false;
    }
}


public class Day15 {
    public static int hash(String str) {
        int ret = 0;
        for (char ch : str.toCharArray()) {
            ret += ch;
            ret *= 17;
            ret %= 256;
        }
        return ret;
    }

    public static int partOne(List<String> lines) {
        return Arrays.stream(lines.get(0).split(",")).map(Day15::hash).reduce(0, Integer::sum);
    }

    public static int partTwo(List<String> lines) {
        List<List<Lens>> storage = new ArrayList<>();
        for (int i = 0; i < 256; ++i) {
            storage.add(new ArrayList<>());
        }
        for (String instruction : Arrays.stream(lines.get(0).split(",")).toList()) {
            if (instruction.contains("=")) {
                String label = instruction.substring(0, instruction.indexOf('='));
                int boxNumber = hash(label);
                int latestFocalLength = Integer.parseInt(instruction.substring(instruction.indexOf('=') + 1));
                boolean existingLensFound = false;
                for (Lens lens : storage.get(boxNumber)) {
                    if (lens.label.equals(label)) {
                        existingLensFound = true;
                        lens.focalLength = latestFocalLength;
                    }
                }
                if (!existingLensFound) {
                    storage.get(boxNumber).add(new Lens(label, latestFocalLength));
                }
            }
            else {
                String label = instruction.substring(0, instruction.indexOf('-'));
                List<Lens> box = storage.get(hash(label));
                int index = 0;
                while (index < box.size()) {
                    if (box.get(index).label.equals(label)) {
                        box.remove(index);
                        break;
                    }
                    ++index;
                }
            }
        }
        return storage.stream().map(box -> box.stream().map(lens -> (storage.indexOf(box) + 1) * (box.indexOf(lens) + 1) * lens.focalLength).reduce(0, Integer::sum)).reduce(0, Integer::sum);
    }
    public static void main(String[] args) throws IOException {
        List<String> lines = inputFileReader.fileToStringList("input/day15_in.txt");
        System.out.println("Part 1: " + partOne(lines));
        System.out.println("Part 2: " + partTwo(lines));
    }
}
