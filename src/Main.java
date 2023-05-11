import java.util.*;

import static java.lang.System.exit;

public class Main {

    static String generateBooleanFunction( String order) {
        String bFunction;
        ArrayList<String> bfunctions = new ArrayList<String>();

        order = order.toUpperCase();

        //amount of expressions
        for(int i = 0; i < order.length(); i ++) {
            String expression = "";

            for(int j = 0; j <  order.length(); j++) {

                String letterToAdd = String.valueOf(order.charAt(j));
                int isAdding = (int)(Math.random()*(1+1));
                if(isAdding == 1) {
                    expression += letterToAdd;
                }
            }
            bfunctions.add(expression);
        }
        bfunctions.removeAll(Arrays.asList("", null));
        bFunction = String.join("+", bfunctions);

        return bFunction;

    }

    //user interface
    static void checkBDDs() {
        BDD bdd = new BDD();
        String bddFunction = "";
        String argumentsForBDD = "";

        Scanner scanner = new Scanner(System.in);
        String userInput;

        System.out.println("What do you want to do?: ");
        System.out.println("1: Create BDD with your order");
        System.out.println("2: Create BDD with amount of variables (order will be shuffled)");
        System.out.println("3: Create BDD with your function and order");

        userInput = scanner.nextLine();
        while (!userInput.equals("1") && !userInput.equals("2") && !userInput.equals("3") && !userInput.equals("e")) {
            System.out.println("Wrong input!");
            System.out.println("What do you want to do?: ");
            System.out.println("1: Create BDD with your order");
            System.out.println("2: Create BDD with amount of variables (order will be shuffled)");
            System.out.println("3: Create BDD with your function and order");
            userInput = scanner.nextLine();
        }

        if(userInput.equals("1")) {
            //creating a BDD
            System.out.println("For creating a BDD, please pass an order in which you want to generate it. The boolean function will be generated automatically.");
            userInput = scanner.nextLine();

            //creating a bdd
            bddFunction = generateBooleanFunction(userInput);
            bdd.bdd_create(bddFunction, userInput);


            System.out.println("Your BDD was successfully created!");
            System.out.println("Boolean function: " + bdd.inputFunction);
            System.out.println("Amount of Variables: " + bdd.amountOfVariables);
            System.out.println(bdd.reductionRate());
            System.out.println("------------------");
        } else if (userInput.equals("2")) {
            String localOrder = "";
            //creating a BDD
            System.out.println("Please pass amount of variables, order will be generated automatically");
            userInput = scanner.nextLine();


            ArrayList<Integer> listOfLetters = generateOrder(Integer.parseInt(userInput));

            shuffleList(listOfLetters); //shuffling Order

            for(int number : listOfLetters) {
                char letter = (char) number;
                localOrder += letter;
            }

            //creating a bdd
            bddFunction = generateBooleanFunction(localOrder);
            bdd.bdd_create(bddFunction, localOrder);

            System.out.println("Your BDD was successfully created!");
            System.out.println("Boolean function: " + bdd.inputFunction);
            System.out.println("Amount of Variables: " + bdd.amountOfVariables);
            System.out.println(bdd.reductionRate());
            System.out.println("------------------");
        } else if (userInput.equals("3")) {
            String localOrder = "";

            //creating a BDD
            System.out.println("Please pass the boolean function in DNF format:");
            bddFunction = scanner.nextLine();
            System.out.println("Please pass the Order you want the BDD to be constructed (order must me contain only letters from the function)");
            localOrder = scanner.nextLine();

            //creating a bdd
            bdd.bdd_create(bddFunction, localOrder);

            System.out.println("Your BDD was successfully created!");
            System.out.println("Boolean function: " + bdd.inputFunction);
            System.out.println("Amount of Variables: " + bdd.amountOfVariables);
            System.out.println(bdd.reductionRate());
            System.out.println("------------------");
        }



        while(!userInput.equals("e")) {
            System.out.println("to exit, press: 'e'");
            System.out.println("What do you want to do next?");
            System.out.println("1: use BDD");
            System.out.println("2: same logic function but in different order");
            System.out.println("3: generate new BDD");

            userInput = scanner.nextLine();
            while (!userInput.equals("1") && !userInput.equals("2") && !userInput.equals("3") && !userInput.equals("e")) {
                System.out.println("Wrong input!");
                System.out.println("What do you want to do next?");
                System.out.println("1: use BDD");
                System.out.println("2: same logic function but in different order");
                System.out.println("3: generate new BDD");
                userInput = scanner.nextLine();
            }

            if(userInput.equals("1")) {
                System.out.println("Please enter combination you want to test in form '0110...");
                userInput = scanner.nextLine();
                System.out.println("Result of you function in current BDD is: " + bdd.use(bdd, userInput));
                System.out.println("-----\n");

            } else if (userInput.equals("2")) {
                System.out.println("Boolean function: " + bdd.inputFunction);
                System.out.println("Amount of Variables: " + bdd.amountOfVariables);
                System.out.println("Please write new order: ");
                userInput = scanner.nextLine();
                bdd.bdd_create(bddFunction, userInput);

                System.out.println("New BDD was constructed!");
                System.out.println(bdd.reductionRate());
                System.out.println("-----\n");

            } else if(userInput.equals("3")) {

                System.out.println("1: Create BDD with your order");
                System.out.println("2: Create BDD with amount of variables (order will be shuffled)");
                System.out.println("3: Create BDD with your function and order");

                userInput = scanner.nextLine();
                while (!userInput.equals("1") && !userInput.equals("2") && !userInput.equals("3") && !userInput.equals("e")) {
                    System.out.println("Wrong input!");
                    System.out.println("What do you want to do?: ");
                    System.out.println("1: Create BDD with your order");
                    System.out.println("2: Create BDD with amount of variables (order will be shuffled)");
                    System.out.println("3: Create BDD with your function and order");
                    userInput = scanner.nextLine();
                }

                if(userInput.equals("1")) {
                    //creating a BDD
                    System.out.println("For creating a BDD, please pass an order in which you want to generate it. The boolean function will be generated automatically.");
                    userInput = scanner.nextLine();

                    //creating a bdd
                    bddFunction = generateBooleanFunction(userInput);
                    bdd.bdd_create(bddFunction, userInput);


                    System.out.println("Your BDD was successfully created!");
                    System.out.println("Boolean function: " + bdd.inputFunction);
                    System.out.println("Amount of Variables: " + bdd.amountOfVariables);
                    System.out.println(bdd.reductionRate());
                    System.out.println("------------------");
                } else if (userInput.equals("2")) {
                    String localOrder = "";
                    //creating a BDD
                    System.out.println("Please pass amount of variables, order will be generated automatically");
                    userInput = scanner.nextLine();


                    ArrayList<Integer> listOfLetters = generateOrder(Integer.parseInt(userInput));

                    shuffleList(listOfLetters); //shuffling Order

                    for(int number : listOfLetters) {
                        char letter = (char) number;
                        localOrder += letter;
                    }

                    //creating a bdd
                    bddFunction = generateBooleanFunction(localOrder);
                    bdd.bdd_create(bddFunction, localOrder);

                    System.out.println("Your BDD was successfully created!");
                    System.out.println("Boolean function: " + bdd.inputFunction);
                    System.out.println("Amount of Variables: " + bdd.amountOfVariables);
                    System.out.println(bdd.reductionRate());
                    System.out.println("------------------");
                } else if (userInput.equals("3")) {
                    String localOrder = "";

                    //creating a BDD
                    System.out.println("Please pass the boolean function in DNF format:");
                    bddFunction = scanner.nextLine();
                    System.out.println("Please pass the Order you want the BDD to be constructed (order must me contain only letters from the function)");
                    localOrder = scanner.nextLine();

                    //creating a bdd
                    bdd.bdd_create(bddFunction, localOrder);

                    System.out.println("Your BDD was successfully created!");
                    System.out.println("Boolean function: " + bdd.inputFunction);
                    System.out.println("Amount of Variables: " + bdd.amountOfVariables);
                    System.out.println(bdd.reductionRate());
                    System.out.println("------------------");
                }

            } else if(userInput.equals("e")) {
                exit(1);
            }
        }
    }

    static ArrayList<Integer> generateOrder(int length) {
        ArrayList<Integer> asciiLetters = new ArrayList<>();
        for(int i = 0; i < length; i++) {
            asciiLetters.add(65+i);
        }

        return asciiLetters;
    }

    static void shuffleList(ArrayList<Integer> list) {
        Collections.shuffle(list, new Random());
    }

    public static void main(String[] args) {
        checkBDDs();
    }
}