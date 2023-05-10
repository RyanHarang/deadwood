import java.util.Scanner;

public class InpManager {

    private static InpManager manager = new InpManager();

    // constructor
    private InpManager() {
    }

    public static InpManager getInstance() {
        return manager;
    }

    public void startGame() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Welcome to deadwood!");
        scan.close();
    }

    // method for prompting the user for input
    public String newInput() {
        Scanner scan = new Scanner(System.in);
        String userInput = scan.nextLine();
        scan.close();
        return userInput;
    }
}