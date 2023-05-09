import java.io.CharConversionException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class BDD {
    Node root;
    int valuesAmount;
    int nodesAmount = 0;
    String inputFunction;
    String zeroPart;
    String onePart;


    void bdd_create(String bFunction, String order) {
        this.inputFunction = bFunction;


//        this.shannonDecomposition(bFunction, String.valueOf(order.charAt(0)));
//        this.root.zero = new Node(zeroPart, this.root);
//        this.root.one = new Node(onePart, this.root);
        this.root = bdd_create_rec(bFunction, order, 0);
        this.root.value = inputFunction;
//        this.root = bdd_create_rec(bFunction, order);
//        for (int i = 1; i < order.length(); i++) {
//            insert(root.zero, String.valueOf(order.charAt(0)));
//        }



    // this.valuesAmount = (int) (Math.log(this.inputFunction.length()) / Math.log(2)); // wrong, must take away "+", and count !A as 1
    }

    Node bdd_create_rec(String bFunction, String order, int iteration) {
        if(iteration >= order.length()) return null;
        Node newNode = new Node(bFunction);

        this.shannonDecomposition(bFunction, String.valueOf(order.charAt(iteration)));

        Node zeroNode = new Node(zeroPart, newNode);
        Node oneNode = new Node(onePart, newNode);
        newNode.zero = zeroNode;
        newNode.one = oneNode;

        zeroNode = bdd_create_rec(zeroNode.value, order, iteration+1);

        oneNode.zero = bdd_create_rec(oneNode.value, order, iteration + 1);

        return newNode;
    }
//
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

            System.out.println("1");
            System.out.println("----");
            return;
        } else if (dnf.contains("0")) {
            zeroPart = "0";
            onePart = "0";
            System.out.println("0");
            System.out.println("----");

            return;
        }
        if(dnf.equals(var)) {
            onePart = "1";
            zeroPart = "0";
            System.out.println("One Part: 1");
            System.out.println("Zero Part: 0");
            System.out.println("----");

            return;
        }
        if(dnf.equals("!"+var)) {
            onePart = "0";
            zeroPart = "1";
            System.out.println("One Part: 0");
            System.out.println("Zero Part: 1");
            System.out.println("----");
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
            System.out.println("One Term: "  + oneTerms.substring(0, oneTerms.length() - 1) + "\nZero Term: 0");
            System.out.println("----");

        } else if (oneTerms.equals("")) {
            zeroPart = zeroTerms.substring(0, zeroTerms.length() - 1);
            onePart = "0";
            System.out.println("Zero Term: " + zeroTerms.substring(0, zeroTerms.length() - 1) + "\nOne Term: 0");
            System.out.println("----");

        } else {
            zeroPart = zeroTerms.substring(0, zeroTerms.length() - 1);
            onePart = oneTerms.substring(0, oneTerms.length() - 1);
            System.out.println("Zero Part: " + (zeroTerms.substring(0, zeroTerms.length() - 1)) + " \nOne Part: " + (oneTerms.substring(0, oneTerms.length() - 1)));
            System.out.println("----");

        }

    }

    public static void main(String[] args) {
        BDD bdd = new BDD();
        bdd.bdd_create("AB+AC+BC", "ABC");
//        System.out.println(bdd.shannonDecomposition("AB+AC+BC", "D"));
//        System.out.println(bdd.zeroPart);
//        System.out.println(bdd.onePart);
    }

}
