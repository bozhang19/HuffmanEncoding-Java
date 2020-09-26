/**
 * This class represents a Huffman node.
 *
 * @author Bo Zhang
 * @version 1.0
 */
public class HuffmanNode implements Comparable<HuffmanNode> {
    private char data;
    private double probability;
    private HuffmanNode left;
    private HuffmanNode right;
    private String binaryNumber;

    public HuffmanNode(char data, double probability, HuffmanNode left, HuffmanNode right) {
        this.data = data;
        this.probability = probability;
        this.left = left;
        this.right = right;
    }

    public char getData() {
        return data;
    }

    public void setData(char data) {
        this.data = data;
    }

    public double getProbability() {
        return probability;
    }

    public void setProbability(double probability) {
        this.probability = probability;
    }

    public HuffmanNode getLeft() {
        return left;
    }

    public void setLeft(HuffmanNode left) {
        this.left = left;
    }

    public HuffmanNode getRight() {
        return right;
    }

    public void setRight(HuffmanNode right) {
        this.right = right;
    }

    public String getBinaryNumber() {
        return binaryNumber;
    }

    public void setBinaryNumber(String binaryNumber) {
        this.binaryNumber = binaryNumber;
    }

    @Override
    public int compareTo(HuffmanNode other) {
        return Double.compare(this.probability, other.probability);
    }

    @Override
    public String toString() {
        return "HuffmanNode{" +
                "data=" + data +
                ", probability=" + probability +
                //", left=" + left +
                //", right=" + right +
                ", binary=" + binaryNumber +
                '}';
    }
}
