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

import java.util.ArrayList;

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
        // pc.setId("pc");
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
        Popup countPopup = new Popup();
        countPopup.getContent().add(popGrid);
        EventHandler<ActionEvent> playerPopup = new EventHandler<ActionEvent>() {

            public void handle(ActionEvent e) {
                int playerCount = 0;
                ArrayList<String> names = new ArrayList<String>();
                if (r2.isSelected()) {
                    playerCount = 2;
                } else if (r3.isSelected()) {
                    playerCount = 3;
                } else if (r4.isSelected()) {
                    playerCount = 4;
                } else if (r5.isSelected()) {
                    playerCount = 5;
                } else if (r6.isSelected()) {
                    playerCount = 6;
                } else if (r7.isSelected()) {
                    playerCount = 7;
                } else if (r8.isSelected()) {
                    playerCount = 8;
                }
                final Integer innerPlayerCount = playerCount;

                GridPane popGrid2 = new GridPane();
                popGrid2.setId("popGrid2");
                Label enterName1 = new Label("Player 1 Name");
                Label enterName2 = new Label("Player 2 Name");
                Label enterName3 = new Label("Player 3 Name");
                Label enterName4 = new Label("Player 4 Name");
                Label enterName5 = new Label("Player 5 Name");
                Label enterName6 = new Label("Player 6 Name");
                Label enterName7 = new Label("Player 7 Name");
                Label enterName8 = new Label("Player 8 Name");
                Button submit2 = new Button("Submit");
                submit2.setId("submit");
                TextField tf1 = new TextField();
                TextField tf2 = new TextField();
                TextField tf3 = new TextField();
                TextField tf4 = new TextField();
                TextField tf5 = new TextField();
                TextField tf6 = new TextField();
                TextField tf7 = new TextField();
                TextField tf8 = new TextField();

                popGrid2.add(enterName1, 0, 0);
                popGrid2.add(tf1, 1, 0);
                popGrid2.add(enterName2, 0, 1);
                popGrid2.add(tf2, 1, 1);
                // adding a textfield per player name needed
                for (int i = 3; i <= playerCount; i++) {
                    if (i == 3) {
                        popGrid2.add(enterName3, 0, 2);
                        popGrid2.add(tf3, 1, 2);
                    } else if (i == 4) {
                        popGrid2.add(enterName4, 0, 3);
                        popGrid2.add(tf4, 1, 3);
                    } else if (i == 5) {
                        popGrid2.add(enterName5, 0, 4);
                        popGrid2.add(tf5, 1, 4);
                    } else if (i == 6) {
                        popGrid2.add(enterName6, 0, 5);
                        popGrid2.add(tf6, 1, 5);
                    } else if (i == 7) {
                        popGrid2.add(enterName7, 0, 6);
                        popGrid2.add(tf7, 1, 6);
                    } else if (i == 8) {
                        popGrid2.add(enterName8, 0, 7);
                        popGrid2.add(tf8, 1, 7);
                    }
                }
                popGrid2.add(submit2, 0, playerCount + 1);
                Popup namePopup = new Popup();

                namePopup.getContent().add(popGrid2);
                namePopup.show(primaryStage);

                submit2.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {
                        names.add(tf1.getText());
                        names.add(tf2.getText());
                        for (int j = 3; j <= innerPlayerCount; j++) {
                            if (j == 3) {
                                // System.out.println("Adding tf3 = " + tf3.getText());
                                names.add(tf3.getText());
                            } else if (j == 4) {
                                // System.out.println("Adding tf4 = " + tf4.getText());
                                names.add(tf4.getText());
                            } else if (j == 5) {
                                // System.out.println("Adding tf5 = " + tf5.getText());
                                names.add(tf5.getText());
                            } else if (j == 6) {
                                // System.out.println("Adding tf6 = " + tf6.getText());
                                names.add(tf6.getText());
                            } else if (j == 7) {
                                // System.out.println("Adding tf7 = " + tf7.getText());
                                names.add(tf7.getText());
                            } else if (j == 8) {
                                // System.out.println("Adding tf8 = " + tf8.getText());
                                names.add(tf8.getText());
                            }
                        }
                        Deadwood.initializePlayers(names);
                        namePopup.hide();
                    }
                });

                countPopup.hide();
            }
        };

        submit.setOnAction(playerPopup);
        Scene mainScene = new Scene(root, 1100, 600);
        mainScene.getStylesheets().add("assets/css/style.css");
        primaryStage.setTitle("Deadwood");
        primaryStage.setScene(mainScene);
        primaryStage.show();
        countPopup.show(primaryStage);
    }

    public void startGame() {

    }

    public String move(String id) {
        System.out.println("move clicked");
        if (id.equals("move")) {
            return "deactivatedMove";
        }
        // get the string name of the players current room
        // use board to get the room object with that name
        // use room object to get all adjacent rooms
        // use names of all adjacent rooms to create options
        // once options created, show popup with the options
        // when popup is submitted, take the selected name
        // turn it back into a room object and send it
        // to location manager to move the player
        //
        //
        // this requires an active player variable in deadwood
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