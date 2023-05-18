import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.image.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.text.Text;

public class View extends Application{
    public void start(Stage primaryStage) throws FileNotFoundException{
        //root
        HBox root = new HBox();

        //stage image
        FileInputStream boardStream = new FileInputStream("testing_javafx/images/board.jpg");
        Image board = new Image(boardStream, 0, 0, true, true);
        ImageView boardView = new ImageView(board);
        boardView.setPreserveRatio(true);
        boardView.setFitHeight(600);
        root.getChildren().add(boardView);
        

        //User interface
        VBox ui = new VBox();
        //Player info
        GridPane playerInfo = new GridPane();
        playerInfo.setMinSize(300, 200);
        playerInfo.setPadding(new Insets(10, 10, 10, 10));
        playerInfo.setVgap(5);
        playerInfo.setHgap(5);
        playerInfo.setAlignment(Pos.CENTER);
        Text Player = new Text("Active player: ");
        Text PlayerMoney = new Text("Money: ");
        Text PlayerCredits = new Text("Credits: ");
        Text PlayerRole = new Text("Role: ");
        Text currentPlayer = new Text("hi");
        Text currentPlayerMoney = new Text("broke");
        Text currentPlayerCredits = new Text("no clout");
        Text currentPlayerRole = new Text("trash");
        playerInfo.add(Player, 0, 0);
        playerInfo.add(currentPlayer, 1, 0);
        playerInfo.add(PlayerMoney, 0, 1);
        playerInfo.add(currentPlayerMoney, 1, 1);
        playerInfo.add(PlayerCredits, 0, 2);
        playerInfo.add(currentPlayerCredits, 1, 2);
        playerInfo.add(PlayerRole, 0, 3);
        playerInfo.add(currentPlayerRole, 1, 3);
        ui.getChildren().add(playerInfo);

        //Player buttons
        GridPane actions = new GridPane();
        actions.setMinSize(300, 300);
        actions.setPadding(new Insets(10, 10, 10, 10));
        actions.setVgap(5);
        actions.setHgap(5);
        actions.setAlignment(Pos.CENTER);
        Button move = new Button("Move");
        Button upgrade = new Button("Upgrade");
        Button act = new Button("Act");
        Button rehearse = new Button("Rehearse");
        actions.add(move, 0, 0);
        actions.add(upgrade, 1, 0);
        actions.add(act, 0, 1);
        actions.add(rehearse, 1, 1);
        ui.getChildren().add(actions);

        Button endTurn = new Button("End Turn");
        ui.getChildren().add(endTurn);

        root.getChildren().add(ui);



        Scene mainScene = new Scene(root, 1100, 600);
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    public static void main(String[] args){
        launch(args);
    }
}
