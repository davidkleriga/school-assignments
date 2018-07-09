package applied_logic.utility;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Prompt {


    public static String GetPromptValueSynchronous(String displayMessage, String errorMessage, String formatValue) {
        Scanner inputScanner = new Scanner(System.in);
        System.out.println(displayMessage);
        String responseValue = "";
        do {
            try {
                String nextLine = inputScanner.nextLine();
                if(nextLine.matches(formatValue)) {
                    responseValue = nextLine;
                } else {
                    throw new InputMismatchException();
                }
            } catch (Exception highLevelException) {
                System.out.println(errorMessage);
            }
        } while (responseValue == "");
        return responseValue;
    }
}
