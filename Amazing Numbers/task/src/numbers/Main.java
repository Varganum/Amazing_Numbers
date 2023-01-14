package numbers;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    static final String[] INSTRUCTIONS_TEXT = {
            "\nSupported requests:",
            "- enter a natural number to know its properties;",
            "- enter two natural numbers to obtain the properties of the list:",
            "  * the first parameter represents a starting number;",
            "  * the second parameter shows how many consecutive numbers are to be processed;",
            "- two natural numbers and properties to search for;",
            "- a property preceded by minus must not be present in numbers;",
            "- separate the parameters with one space;",
            "- enter 0 to exit."
    };

    static final String[] MESSAGES = {
            "\nEnter a request: ",
            "\nThe first parameter should be a natural number or zero.",
            "\nThe second parameter should be a natural number.",
            "\nGoodbye!"
    };

    /* Used on stages 1-3 of project Amazing numbers.

    static final String[] MESSAGES_FOR_BUZZ_CHECK = {
            "It is a Buzz number.\nExplanation:",
            "It is not a Buzz number.\nExplanation:",
            " is divisible by 7 and ends with 7.",
            " is divisible by 7.",
            " ends with 7.",
            " is neither divisible by 7 nor does it end with 7."
    };
    */

    static final String[] MESSAGES_FOR_PROPERTIES = {
            "Properties of ",
            "        even: ",
            "         odd: ",
            "        buzz: ",
            "        duck: ",
            " palindromic: ",
            "      gapful: ",
            "         spy: ",
            "      square: ",
            "       sunny: ",
            "     jumping: ",
            "       happy: ",
            "         sad: "

    };

    static final int QUANTITY_OF_NUMBER_PROPERTIES = MESSAGES_FOR_PROPERTIES.length - 1;

    static final ArrayList<numberProperties> userPropertySet = new ArrayList<>();

    public static void main(String[] args) {
        //write your code here
        boolean exit = false;
        long[] userInput;


        Scanner scanner = new Scanner(System.in);

        System.out.print("Welcome to Amazing Numbers!\n");
        printInstructions();

        while (!exit) {
            userInput = acceptUserInput(scanner);

            /* Line accepted user input on stages 1-3 of the project Amazing numbers.
            long numberEntered = acceptUserNaturalNumber(scanner);
             */

            if (userInput[0] == 0) {
                exit = true;
                System.out.println(Main.MESSAGES[3]);
            } else {
                //further program
                if (userInput[1] == 0) {
                    printNumberProperties(userInput[0]);
                } else if (userPropertySet.isEmpty()) {
                //checking a sequence of numbers without particular properties
                    for (long i = 0; i < userInput[1]; i++) {
                        printNumberPropertiesInOneLine(userInput[0] + i);
                    }
                    System.out.println();
                } else {
                    //looking for numbers with particular properties saved in ArrayList userPropertySet
                    int counter = 0; //for counting quantity of found numbers with particular property
                    boolean searchFinished = false;
                    long currentNumber = userInput[0];
                    long nextNumber;

                    while (counter < userInput[1] && !searchFinished) {
                        nextNumber = findNextNumberWithProperty(currentNumber, userPropertySet.get(0));
                        if (nextNumber == -1) {
                            searchFinished = true;
                        } else {
                            if (checkNumberForPropertySet(nextNumber)) {
                                counter++;
                                printNumberPropertiesInOneLine(nextNumber);
                            }
                            currentNumber = nextNumber + 1;
                        }
                    }
                    System.out.println();
                }
            }
        }
    }

    /* *****************METHODS SECTION********************** */

    static long[] acceptUserInput(Scanner scanner) {
        boolean inputAccepted = false;
        String inputString;
        String[] inputWords;
        long[] twoNaturalNumbers = new long[2];

        while (!inputAccepted) {
            System.out.print(Main.MESSAGES[0]);
            inputString = scanner.nextLine();

            if (inputString.isEmpty()) {
                printInstructions();
            } else {
                inputWords = inputString.split(" ");

                if (inputWords.length == 1) {

                    try {
                        twoNaturalNumbers[0] = Long.parseLong(inputWords[0]);
                        inputAccepted = checkFirstArgument(twoNaturalNumbers[0]);
                    } catch (NumberFormatException e) {
                        System.out.println(Main.MESSAGES[1]);
                    }
                    twoNaturalNumbers[1] = 0;

                } else if (inputWords.length == 2) {

                    try {
                        twoNaturalNumbers[0] = Long.parseLong(inputWords[0]);
                        try {
                            twoNaturalNumbers[1] = Long.parseLong(inputWords[1]);
                            inputAccepted = checkFirstArgument(twoNaturalNumbers[0])
                                    & checkSecondArgument(twoNaturalNumbers[1]);
                        } catch (NumberFormatException e) {
                            System.out.println(Main.MESSAGES[2]);
                        }
                    } catch (NumberFormatException e) {
                        System.out.println(Main.MESSAGES[1]);
                    }

                }
                /* Below is the part from version of the program up to 6th stage of the project Amazing numbers.

                else if (inputWords.length == 3) {
                    try {
                        twoNaturalNumbers[0] = Long.parseLong(inputWords[0]);
                        try {
                            twoNaturalNumbers[1] = Long.parseLong(inputWords[1]);
                            setFilterByProperty(inputWords[2].toLowerCase());
                            inputAccepted = checkFirstArgument(twoNaturalNumbers[0]) & checkSecondArgument(twoNaturalNumbers[1])
                                    & checkFilterByProperty(inputWords[2]);
                        } catch (NumberFormatException e) {
                            System.out.println(Main.MESSAGES[2]);
                        }
                    } catch (NumberFormatException e) {
                        System.out.println(Main.MESSAGES[1]);
                    }

                } else if (inputWords.length == 4) {
                    try {
                        twoNaturalNumbers[0] = Long.parseLong(inputWords[0]);
                        try {
                            twoNaturalNumbers[1] = Long.parseLong(inputWords[1]);
                            setFilterByProperty(inputWords[2].toLowerCase());
                            setFilter2ByProperty(inputWords[3].toLowerCase());

                            inputAccepted = checkFirstArgument(twoNaturalNumbers[0])
                                    && checkSecondArgument(twoNaturalNumbers[1])
                                    && checkBothFiltersByProperty(inputWords[2], inputWords[3])
                                    && checkPropertiesCompatibility();
                        } catch (NumberFormatException e) {
                            System.out.println(Main.MESSAGES[2]);
                        }
                    } catch (NumberFormatException e) {
                        System.out.println(Main.MESSAGES[1]);
                    }
                }
                */

                else {
                    //case with arbitrary set or number properties
                    //first checking the correctness of two first numeric arguments
                    try {
                        twoNaturalNumbers[0] = Long.parseLong(inputWords[0]);
                        try {
                            twoNaturalNumbers[1] = Long.parseLong(inputWords[1]);
                            inputAccepted = checkFirstArgument(twoNaturalNumbers[0])
                                    & checkSecondArgument(twoNaturalNumbers[1])
                                    & checkProperties(inputWords);
                        } catch (NumberFormatException e) {
                            System.out.println(Main.MESSAGES[2]);
                        }
                    } catch (NumberFormatException e) {
                        System.out.println(Main.MESSAGES[1]);
                    }
                }
            }

        }

        return twoNaturalNumbers;
    }

    private static boolean checkFirstArgument(long number) {
        boolean result = false;
        if (number >= 0) {
            result = true;
        } else {
            System.out.println(Main.MESSAGES[1]);
        }
        return result;
    }

    private static boolean checkSecondArgument(long number) {
        boolean result = false;
        if (number > 0) {
            result = true;
        } else {
            System.out.println(Main.MESSAGES[2]);
        }
        return result;
    }

    private static boolean checkProperties(String[] userPropertiesInput) {
        boolean result = false;
        //delete all previous numberProperties from ArrayList userPropertySet
        userPropertySet.clear();
        //adding number properties entered by user to ArrayList userPropertySet
        numberProperties currentProperty;
        ArrayList<String> userMistakes = new ArrayList<>();

        for (int i = 2; i < userPropertiesInput.length; i++) {
            currentProperty = numberProperties.findByPropertyTitle(userPropertiesInput[i].toLowerCase());
            if (currentProperty == null) {
                userMistakes.add(userPropertiesInput[i]);
            } else {
                userPropertySet.add(currentProperty);
            }
        }
        //check if there are mistakes in set of properties entered by user
        if (userMistakes.isEmpty()) {
            result = checkPropertySetCompatibility() && checkQuantityOfProperties();
        } else if (userMistakes.size() == 1) {
            System.out.println("The property [" + userMistakes.get(0).toUpperCase() + "] is wrong.");
            numberProperties.printAvailableProperties();
        } else {
            System.out.print("The properties [");
            for (int i = 0; i < userMistakes.size(); i++) {
                System.out.print(userMistakes.get(i).toUpperCase());
                if (i < userMistakes.size() - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println("] are wrong.");
            numberProperties.printAvailableProperties();
        }

        return result;
    }

    private static boolean checkPropertySetCompatibility() {
        boolean result = true;
        if (userPropertySet.contains(numberProperties.EVEN) & userPropertySet.contains(numberProperties.ODD)) {
            System.out.println("\nThe request contains mutually exclusive properties: [" +
                    numberProperties.EVEN.name() +", " + numberProperties.ODD.name() + "]\n" +
                    "There are no numbers with these properties.");
            result = false;
        } else if (userPropertySet.contains(numberProperties.EVEN_MINUS) & userPropertySet.contains(numberProperties.ODD_MINUS)) {
            System.out.println("\nThe request contains mutually exclusive properties: [-" +
                    numberProperties.EVEN.name() +", -" + numberProperties.ODD.name() + "]\n" +
                    "There are no numbers with these properties.");
            result = false;
        } else if (userPropertySet.contains(numberProperties.DUCK) & userPropertySet.contains(numberProperties.SPY)) {
            System.out.println("\nThe request contains mutually exclusive properties: [" +
                    numberProperties.DUCK.name() +", " + numberProperties.SPY.name() + "]\n" +
                    "There are no numbers with these properties.");
            result = false;
        } else if (userPropertySet.contains(numberProperties.DUCK_MINUS) & userPropertySet.contains(numberProperties.SPY_MINUS)) {
            System.out.println("\nThe request contains mutually exclusive properties: [-" +
                    numberProperties.DUCK.name() +", -" + numberProperties.SPY.name() + "]\n" +
                    "There are no numbers with these properties.");
            result = false;
        } else if (userPropertySet.contains(numberProperties.SUNNY) & userPropertySet.contains(numberProperties.SQUARE)) {
            System.out.println("\nThe request contains mutually exclusive properties: [" +
                    numberProperties.SUNNY.name() +", " + numberProperties.SQUARE.name() + "]\n" +
                    "There are no numbers with these properties.");
            result = false;
        } else if (userPropertySet.contains(numberProperties.SUNNY_MINUS) & userPropertySet.contains(numberProperties.SQUARE_MINUS)) {
            System.out.println("\nThe request contains mutually exclusive properties: [-" +
                    numberProperties.SUNNY.name() +", -" + numberProperties.SQUARE.name() + "]\n" +
                    "There are no numbers with these properties.");
            result = false;
        } else if (userPropertySet.contains(numberProperties.HAPPY) & userPropertySet.contains(numberProperties.SAD)) {
            System.out.println("\nThe request contains mutually exclusive properties: [" +
                    numberProperties.HAPPY.name() +", " + numberProperties.SAD.name() + "]\n" +
                    "There are no numbers with these properties.");
            result = false;
        } else if (userPropertySet.contains(numberProperties.HAPPY_MINUS) & userPropertySet.contains(numberProperties.SAD_MINUS)) {
            System.out.println("\nThe request contains mutually exclusive properties: [-" +
                    numberProperties.HAPPY.name() +", -" + numberProperties.SAD.name() + "]\n" +
                    "There are no numbers with these properties.");
            result = false;
        } else if (userPropertySet.contains(numberProperties.BUZZ) & userPropertySet.contains(numberProperties.BUZZ_MINUS)) {
            System.out.println("\nThe request contains mutually exclusive properties: [" +
                    numberProperties.BUZZ.name() +", -" + numberProperties.BUZZ.name() + "]\n" +
                    "There are no numbers with these properties.");
            result = false;
        } else if (userPropertySet.contains(numberProperties.DUCK) & userPropertySet.contains(numberProperties.DUCK_MINUS)) {
            System.out.println("\nThe request contains mutually exclusive properties: [" +
                    numberProperties.DUCK.name() +", -" + numberProperties.DUCK.name() + "]\n" +
                    "There are no numbers with these properties.");
            result = false;
        } else if (userPropertySet.contains(numberProperties.PALINDROMIC) & userPropertySet.contains(numberProperties.PALINDROMIC_MINUS)) {
            System.out.println("\nThe request contains mutually exclusive properties: [" +
                    numberProperties.PALINDROMIC.name() +", -" + numberProperties.PALINDROMIC.name() + "]\n" +
                    "There are no numbers with these properties.");
            result = false;
        } else if (userPropertySet.contains(numberProperties.GAPFUL) & userPropertySet.contains(numberProperties.GAPFUL_MINUS)) {
            System.out.println("\nThe request contains mutually exclusive properties: [" +
                    numberProperties.GAPFUL.name() +", -" + numberProperties.GAPFUL.name() + "]\n" +
                    "There are no numbers with these properties.");
            result = false;
        } else if (userPropertySet.contains(numberProperties.SPY) & userPropertySet.contains(numberProperties.SPY_MINUS)) {
            System.out.println("\nThe request contains mutually exclusive properties: [" +
                    numberProperties.SPY.name() +", -" + numberProperties.SPY.name() + "]\n" +
                    "There are no numbers with these properties.");
            result = false;
        } else if (userPropertySet.contains(numberProperties.SQUARE) & userPropertySet.contains(numberProperties.SQUARE_MINUS)) {
            System.out.println("\nThe request contains mutually exclusive properties: [" +
                    numberProperties.SQUARE.name() +", -" + numberProperties.SQUARE.name() + "]\n" +
                    "There are no numbers with these properties.");
            result = false;
        } else if (userPropertySet.contains(numberProperties.SUNNY) & userPropertySet.contains(numberProperties.SUNNY_MINUS)) {
            System.out.println("\nThe request contains mutually exclusive properties: [" +
                    numberProperties.SUNNY.name() +", -" + numberProperties.SUNNY.name() + "]\n" +
                    "There are no numbers with these properties.");
            result = false;
        } else if (userPropertySet.contains(numberProperties.JUMPING) & userPropertySet.contains(numberProperties.JUMPING_MINUS)) {
            System.out.println("\nThe request contains mutually exclusive properties: [" +
                    numberProperties.JUMPING.name() +", -" + numberProperties.JUMPING.name() + "]\n" +
                    "There are no numbers with these properties.");
            result = false;
        } else if (userPropertySet.contains(numberProperties.HAPPY) & userPropertySet.contains(numberProperties.HAPPY_MINUS)) {
            System.out.println("\nThe request contains mutually exclusive properties: [" +
                    numberProperties.HAPPY.name() +", -" + numberProperties.HAPPY.name() + "]\n" +
                    "There are no numbers with these properties.");
            result = false;
        } else if (userPropertySet.contains(numberProperties.SAD) & userPropertySet.contains(numberProperties.SAD_MINUS)) {
            System.out.println("\nThe request contains mutually exclusive properties: [" +
                    numberProperties.SAD.name() +", -" + numberProperties.SAD.name() + "]\n" +
                    "There are no numbers with these properties.");
            result = false;
        } else if (userPropertySet.contains(numberProperties.EVEN) & userPropertySet.contains(numberProperties.EVEN_MINUS)) {
            System.out.println("\nThe request contains mutually exclusive properties: [" +
                    numberProperties.EVEN.name() +", -" + numberProperties.EVEN.name() + "]\n" +
                    "There are no numbers with these properties.");
            result = false;
        } else if (userPropertySet.contains(numberProperties.ODD) & userPropertySet.contains(numberProperties.ODD_MINUS)) {
            System.out.println("\nThe request contains mutually exclusive properties: [" +
                    numberProperties.ODD.name() +", -" + numberProperties.ODD.name() + "]\n" +
                    "There are no numbers with these properties.");
            result = false;
        }
        return result;
    }

    private static boolean checkQuantityOfProperties() {
        boolean result = true;

        if (userPropertySet.size() > QUANTITY_OF_NUMBER_PROPERTIES - 4) {
            result = false;
            System.out.println("\nToo many arguments! Press Enter to read instructions.");
        }

        return result;
    }

    private static long findNextNumberWithProperty(long startNumber, numberProperties property) {
        //looking for number with particular property starting from startNumber
        long result;
        long currentNumber = startNumber;
        //System.out.println("/nStarted search for number with property: " + property.name());
        switch (property) {
            case BUZZ        -> {while (!isBuzz(currentNumber)) {currentNumber++;}}
            case DUCK        -> {while (!isDuck(currentNumber)) {currentNumber++;}}
            case PALINDROMIC -> {while (!isPalindromic(currentNumber)) {currentNumber++;}}
            case GAPFUL      -> {while (!isGapful(currentNumber)) {currentNumber++;}}
            case SPY         -> {while (!isSpy(currentNumber)) {currentNumber++;}}
            case SQUARE      -> {while (!isSquare(currentNumber)) {currentNumber++;}}
            case SUNNY       -> {while (!isSunny(currentNumber)) {currentNumber++;}}
            case JUMPING     -> {while (!isJumping(currentNumber)) {currentNumber++;}}
            case HAPPY, SAD_MINUS -> {while (!isHappy(currentNumber)) {currentNumber++;}}
            case SAD, HAPPY_MINUS -> {while (isHappy(currentNumber)) {currentNumber++;}}
            case EVEN, ODD_MINUS -> {while (!isEven(currentNumber)) {currentNumber++;}}
            case ODD, EVEN_MINUS -> {while (isEven(currentNumber)) {currentNumber++;}}
            case BUZZ_MINUS        -> {while (isBuzz(currentNumber)) {currentNumber++;}}
            case DUCK_MINUS        -> {while (isDuck(currentNumber)) {currentNumber++;}}
            case PALINDROMIC_MINUS -> {while (isPalindromic(currentNumber)) {currentNumber++;}}
            case GAPFUL_MINUS      -> {while (isGapful(currentNumber)) {currentNumber++;}}
            case SPY_MINUS         -> {while (isSpy(currentNumber)) {currentNumber++;}}
            case SQUARE_MINUS      -> {while (isSquare(currentNumber)) {currentNumber++;}}
            case SUNNY_MINUS       -> {while (isSunny(currentNumber)) {currentNumber++;}}
            case JUMPING_MINUS     -> {while (isJumping(currentNumber)) {currentNumber++;}}
            default                -> System.out.println("I'm in 405th line");
        }
        result = currentNumber;
        return result;
    }

    private static boolean checkNumberForPropertySet(long number) {
        boolean result = true;
        for (numberProperties property : userPropertySet) {
            if (!checkNumberForProperty(number, property)) {
                result = false;
                break;
            }
        }
        return result;
    }

    private static boolean checkNumberForProperty(long number, numberProperties property) {
        switch (property) {
            case BUZZ -> {
                return isBuzz(number);
            }
            case BUZZ_MINUS -> {
                return !isBuzz(number);
            }
            case DUCK -> {
                return isDuck(number);
            }
            case DUCK_MINUS -> {
                return !isDuck(number);
            }
            case PALINDROMIC -> {
                return isPalindromic(number);
            }
            case PALINDROMIC_MINUS -> {
                return !isPalindromic(number);
            }
            case GAPFUL -> {
                return isGapful(number);
            }
            case GAPFUL_MINUS -> {
                return !isGapful(number);
            }
            case SPY -> {
                return isSpy(number);
            }
            case SPY_MINUS -> {
                return !isSpy(number);
            }
            case SQUARE -> {
                return isSquare(number);
            }
            case SQUARE_MINUS -> {
                return !isSquare(number);
            }
            case SUNNY -> {
                return isSunny(number);
            }
            case SUNNY_MINUS -> {
                return !isSunny(number);
            }
            case JUMPING -> {
                return isJumping(number);
            }
            case JUMPING_MINUS -> {
                return !isJumping(number);
            }
            case HAPPY, SAD_MINUS -> {
                return isHappy(number);
            }
            case HAPPY_MINUS, SAD -> {
                return !isHappy(number);
            }
            case EVEN, ODD_MINUS -> {
                return isEven(number);
            }
            case EVEN_MINUS, ODD -> {
                return !isEven(number);
            }
            default -> {
                return false;
            }
        }
    }

    /* Method was used on stages 1-3 of the project Amazing numbers.

    private static long acceptUserNaturalNumber(Scanner scanner) {
        //System.out.print(Main.MESSAGES[0]);
        long result = scanner.nextLong();
        result = Main.checkIfNatural(result);
        return result;
    }
    */

    /* Method was used on stages 1-3 of the project Amazing numbers.
    private static long checkIfNatural(long number) {
        long result = -1;
        if (number >= 0) {
            result = number;
        }
        return result;
    }
    */

    private static boolean isEven(long number) {
        return number % 2 == 0;
    }

    private static boolean isBuzz(long number) {
        return number % 7 == 0 || number % 10 == 7;
    }

    private static boolean isDuck(long number) {
        boolean result = false;
        String numberAsString = Long.toString(number);
        for (int i = 1; i < numberAsString.length(); i++) {
            if (numberAsString.charAt(i) == '0') {
                result = true;
                break;
            }
        }
        return result;
    }

    private static boolean isPalindromic(long number) {
        boolean result = true;
        String numberAsString = Long.toString(number);
        int lengthOfNumberAsString = numberAsString.length();
        for (int i = 0; i < lengthOfNumberAsString / 2; i++) {
            if (numberAsString.charAt(i) != numberAsString.charAt(lengthOfNumberAsString - 1 - i)) {
                result = false;
                break;
            }
        }
        return result;
    }

    private static boolean isGapful(long number) {
        boolean result = true;
        if (number < 100) {
            return false;
        }
        String numberAsString = Long.toString(number);
        char[] dividerAsString = {numberAsString.charAt(0), numberAsString.charAt(numberAsString.length() - 1)};
        int divider = Integer.parseInt(String.valueOf(dividerAsString));
        if (number % divider != 0) {
            result = false;
        }
        return result;
    }

    private static boolean isSpy(long number) {
        boolean result = false;
        int sumOfDigits = 0;
        int productOfDigits = 1;
        if (!isDuck(number)) {
            String numberAsString = Long.toString(number);
            char[] numberAsChars = numberAsString.toCharArray();
            for (char digit : numberAsChars) {
                sumOfDigits += digit - '0';
                productOfDigits *= digit - '0';
            }
            if (sumOfDigits == productOfDigits) {
                result = true;
            }
        }
        return result;
    }

    private static boolean isSquare(long number) {
        boolean result;
        long integerPartOfSquareRoot = (long) Math.sqrt(number);
        result = (integerPartOfSquareRoot * integerPartOfSquareRoot == number);
        return result;
    }

    private static boolean isSunny(long number) {
        return isSquare(number + 1);
    }

    private static boolean isJumping(long number) {
        boolean result = true;

        if (number > 9) {
            char[] numberAsChars = Long.toString(number).toCharArray();
            for (int i = 1; i < numberAsChars.length; i++) {
                if (Math.abs(numberAsChars[i] - numberAsChars[i - 1]) != 1) {
                    result = false;
                    break;
                }
            }
        }

        return result;
    }

    private static boolean isHappy(long number) {

        boolean result = false;
        boolean search = true;

        ArrayList<Long> stepValues = new ArrayList<>();
        stepValues.add(number);

        StringBuilder numberAsString = new StringBuilder();
        long previousStepValue = number;
        long nextStepValue = 0;


            while (search) {
                numberAsString.insert(0, previousStepValue);
                for (int i = 0; i < numberAsString.length(); i++) {
                    nextStepValue += (long) (numberAsString.charAt(i) - '0') * (numberAsString.charAt(i) - '0');
                }

                if (nextStepValue == 1) {
                    result = true;
                    break;
                } else {
                    for (Long previousSteps : stepValues) {
                        if (previousSteps == nextStepValue) {
                            search = false;
                            break;
                        }
                    }
                    stepValues.add(nextStepValue);
                    previousStepValue = nextStepValue;
                }
                nextStepValue = 0;
                numberAsString.delete(0, numberAsString.length());
            }
        stepValues.clear();
        return result;
    }

    private static void printInstructions() {
        for (String instructionLine : Main.INSTRUCTIONS_TEXT) {
            System.out.println(instructionLine);
        }
    }

    private static void printNumberProperties(long number) {
        System.out.println();
        System.out.print(Main.MESSAGES_FOR_PROPERTIES[0]);
        System.out.println(number);
        System.out.print(Main.MESSAGES_FOR_PROPERTIES[3]);
        System.out.println(isBuzz(number));
        System.out.print(Main.MESSAGES_FOR_PROPERTIES[4]);
        System.out.println(isDuck(number));
        System.out.print(Main.MESSAGES_FOR_PROPERTIES[5]);
        System.out.println(isPalindromic(number));
        System.out.print(Main.MESSAGES_FOR_PROPERTIES[6]);
        System.out.println(isGapful(number));
        System.out.print(Main.MESSAGES_FOR_PROPERTIES[7]);
        System.out.println(isSpy(number));
        System.out.print(Main.MESSAGES_FOR_PROPERTIES[8]);
        System.out.println(isSquare(number));
        System.out.print(Main.MESSAGES_FOR_PROPERTIES[9]);
        System.out.println(isSunny(number));
        System.out.print(Main.MESSAGES_FOR_PROPERTIES[10]);
        System.out.println(isJumping(number));
        System.out.print(Main.MESSAGES_FOR_PROPERTIES[11]);
        System.out.println(isHappy(number));
        System.out.print(Main.MESSAGES_FOR_PROPERTIES[12]);
        System.out.println(!isHappy(number));
        System.out.print(Main.MESSAGES_FOR_PROPERTIES[1]);
        System.out.println(isEven(number));
        System.out.print(Main.MESSAGES_FOR_PROPERTIES[2]);
        System.out.println(!isEven(number));
    }

    private static void printNumberPropertiesInOneLine(long number) {
        System.out.println();
        System.out.print("             " + number + " is ");
        StringBuilder propertiesLine = new StringBuilder();
        if (isBuzz(number)) {
            propertiesLine.append("buzz");
        }

        if (isDuck(number)) {
            if (propertiesLine.isEmpty()) {
                propertiesLine.append("duck");
            } else {
                propertiesLine.append(", duck");
            }
        }

        if (isPalindromic(number)) {
            if (propertiesLine.isEmpty()) {
                propertiesLine.append("palindromic");
            } else {
                propertiesLine.append(", palindromic");
            }
        }

        if (isGapful(number)) {
            if (propertiesLine.isEmpty()) {
                propertiesLine.append("gapful");
            } else {
                propertiesLine.append(", gapful");
            }
        }

        if (isSpy(number)) {
            if (propertiesLine.isEmpty()) {
                propertiesLine.append("spy");
            } else {
                propertiesLine.append(", spy");
            }
        }

        if (isSquare(number)) {
            if (propertiesLine.isEmpty()) {
                propertiesLine.append("square");
            } else {
                propertiesLine.append(", square");
            }
        }

        if (isSunny(number)) {
            if (propertiesLine.isEmpty()) {
                propertiesLine.append("sunny");
            } else {
                propertiesLine.append(", sunny");
            }
        }

        if (isJumping(number)) {
            if (propertiesLine.isEmpty()) {
                propertiesLine.append("jumping");
            } else {
                propertiesLine.append(", jumping");
            }
        }

        if (isHappy(number)) {
            if (propertiesLine.isEmpty()) {
                propertiesLine.append("happy");
            } else {
                propertiesLine.append(", happy");
            }
        } else {
            if (propertiesLine.isEmpty()) {
                propertiesLine.append("sad");
            } else {
                propertiesLine.append(", sad");
            }
        }

        if (isEven(number)) {
            if (propertiesLine.isEmpty()) {
                propertiesLine.append("even");
            } else {
                propertiesLine.append(", even");
            }
        } else {
            if (propertiesLine.isEmpty()) {
                propertiesLine.append("odd");
            } else {
                propertiesLine.append(", odd");
            }
        }

        System.out.print(propertiesLine);
    }

    private enum numberProperties {
        BUZZ("buzz"),
        BUZZ_MINUS("-buzz"),
        DUCK("duck"),
        DUCK_MINUS("-duck"),
        PALINDROMIC("palindromic"),
        PALINDROMIC_MINUS("-palindromic"),
        GAPFUL("gapful"),
        GAPFUL_MINUS("-gapful"),
        SPY("spy"),
        SPY_MINUS("-spy"),
        SQUARE("square"),
        SQUARE_MINUS("-square"),
        SUNNY("sunny"),
        SUNNY_MINUS("-sunny"),
        JUMPING("jumping"),
        JUMPING_MINUS("-jumping"),
        HAPPY("happy"),
        HAPPY_MINUS("-happy"),
        SAD("sad"),
        SAD_MINUS("-sad"),
        EVEN("even"),
        EVEN_MINUS("-even"),
        ODD("odd"),
        ODD_MINUS("-odd");

        final String propertyTitle;

        numberProperties(String propertyTitle) {
            this.propertyTitle = propertyTitle;
        }

        public static numberProperties findByPropertyTitle(String propertyTitle) {
            for (numberProperties property: values()) {
                if (property.propertyTitle.equals(propertyTitle)) {
                    return property;
                }
            }
            return null;
        }

        public static void printAvailableProperties() {
            System.out.print("Available properties: [");
            for (numberProperties property: values()) {
                if (property.propertyTitle.charAt(0) != '-') {
                    System.out.print(property.name());
                    if (property != ODD) {
                        System.out.print(", ");
                    }
                }
            }
            System.out.println("]");
        }

    }

}
