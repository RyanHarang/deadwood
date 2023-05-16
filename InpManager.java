import java.util.Scanner;

public class InpManager {
    private static Scanner scan;
    private static InpManager manager = new InpManager();

    // constructor
    private InpManager() {
    }

    public static InpManager getInpManager() {
        return manager;
    }

    // method for prompting the user for input
    public String newInput() {
        scan = new Scanner(System.in);
        String userInput = scan.nextLine();
        return userInput;
    }

    public void newOutput(String output) {
        System.out.println(output);
    }

    public void end() {
        scan.close();
    }
}