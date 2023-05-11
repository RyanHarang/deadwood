import java.util.ArrayList;

public class SceneDeck {
    private ArrayList<Scene> scenes;

    public SceneDeck(ArrayList<Scene> scenes) {
        this.scenes = scenes;
    }

    // return random scene, remove scene from deck
    public Scene getScene() {
        int rand = (int) (Math.random() * scenes.size());
        Scene sc = scenes.get(rand);
        scenes.remove(rand);
        return sc;
    }
}