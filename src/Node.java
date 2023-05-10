public class Node {
    String value = "";
    Node parent = null;
    Node zero = null;
    Node one = null;
    int size = 0;
    int depth = 1;
    boolean original;

    public Node(String value) {
        this.value = value;
    }

    public Node(String value, Node parent) {
        this.value = value;
        this.parent = parent;
    }

    public Node(String value, int depth, Node parent) {
        this.value = value;
        this.depth = depth;
        this.parent = parent;
    }

}