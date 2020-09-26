import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * This class represents a console.
 *
 * @author Bo Zhang
 * @version 1.0
 */
public class Console {
    public static final String FILE_NAME = "war_and_peace/war_and_peace_(short).txt";

    /**
     * Main method.
     *
     * @param args arguments
     */
    public static void main(String[] args) {
        // generate a ASCII map with the file "ascii_to_binary.txt"
        Map<Character, String> asciiMap = generateASCIIMap("ascii_to_binary.txt");
        //printMap(asciiMap);

        // generate a frequency map of all the characters in the input file
        Map<Character, Double> frequencyMap = getFrequencyMap(FILE_NAME);

        HuffmanNode root = generateHuffmanTree(frequencyMap);
        root.setBinaryNumber("");

        Map<Character, String> huffmanMap = new HashMap<>();
        huffmanMap = generateHuffmanCode(huffmanMap, root);
        printMap(huffmanMap);

        // print a binary output of the input file using ASCII encoding
        printBinary(FILE_NAME, asciiMap, false);
        // print a binary output of the input file using Huffman encoding
        printBinary(FILE_NAME, huffmanMap, true);
    }

    /**
     * Returns a ASCII map based on the designated file. Each key in
     * the map is a character, and each corresponding value is a binary number.
     *
     * @param fileName  the input file name
     * @return asciiMap  a map between characters and binary numbers
     */
    public static Map<Character, String> generateASCIIMap(String fileName) {
        Map<Character, String> asciiMap = new HashMap<>();
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File(fileName));
        } catch (FileNotFoundException exception) {
            System.out.println(exception.getMessage());
        }

        if (scanner != null) {
            while (scanner.hasNextLine()) {
                String nextLine = scanner.nextLine();
                String[] tokens = nextLine.split(Pattern.quote(", "));

                char character = tokens[0].charAt(0);
                String binaryString = tokens[1];

                asciiMap.put(character, binaryString);
            }
        }

        return asciiMap;
    }

    /**
     * Converts each character from the file to its binary ASCII value,
     * prints the binary values to the console and the total number of
     * bits used by the entire file (the total binary digits).
     *
     * @param fileName  the file name
     * @param encodingMap  the encoding map
     * @param useHuffman  to use Huffman encoding or not
     */
    public static void printBinary(String fileName, Map<Character, String> encodingMap, boolean useHuffman) {
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File(fileName));
        } catch (FileNotFoundException exception) {
            System.out.println(exception.getMessage());
        }
        StringBuilder output = new StringBuilder();

        int bitsUsedCounter = 0;
        if (scanner != null) {
            while (scanner.hasNextLine()) {
                String nextLine = scanner.nextLine();
                StringBuilder asciiLine = new StringBuilder();
                for (int i = 0; i < nextLine.length(); i++) {
                    char character = Character.toLowerCase(nextLine.charAt(i));
                    String binaryString = encodingMap.get(character);
                    asciiLine.append(binaryString);
                    bitsUsedCounter += binaryString.length();
                }
                output.append(asciiLine).append("\n");
            }
        }

        if (useHuffman) {
            System.out.println("Huffman Output\n" + output + "Total bits used: " + bitsUsedCounter + "\n");
        } else {
            System.out.println("ASCII Output\n" + output + "Total bits used: " + bitsUsedCounter + "\n");
        }
    }

    /**
     * Returns the frequency map from the file.
     *
     * @param fileName  the file name
     * @return hash map
     */
    public static Map<Character, Double> getFrequencyMap(String fileName) {
        Map<Character, Double> frequencyMap = new HashMap<>();
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File(fileName));
        } catch (FileNotFoundException exception) {
            System.out.println(exception.getMessage());
        }

        int totalCharCounter = 0;
        if (scanner != null) {
            while (scanner.hasNextLine()) {
                String nextLine = scanner.nextLine();
                totalCharCounter += nextLine.length();

                for (int i = 0; i < nextLine.length(); i++) {
                    char character = Character.toLowerCase(nextLine.charAt(i));
                    Double frequency = frequencyMap.get(character);
                    frequencyMap.put(character, frequency != null ? frequency + 1.0 : 1.0);
                }
            }
        }

        double totalFrequency = 0.0;
        for (char character : frequencyMap.keySet()) {
            double frequency = frequencyMap.get(character);
            double frequencyPercentage = frequency / totalCharCounter;
            totalFrequency += frequencyPercentage;
            frequencyMap.put(character, frequencyPercentage);
        }

        System.out.println("Frequencies");
        for (Character character : frequencyMap.keySet()) {
            System.out.println(character + ": " + frequencyMap.get(character));
        }
        System.out.println("Root frequency: " + totalFrequency + "\n");

        return frequencyMap;
    }

    /**
     * The algorithm performs the following steps repeatedly until the heap has only one element:
     * Remove the two lowest frequency nodes from the heap
     * Create a new parent node for the two nodes removed from the heap.
     * The parent node stores no character. You can represent this by storing the null character: '\u0000'
     * The parent node's frequency is the sum of both of the child node frequencies
     * The new parent node is then put back on the heap

     * @param frequencyMap  the frequency map
     * @return HuffmanNode  the root node
     */
    public static HuffmanNode generateHuffmanTree(Map<Character, Double> frequencyMap) {
        FourWayHeap<HuffmanNode> heap = new FourWayHeap<>();
        for (char character : frequencyMap.keySet()) {
            HuffmanNode node = new HuffmanNode(character, frequencyMap.get(character), null, null);
            heap.insert(node);
        }

        // while there are 2 or more nodes in the heap,
        // take the minimum two and create a parent node,
        // then put the parent node back to the heap
        while (heap.size() > 1) {
            HuffmanNode node1 = heap.deleteMin();
            HuffmanNode node2 = heap.deleteMin();

            double parentProbability = node1.getProbability() + node2.getProbability();
            HuffmanNode parentNode = new HuffmanNode('\u0000', parentProbability, node1, node2);
            heap.insert(parentNode);
        }

        // by now there is only one element in the heap, which is the root
        return heap.peek();
    }

    /**
     * Returns a map which keys are characters and the corresponding values
     * are binary strings generated by Huffman encoding algorithm.
     *
     * Note: this could be done in a short recursive method.
     *
     * @param map  the Huffman map
     * @param parentNode  the parent node
     * @return a map
     */
    public static Map<Character, String> generateHuffmanCode(Map<Character, String> map, HuffmanNode parentNode) {
        if (parentNode.getLeft() == null && parentNode.getRight() == null) {
            return map;
        }

        HuffmanNode leftChildNode = parentNode.getLeft();
        leftChildNode.setBinaryNumber(parentNode.getBinaryNumber() + "1");
        if (leftChildNode.getData() != '\u0000') {
            map.put(leftChildNode.getData(), leftChildNode.getBinaryNumber());
        }

        HuffmanNode rightChildNode = parentNode.getRight();
        rightChildNode.setBinaryNumber(parentNode.getBinaryNumber() + "0");
        if (rightChildNode.getData() != '\u0000') {
            map.put(rightChildNode.getData(), rightChildNode.getBinaryNumber());
        }

        generateHuffmanCode(map, leftChildNode);
        generateHuffmanCode(map, rightChildNode);

        return map;
    }

    /**
     * Prints the map which keys are characters and values are binary strings.
     *
     * @param map  a map between chars and strings
     */
    public static void printMap(Map<Character, String> map) {
        int totalBits = 0;
        for (Character key : map.keySet()) {
            String binaryCode = map.get(key);
            System.out.println(key + ": " + binaryCode);
            totalBits += binaryCode.length();
        }
        System.out.println("Total bits: " + totalBits + "\n");
    }
}
