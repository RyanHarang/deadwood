import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.event.*;
import javafx.stage.Popup;
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

    /*
     * public static void open(String[] args) {
     * launch(args);
     * }
     */

    public void start(Stage primaryStage) throws FileNotFoundException {
        // root
        HBox root = new HBox();

        // stage image
        FileInputStream boardStream = new FileInputStream("assets/images/board.jpg");
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
        // Info Text
        Text player = new Text("Active player: ");
        Text playerMoney = new Text("Money: ");
        Text playerCredits = new Text("Credits: ");
        Text playerRole = new Text("Role: ");
        Text currentPlayer = new Text("hi");
        Text currentPlayerMoney = new Text("broke");
        Text currentPlayerCredits = new Text("no clout");
        Text currentPlayerRole = new Text("trash");

        player.setId("player");
        playerMoney.setId("playerMoney");
        playerCredits.setId("playerCredits");
        playerRole.setId("playerRole");
        currentPlayer.setId("currentPlayer");
        currentPlayerMoney.setId("currentPlayerMoney");
        currentPlayerCredits.setId("currentPlayerCredits");
        currentPlayerRole.setId("currentPlayerRole");

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
        move.setMinWidth(120.0);
        upgrade.setMinWidth(120.0);
        act.setMinWidth(120.0);
        rehearse.setMinWidth(120.0);
        endTurn.setMinWidth(244.0);

        // setting height
        move.setMinHeight(40.0);
        upgrade.setMinHeight(40.0);
        act.setMinHeight(40.0);
        rehearse.setMinHeight(40.0);
        endTurn.setMinHeight(40.0);

        move.setId("move");
        upgrade.setId("upgrade");
        act.setId("act");
        rehearse.setId("rehearse");
        endTurn.setId("endTurn");

        move.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                move.setId(move(move.getId()));
            }
        });

        upgrade.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                upgrade.setId(upgrade(upgrade.getId()));
            }
        });

        act.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                act.setId(act(act.getId()));
            }
        });

        rehearse.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                rehearse.setId(rehearse(rehearse.getId()));
            }
        });

        endTurn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                endTurn();
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

        GridPane popGrid = new GridPane();
        // creating 7 radio buttons and adding them to a toggle group
        Label pc = new Label("Select a player count:");
        pc.setId("pc");
        ToggleGroup group = new ToggleGroup();
        RadioButton r2 = new RadioButton("2 Players");
        RadioButton r3 = new RadioButton("3 Players");
        RadioButton r4 = new RadioButton("4 Players");
        RadioButton r5 = new RadioButton("5 Players");
        RadioButton r6 = new RadioButton("6 Players");
        RadioButton r7 = new RadioButton("7 Players");
        RadioButton r8 = new RadioButton("8 Players");
        Button submit = new Button("Submit");
        submit.setId("submit");

        // putting all radio buttons in one group
        r2.setToggleGroup(group);
        r3.setToggleGroup(group);
        r4.setToggleGroup(group);
        r5.setToggleGroup(group);
        r6.setToggleGroup(group);
        r7.setToggleGroup(group);
        r8.setToggleGroup(group);
        r4.setSelected(true);

        // adding things to popup
        popGrid.add(pc, 0, 0);
        popGrid.add(r2, 0, 1);
        popGrid.add(r3, 0, 2);
        popGrid.add(r4, 0, 3);
        popGrid.add(r5, 0, 4);
        popGrid.add(r6, 0, 5);
        popGrid.add(r7, 0, 6);
        popGrid.add(r8, 0, 7);
        popGrid.add(submit, 0, 8);

        popGrid.setId("popGrid");
        // create a popup
        Popup popup = new Popup();
        popup.setWidth(600);
        popup.setHeight(600);

        popup.getContent().add(popGrid);
        EventHandler<ActionEvent> playerPopup = new EventHandler<ActionEvent>() {

            public void handle(ActionEvent e) {
                Deadwood.setDays(2);
                popup.hide();
            }
        };

        submit.setOnAction(playerPopup);
        Scene mainScene = new Scene(root, 1100, 600);
        mainScene.getStylesheets().add("assets/css/style.css");
        primaryStage.setScene(mainScene);
        primaryStage.show();
        popup.show(primaryStage);
    }

    public void startGame() {

    }

    public String move(String id) {
        System.out.println("move clicked");
        if (id.equals("move")) {
            return "deactivatedMove";
        }
        return "move";
    }

    public String upgrade(String id) {
        System.out.println("upgrade clicked");
        if (id.equals("upgrade")) {
            return "deactivatedUpgrade";
        }
        return "upgrade";
    }

    public String act(String id) {
        System.out.println("act clicked");
        if (id.equals("act")) {
            return "deactivatedAct";
        }
        return "act";
    }

    public String rehearse(String id) {
        System.out.println("rehearse clicked");
        if (id.equals("rehearse")) {
            return "deactivatedRehearse";
        }
        return "rehearse";
    }

    public void endTurn() {
        System.out.println("endTurn clicked");
    }
}