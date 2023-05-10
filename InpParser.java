public class InpParser {

    private InpManager manager;

    public InpParser() {
        manager = InpManager.getInstance();
    }

    public void startGame() {
        manager.newInput();
    }

    public void handleInput(String inp) {

    }
}
