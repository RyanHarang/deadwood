import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.event.*;
import javafx.stage.Stage;
import javafx.scene.image.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.text.*;

public class View extends Application {

    // constructor
    public View() {

    }

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) throws FileNotFoundException {
        // create control
        Control control = new Control();

        // root
        HBox root = new HBox();

        // stage image
        FileInputStream boardStream = new FileInputStream("images/board.jpg");
        Image board = new Image(boardStream, 0, 0, true, true);
        ImageView boardView = new ImageView(board);
        boardView.setPreserveRatio(true);
        boardView.setFitHeight(600);
        root.getChildren().add(boardView);

        // User interface
        VBox ui = new VBox();
        // Player info
        GridPane playerInfo = new GridPane();
        playerInfo.setMinSize(300, 200);
        playerInfo.setPadding(new Insets(10, 10, 10, 10));
        playerInfo.setVgap(5);
        playerInfo.setHgap(5);
        playerInfo.setAlignment(Pos.CENTER);

        Text player = new Text("Active player: ");
        Text playerMoney = new Text("Money: ");
        Text playerCredits = new Text("Credits: ");
        Text playerRole = new Text("Role: ");
        Text currentPlayer = new Text("hi");
        Text currentPlayerMoney = new Text("broke");
        Text currentPlayerCredits = new Text("no clout");
        Text currentPlayerRole = new Text("trash");

        Font infoFont = new Font(20);
        Font btnFont = new Font(20);

        // System.out.println(javafx.scene.text.Font.getFamilies());

        player.setFont(infoFont);
        playerMoney.setFont(infoFont);
        playerCredits.setFont(infoFont);
        playerRole.setFont(infoFont);
        currentPlayer.setFont(infoFont);
        currentPlayerMoney.setFont(infoFont);
        currentPlayerCredits.setFont(infoFont);
        currentPlayerRole.setFont(infoFont);

        playerInfo.add(player, 0, 0);
        playerInfo.add(currentPlayer, 1, 0);
        playerInfo.add(playerMoney, 0, 1);
        playerInfo.add(currentPlayerMoney, 1, 1);
        playerInfo.add(playerCredits, 0, 2);
        playerInfo.add(currentPlayerCredits, 1, 2);
        playerInfo.add(playerRole, 0, 3);
        playerInfo.add(currentPlayerRole, 1, 3);
        ui.getChildren().add(playerInfo);

        // Player buttons
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
        Button endTurn = new Button("End Turn");

        // setting width
        move.setMinWidth(110.0);
        upgrade.setMinWidth(110.0);
        act.setMinWidth(110.0);
        rehearse.setMinWidth(110.0);
        endTurn.setMinWidth(224.0);

        // setting height
        move.setMinHeight(40.0);
        upgrade.setMinHeight(40.0);
        act.setMinHeight(40.0);
        rehearse.setMinHeight(40.0);
        endTurn.setMinHeight(40.0);

        /*
         * //This is how to set a button with an image
         * ImageView moveImg = new ImageView(new Image("images/CardBack.jpg"));
         * moveImg.setFitHeight(40);
         * moveImg.setPreserveRatio(true);
         * move.setGraphic(moveImg);
         */

        // applying font
        move.setFont(btnFont);
        upgrade.setFont(btnFont);
        act.setFont(btnFont);
        rehearse.setFont(btnFont);
        endTurn.setFont(btnFont);

        move.setId("move");
        upgrade.setId("upgrade");
        act.setId("act");
        rehearse.setId("rehearse");
        endTurn.setId("endTurn");

        move.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                control.move();
            }
        });

        upgrade.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                control.upgrade();
            }
        });

        act.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                control.act();
            }
        });

        rehearse.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                control.rehearse();
            }
        });

        endTurn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                control.endTurn();
            }
        });

        // adding buttons to grid pane
        actions.add(move, 0, 0);
        actions.add(upgrade, 1, 0);
        actions.add(act, 0, 1);
        actions.add(rehearse, 1, 1);
        actions.add(endTurn, 0, 2, 2, 1);

        ui.getChildren().add(actions);
        root.getChildren().add(ui);

        Scene mainScene = new Scene(root, 1100, 600);
        mainScene.getStylesheets().add("style.css");
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

}
