import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.image.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;

public class Test extends Application {
    public void start(Stage primaryStage) throws FileNotFoundException {
        HBox hbox1 = new HBox();
        Label label1 = new Label("Multipy by 5:"); // show text
        TextField myTextField = new TextField();
        hbox1.getChildren().addAll(label1, myTextField);
        BorderPane borderPane1 = new BorderPane();
        // Button myButton = new Button("Multiply");
        FileInputStream inputstream = new FileInputStream("images/board.jpg");

        Image image = new Image(inputstream);
        ImageView imageView = new ImageView(image);
        imageView.setX(0);
        imageView.setY(0);
        imageView.setFitHeight(300);
        imageView.setFitWidth(300);

        imageView.setPreserveRatio(true);
        Group root2 = new Group(imageView);
        /*
         * myButton.setOnAction(event -> {
         * try {
         * System.out.println(Integer.parseInt(myTextField.getText()) * 5);
         * } catch (Exception e) {
         * System.out.println("Not an integer!");
         * }
         * });
         */

        // borderPane1.setTop(hbox1);
        // borderPane1.setCenter(myButton);
        int width = 300;
        int height = 300;
        Scene scene = new Scene(root2, width, height);
        primaryStage.setScene(scene);
        primaryStage.show(); // show the Stage
    }

    public static void handleClick() {
        System.out.println("I'VE BEEN CLICKED");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
