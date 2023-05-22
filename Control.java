public class Control {

    public Control() {

    }

    public void startGame() {

    }

    public String move(String id) {
        if (id.equals("move")) {
            return "deactivatedMove";
        }
        System.out.println("move clicked");
        return "move";
    }

    public String upgrade(String id) {
        if (id.equals("upgrade")) {
            return "deactivatedUpgrade";
        }
        System.out.println("upgrade clicked");
        return "upgrade";
    }

    public String act(String id) {
        if (id.equals("act")) {
            return "deactivatedAct";
        }
        System.out.println("act clicked");
        return "act";
    }

    public String rehearse(String id) {
        if (id.equals("rehearse")) {
            return "deactivatedRehearse";
        }
        System.out.println("rehearse clicked");
        return "rehearse";
    }

    public void endTurn() {
        System.out.println("endTurn clicked");
    }
}
