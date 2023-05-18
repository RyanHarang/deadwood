import java.util.ArrayList;

public class SceneDeck {
    private ArrayList<SceneCard> scenes;

    public SceneDeck(ArrayList<SceneCard> scenes) {
        this.scenes = scenes;
    }

    // return random scene, remove scene from deck
    public SceneCard getScene() {
        int rand = (int) (Math.random() * scenes.size());
        SceneCard sc = scenes.get(rand);
        scenes.remove(rand);
        return sc;
    }
}