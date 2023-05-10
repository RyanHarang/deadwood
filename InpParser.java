public class InpParser {

    private InpManager manager;

    public InpParser() {
        manager = InpManager.getInstance();
    }

    // this method will set the player count and day count in deadwood
    // potentially easiest to return an array formatted something like
    // [playerCount, days] and have deadwood use the return to initialize it's own
    // variables
    public void startGame() {
        int playerCount, days;
        boolean validPC = false;
        String input;

        System.out
                .println("Welcome to deadwood! Please enter the number of players you would like to play with [2-8]: ");
        input = manager.newInput();
        while (!validPC) {
            try {
                playerCount = Integer.parseInt(input);
                if (2 <= playerCount && playerCount <= 8) {
                    validPC = true;
                    System.out.println("You have seleved to play with " + playerCount + " players.");
                } else {
                    System.out.println("Deadwood requires a [2-8] players to play! Please try again");
                }

            } catch (Exception e) {
                System.out.println("That's not a number! Please try again. Valid player counts are [2-8]");
                input = manager.newInput();
            }
        }

    }

    public void handleInput(String inp) {

    }
}
