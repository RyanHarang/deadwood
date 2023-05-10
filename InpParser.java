import java.util.Scanner;
public class InpParser {

    Scanner s = new Scanner(System.in);
    public InpParser() {

    }

    public char handleInputFirstLetter() {
        String response = s.nextLine();
        return response.toLowerCase().charAt(0);
    }
}
