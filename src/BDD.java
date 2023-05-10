import java.util.ArrayList;
import java.util.Objects;

public class BDD {
    Node root;
    int valuesAmount = 0;
    int nodesAmount = 0;
    int nodesReduced = 0;
    int layersAmount = 0;
    String inputFunction;
    String inputOrder;
    String zeroPart;
    String onePart;


    void bdd_create(String bFunction, String order) {
        order += " ";
        this.inputFunction = bFunction;
        this.inputOrder = order;

        this.root = bdd_create_rec(bFunction, order, 0, null);
        this.root.value = inputFunction;

    }

    Node bdd_create_rec(String bFunction, String order, int iteration, Node parentNode) {
        if(iteration >= order.length()) return null;

        this.layersAmount++;

        Node newNode = new Node(bFunction, iteration + 1, parentNode);

        this.shannonDecomposition(bFunction, String.valueOf(order.charAt(iteration)));

        Node zeroNode = new Node(zeroPart, iteration+1, newNode);
        zeroNode.depth = newNode.depth + 1;
        Node oneNode = new Node(onePart, iteration+1, newNode);
        oneNode.depth = newNode.depth + 1;

        newNode.zero = zeroNode;
        newNode.one = oneNode;


        newNode.zero = bdd_create_rec(newNode.zero.value, order, iteration+1, newNode);

        newNode.one = bdd_create_rec(newNode.one.value, order, iteration + 1, newNode);


        this.nodesAmount += 1;
        return newNode;

    }

    void reduce(int depth) {


        // --- REDUCTION ---
        for(int k = 1; k <= depth; k++) {
            ArrayList<Node> nodesList = getNodesByDepth(this.root, k);

            //      Type I reduction
            for (int i = 0; i < nodesList.size(); i++) {
                if(nodesList.get(i).parent == null) continue;
                for (int j = i+1; j < nodesList.size(); j++) {
                    if (nodesList.get(i) == nodesList.get(j)) continue;
                    if (nodesList.get(i).value.equals(nodesList.get(j).value)) {

                        System.out.println("Type I Reduction was made! ");

                        if (nodesList.get(j).parent.zero.value.equals(nodesList.get(j).value)) {

                            nodesList.get(j).parent.zero = nodesList.get(i);

                        } else if (nodesList.get(j).parent.one.value.equals(nodesList.get(j).value)) {
//                            System.out.println("parent one was changed!");
                            nodesList.get(j).parent.one = nodesList.get(i);

                        }
                        this.nodesReduced++;
                    }
                }
            }

            for(Node node : nodesList) {
                //null checkers
                if (node == null) return;
//          Type S reduction
                if (node.zero == null || node.one == null) continue;
                if (node.zero.value.equals(node.one.value)) {
                    System.out.println("Type S reduction was made");
                    if (node.parent.zero.value.equals(node.value)) {
                        node.parent.zero = node.zero;
                    } else {
                        node.parent.one = node.zero;
                    }
                    this.nodesReduced += 2;
                }
            }
        }

    }

    ArrayList<Node> getNodesByDepth(Node root, int depth) {
        ArrayList<Node> nodes = new ArrayList<>();
        if (root == null) return nodes;

        if (root.depth == depth) {
            nodes.add(root);
        } else {
            nodes.addAll(getNodesByDepth(root.zero, depth));
            nodes.addAll(getNodesByDepth(root.one, depth));
        }
       return nodes;
    }

    private ArrayList<Node> removedDuplicates(ArrayList<Node> nodes) {
        ArrayList<Node> newNodes = new ArrayList<>();
        for (Node thisNode : nodes) {
            if (!newNodes.contains(thisNode)) newNodes.add(thisNode);
        }
        return newNodes;
    }
//    void reduce() {
//
//    }
//    void reduceDuplicates() {
//
//    }
//
//    String use(String input) {
//        return "0";
//    }

    public void shannonDecomposition( String dnf, String var) {

        if(dnf.contains("1")) {
            zeroPart = "1";
            onePart = "1";
            return;
        } else if (dnf.contains("0")) {
            zeroPart = "0";
            onePart = "0";

            return;
        }
        if(dnf.equals(var)) {
            onePart = "1";
            zeroPart = "0";

            return;
        }
        if(dnf.equals("!"+var)) {
            onePart = "0";
            zeroPart = "1";
            return;
        }

        String[] terms = dnf.split("\\+");

        String zeroTerms = "";
        String oneTerms = "";



        for (String term : terms) {
            if (!term.contains(var)) {
                zeroTerms += term + "+";
                oneTerms += term + "+";
            } else {
                if(term.equals(var)) {
                    oneTerms = "1";
                } else if (term.equals("!"+var)) {
                    zeroTerms = "1";
                } else {
                    String subterm = term.replaceAll("[!]*" + var, "");
                    if (!term.contains("!" + var)) {
                        oneTerms += subterm + "+";
                    } else {
                        zeroTerms += subterm + "+";
                    }
                }
            }
        }
        if(zeroTerms.contains("1")) zeroTerms = "1";
        if(oneTerms.contains("1")) oneTerms = "11";

        if (zeroTerms.equals("")) {
            zeroPart = "0";
            onePart = oneTerms.substring(0, oneTerms.length() - 1);
        } else if (oneTerms.equals("")) {
            zeroPart = zeroTerms.substring(0, zeroTerms.length() - 1);
            onePart = "0";
        } else {
            zeroPart = zeroTerms.substring(0, zeroTerms.length() - 1);
            onePart = oneTerms.substring(0, oneTerms.length() - 1);
        }

    }

    public String use(BDD bdd, String value) {
        Node currentNode = bdd.root;
        if(bdd.inputFunction.equals("1") || bdd.inputFunction.equals("0")) System.out.println("1");

        for (int i = 0; i < value.length(); i++) {
            if (value.charAt(i) == '0' && currentNode.zero == null) return "-1";
            else if (value.charAt(i) == '1' && currentNode.one == null) return "-1";
            else if(value.charAt(i) == '0') currentNode = currentNode.zero;
            else if(value.charAt(i) == '1') currentNode = currentNode.one;
        }
        if(currentNode.value.equals("1") || (currentNode.value.equals("0"))) return (currentNode.value);
        else return "-1";

    }

    public String reductionRate() {
        float v = (float) nodesReduced / nodesAmount * 100.0f;
        return String.format("Reduction rate on this BDD: %.2f %%", v);
    }


    void printInorder(Node node)
    {
        if (node == null)

            return;

        /* first recur on left child */
        printInorder(node.zero);

        /* then print the data of node */
        System.out.print(node.value + " ");

        /* now recur on right child */
        printInorder(node.one);
    }

    void print2DUtil(Node root, int space)
    {
        // Base case
        if (root == null)
            return;

        // Increase distance between levels
        space += 20;

        // Process right child first
        print2DUtil(root.one, space);

        // Print current node after space
        // count
        System.out.print("\n");
        for (int i = 20; i < space; i++)
            System.out.print(" ");
        System.out.print(root.value + "\n");

        // Process left child
        print2DUtil(root.zero, space);
    }

    public static void main(String[] args) {
        BDD bdd = new BDD();
        bdd.bdd_create("AB+AC+BC", "ABC");

        bdd.reduce(bdd.layersAmount);

        bdd.print2DUtil(bdd.root, 0);
        System.out.println(bdd.nodesAmount);
        System.out.println(bdd.nodesReduced);
        System.out.println(bdd.reductionRate());






//
//        ArrayList<Node> newNodeList = bdd.getNodesByDepth(bdd.root, 3);
//        System.out.println(newNodeList.size());
//        for(Node node : newNodeList) {
//            System.out.println("Parent: " + node.parent.value + " Code: " + node.parent);
//            System.out.println("Node: " + node.value + " Code: " + node);
//            System.out.println("Zero: " + node.zero.value+ " Code: " + node.zero);
//            System.out.println("One: " + node.one.value+ " Code: " + node.one);
//            System.out.println("---");
//        }
//
//        bdd.print2DUtil(bdd.root, 0);
//        System.out.println(bdd.nodesAmount);
//        bdd.printInorder(bdd.root);
//        System.out.println("\n" + bdd.root.one.zero.zero.value);
//        System.out.println("\n" + bdd.root.zero.one.zero.value);
//        System.out.println("\n" + bdd.root.zero.one.zero.equals(bdd.root.one.zero.zero));


    }

}
