import java.util.Scanner;

public class InpManager {
<<<<<<< HEAD
    // constructor
    public InpManager() {
=======

    private static InpManager manager = new InpManager();
>>>>>>> d6ffd90590bd0696bf3f657a1da68678d24cf1a7

    // constructor
    private InpManager() {
    }

    public static InpManager getInstance() {
        return manager;
    }

    // method for prompting the user for input
<<<<<<< HEAD
    public void newInput(String input) {
        System.out.print(input + " ");
=======
    public String newInput() {
        Scanner scan = new Scanner(System.in);
        String userInput = scan.nextLine();
        scan.close();
        return userInput;
>>>>>>> d6ffd90590bd0696bf3f657a1da68678d24cf1a7
    }
}