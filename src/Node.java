public class Node {
    String value = "";
    Node parent = null;
    Node zero = null;
    Node one = null;
    int depth = 1;
    public Node(String value) {
        this.value = value;
    }


    public Node(String value, int depth, Node parent) {
        this.value = value;
        this.depth = depth;
        this.parent = parent;
    }
}