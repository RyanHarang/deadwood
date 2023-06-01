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

import java.util.*;

public class View extends Application {
    private static ArrayList<RadioButton> locations;
    private static ArrayList<Role> availableRoles;
    private static ArrayList<RadioButton> upgrades;

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) throws FileNotFoundException {
        // initialize rooms
        rooms();
        upgrades();
        XMLParser xml = new XMLParser();
        Deadwood.setDeck(new SceneDeck(xml.readCardData()));
        Deadwood.setBoard(new Board(xml.readBoardData()));

        // setting root and back layer of stackpane
        HBox back = new HBox();
        back.setMaxSize(1725, 900);
        back.setMinSize(1725, 600);
        Pane root = new Pane(back);

        // stage image
        FileInputStream boardStream = new FileInputStream("assets/images/board.jpg");
        Image board = new Image(boardStream, 0, 0, true, true);
        ImageView boardView = new ImageView(board);
        boardView.setPreserveRatio(true);
        boardView.setFitHeight(900);
        back.getChildren().add(boardView);

        // creating 10 scenes, one for each room
        FileInputStream cardBackStream = new FileInputStream("assets/images/cardBack.jpg");
        Image cardBack = new Image(cardBackStream, 0, 0, true, true);
        // mapping scene images to room names
        Map<String, ImageView> scenes = new HashMap<String, ImageView>();
        ImageView scene0View = new ImageView(cardBack), scene1View = new ImageView(cardBack),
                scene2View = new ImageView(cardBack), scene3View = new ImageView(cardBack),
                scene4View = new ImageView(cardBack), scene5View = new ImageView(cardBack),
                scene6View = new ImageView(cardBack), scene7View = new ImageView(cardBack),
                scene8View = new ImageView(cardBack), scene9View = new ImageView(cardBack);
        scenes.put("Train Station", scene0View); // train station
        scenes.put("Jail", scene1View); // jail
        scenes.put("Main Street", scene2View); // main street
        scenes.put("General Store", scene3View); // general store
        scenes.put("Saloon", scene4View); // saloon
        scenes.put("Ranch", scene5View); // ranch
        scenes.put("Bank", scene6View); // bank
        scenes.put("Secret Hideout", scene7View); // secret hideout
        scenes.put("Church", scene8View); // church
        scenes.put("Hotel", scene9View); // hotel

        // creating and placing scenes
        for (Map.Entry<String, ImageView> entry : scenes.entrySet()) {
            String name = entry.getKey();
            ImageView cur = entry.getValue();
            Room room = Board.roomByName(name);
            int x = room.getArea()[0];
            int y = room.getArea()[1];
            cur.setLayoutX(x);
            cur.setLayoutY(y);
            cur.setPreserveRatio(true);
            cur.setFitHeight(115);
            root.getChildren().add(cur);
        }

        // create 8 player icons
        String basePath = "assets/images/dice/";
        Image dice1 = null;
        Image dice2 = null;
        Image dice3 = null;
        Image dice4 = null;
        Image dice5 = null;
        Image dice6 = null;
        Image dice7 = null;
        Image dice8 = null;
        Image[] diceArray = { dice1, dice2, dice3, dice4, dice5, dice6, dice7, dice8 };
        ImageView dice1View = null, dice2View = null, dice3View = null, dice4View = null,
                dice5View = null, dice6View = null, dice7View = null, dice8View = null;
        ImageView[] diceViewArray = { dice1View, dice2View, dice3View, dice4View, dice5View, dice6View, dice7View,
                dice8View };
        Map<Player, ImageView> playerDiceMap = new HashMap<Player, ImageView>();
        Map<String, int[]> noRole = new HashMap<String, int[]>();
        int[] officeXY = { 125, 540 };
        int[] trailerXY = { 1115, 290 };
        int[] ranchXY = { 298, 629 };
        int[] saloonXY = { 837, 395 };
        int[] hotelXY = { 1144, 855 };
        int[] secretHideoutXY = { 232, 847 };
        int[] trainStationXY = { 67, 230 };
        int[] mainStreetXY = { 1144, 143 };
        int[] jailXY = { 486, 142 };
        int[] churchXY = { 798, 849 };
        int[] bankXY = { 831, 590 };
        int[] generalStoreXY = { 324, 397 };
        noRole.put("office", officeXY);
        noRole.put("trailer", trailerXY);
        noRole.put("Ranch", ranchXY);
        noRole.put("Saloon", saloonXY);
        noRole.put("Hotel", hotelXY);
        noRole.put("Secret Hideout", secretHideoutXY);
        noRole.put("Train Station", trainStationXY);
        noRole.put("Main Street", mainStreetXY);
        noRole.put("Jail", jailXY);
        noRole.put("Church", churchXY);
        noRole.put("Bank", bankXY);
        noRole.put("General Store", generalStoreXY);

        // creating shot images for each room
        FileInputStream shotStream = new FileInputStream("assets/images/shot.png");
        Image shot = new Image(shotStream, 0, 0, true, true);
        ImageView[] shotViews = new ImageView[22];
        for (int i = 0; i < 22; i++) {
            shotViews[i] = new ImageView(shot);
            shotViews[i].setPreserveRatio(true);
            shotViews[i].setFitHeight(47);
        }
        Map<String, ArrayList<ImageView>> shotsMap = new HashMap<String, ArrayList<ImageView>>();
        shotsMap.put("Ranch", null);
        shotsMap.put("Saloon", null);
        shotsMap.put("Hotel", null);
        shotsMap.put("Secret Hideout", null);
        shotsMap.put("Train Station", null);
        shotsMap.put("Main Street", null);
        shotsMap.put("Jail", null);
        shotsMap.put("Church", null);
        shotsMap.put("Bank", null);
        shotsMap.put("General Store", null);
        // created a map to hold room names and arraylists of their corresponding shots
        for (Map.Entry<String, ArrayList<ImageView>> entry : shotsMap.entrySet()) {
            ArrayList<ImageView> shots = new ArrayList<ImageView>();
            String name = entry.getKey();
            if (name.equals("Train Station")) {
                shotViews[0].setLayoutX(141);
                shotViews[1].setLayoutX(89);
                shotViews[2].setLayoutX(36);
                shotViews[0].setLayoutY(11);
                shotViews[1].setLayoutY(11);
                shotViews[2].setLayoutY(11);
                shots.add(shotViews[0]);
                shots.add(shotViews[1]);
                shots.add(shotViews[2]);
            } else if (name.equals("Jail")) {
                shotViews[3].setLayoutX(442);
                shotViews[3].setLayoutY(156);
                shots.add(shotViews[3]);
            } else if (name.equals("Main Street")) {
                shotViews[4].setLayoutX(804);
                shotViews[5].setLayoutX(858);
                shotViews[6].setLayoutX(912);
                shotViews[4].setLayoutY(23);
                shotViews[5].setLayoutY(23);
                shotViews[6].setLayoutY(23);
                shots.add(shotViews[4]);
                shots.add(shotViews[5]);
                shots.add(shotViews[6]);
            } else if (name.equals("General Store")) {
                shotViews[7].setLayoutX(313);
                shotViews[8].setLayoutX(313);
                shotViews[7].setLayoutY(330);
                shotViews[8].setLayoutY(277);
                shots.add(shotViews[7]);
                shots.add(shotViews[8]);
            } else if (name.equals("Saloon")) {
                shotViews[9].setLayoutX(679);
                shotViews[10].setLayoutX(626);
                shotViews[9].setLayoutY(216);
                shotViews[10].setLayoutY(216);
                shots.add(shotViews[9]);
                shots.add(shotViews[10]);
            } else if (name.equals("Ranch")) {
                shotViews[11].setLayoutX(525);
                shotViews[12].setLayoutX(472);
                shotViews[11].setLayoutY(473);
                shotViews[12].setLayoutY(473);
                shots.add(shotViews[11]);
                shots.add(shotViews[12]);
            } else if (name.equals("Bank")) {
                shotViews[13].setLayoutX(840);
                shotViews[13].setLayoutY(549);
                shots.add(shotViews[13]);
            } else if (name.equals("Secret Hideout")) {
                shotViews[14].setLayoutX(354);
                shotViews[15].setLayoutX(299);
                shotViews[16].setLayoutX(244);
                shotViews[14].setLayoutY(764);
                shotViews[15].setLayoutY(764);
                shotViews[16].setLayoutY(764);
                shots.add(shotViews[14]);
                shots.add(shotViews[15]);
                shots.add(shotViews[16]);
            } else if (name.equals("Church")) {
                shotViews[17].setLayoutX(682);
                shotViews[18].setLayoutX(623);
                shotViews[17].setLayoutY(675);
                shotViews[18].setLayoutY(675);
                shots.add(shotViews[17]);
                shots.add(shotViews[18]);
            } else if (name.equals("Hotel")) {
                shotViews[19].setLayoutX(1111);
                shotViews[20].setLayoutX(1058);
                shotViews[21].setLayoutX(1005);
                shotViews[19].setLayoutY(683);
                shotViews[20].setLayoutY(683);
                shotViews[21].setLayoutY(683);
                shots.add(shotViews[19]);
                shots.add(shotViews[20]);
                shots.add(shotViews[21]);
            }
            ArrayList<ImageView> copy = new ArrayList<ImageView>(shots);
            entry.setValue(copy);
            shots.clear();
        }

        for (int p = 0; p < 22; p++) {
            root.getChildren().add(shotViews[p]);
        }

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
        Text playerPracticeChips = new Text("Practice chips:");
        Text playerRank = new Text("Rank");
        Text currentPlayer = new Text("----");
        Text currentPlayerMoney = new Text("----");
        Text currentPlayerCredits = new Text("----");
        Text currentPlayerRole = new Text("----");
        Text currentPlayerPracticeChips = new Text("----");
        Text currentPlayerRank = new Text("----");

        player.setId("player");
        playerMoney.setId("playerMoney");
        playerCredits.setId("playerCredits");
        playerRole.setId("playerRole");
        playerPracticeChips.setId("playerPracticeChips");
        playerRank.setId("playerRank");
        currentPlayer.setId("currentPlayer");
        currentPlayerMoney.setId("currentPlayerMoney");
        currentPlayerCredits.setId("currentPlayerCredits");
        currentPlayerRole.setId("currentPlayerRole");
        currentPlayerPracticeChips.setId("currentPlayerPracticeChips");
        currentPlayerRank.setId("currentPlayerRank");

        playerInfo.add(player, 0, 0);
        playerInfo.add(currentPlayer, 1, 0);
        playerInfo.add(playerPracticeChips, 0, 1);
        playerInfo.add(currentPlayerPracticeChips, 1, 1);
        playerInfo.add(playerMoney, 0, 2);
        playerInfo.add(currentPlayerMoney, 1, 2);
        playerInfo.add(playerCredits, 0, 3);
        playerInfo.add(currentPlayerCredits, 1, 3);
        playerInfo.add(playerRank, 0, 4);
        playerInfo.add(currentPlayerRank, 1, 4);
        playerInfo.add(playerRole, 0, 5);
        playerInfo.add(currentPlayerRole, 1, 5);
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

        // setting id
        move.setId("move");
        upgrade.setId("upgrade");
        act.setId("act");
        rehearse.setId("rehearse");
        endTurn.setId("endTurn");

        // move onClick
        move.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (move.getId().equals("move")) {
                    // hiding buttons
                    endTurn.setVisible(false);
                    move.setVisible(false);
                    act.setVisible(false);
                    upgrade.setVisible(false);
                    rehearse.setVisible(false);
                }
                if (Deadwood.getActivePlayer().getCanMove()) {
                    // step 1: generating list of room names
                    ArrayList<Room> adjRooms = new ArrayList<Room>();
                    ArrayList<String> rNames = new ArrayList<String>();
                    adjRooms = (LocationManager.getPlayerLocation(Deadwood.getActivePlayer()).getAdjacents());

                    for (Room r : adjRooms) {
                        rNames.add(r.getName());
                    }

                    // step 2: Create gridPane and popup with radio button options and submit button
                    GridPane movePane = new GridPane();
                    ToggleGroup moveToggleGroup = new ToggleGroup();
                    movePane.setId("popGrid3");
                    Label moveLabel = new Label("Select a room to move to:");
                    for (int i = 0; i < rNames.size(); i++) {
                        for (RadioButton rb : View.locations) {
                            if (rb.getId().equals(rNames.get(i))) {
                                movePane.add(rb, 0, i + 1);
                                rb.setToggleGroup(moveToggleGroup);
                                rb.setSelected(true);
                            }
                        }
                    }
                    Popup movePopup = new Popup();
                    movePane.add(moveLabel, 0, 0);
                    Button moveSubmit = new Button("Submit");
                    moveSubmit.setId("submit");
                    movePane.add(moveSubmit, 0, rNames.size() + 1);
                    movePopup.getContent().add(movePane);
                    movePopup.show(primaryStage, 1180, 350);

                    // deactivate move, only one move per turn
                    move.setId("deactivatedMove");

                    // moveSubmit onClick
                    moveSubmit.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent e) {
                            RadioButton rb = (RadioButton) moveToggleGroup.getSelectedToggle();
                            System.out.println(rb.getId());
                            Room newRoom = Board.roomByName(rb.getId());
                            LocationManager.move(Deadwood.getActivePlayer(), newRoom);

                            // change scene image in that room if neccesary
                            SceneCard currentScene = newRoom.getScene();
                            if (currentScene != null) {
                                if (!currentScene.isFaceUp()) {
                                    currentScene.flip();
                                    String newLoc = "assets/images/cards/" + currentScene.getImg();
                                    Image faceUp = new Image(newLoc);
                                    scenes.get(newRoom.getName()).setImage(faceUp);
                                }
                            }
                            if (newRoom.getName().equals("office")) {
                                upgrade.setId("upgrade");
                                ImageView cur = playerDiceMap.get(Deadwood.getActivePlayer());
                                int[] xy = noRole
                                        .get(LocationManager.getPlayerLocation(Deadwood.getActivePlayer()).getName());
                                cur.setLayoutX(xy[0]);
                                cur.setLayoutY(xy[1]);

                            } else if (newRoom.getName().equals("trailer")) {
                                ImageView cur = playerDiceMap.get(Deadwood.getActivePlayer());
                                int[] xy = noRole
                                        .get(LocationManager.getPlayerLocation(Deadwood.getActivePlayer()).getName());
                                cur.setLayoutX(xy[0]);
                                cur.setLayoutY(xy[1]);
                            } else {
                                upgrade.setId("deactivatedUpgrade");
                            }

                            movePopup.hide();
                            if (newRoom.getScene() != null) {
                                // step 1: determine list of roles active player can choose from their new room
                                // setting arraylist using method in playeractions, passing active player and
                                // their new location
                                availableRoles = PlayerActions.availableRoles(Deadwood.getActivePlayer(),
                                        Board.roomByName(rb.getId()));
                                // step 2: make radio buttons using availableRoles
                                Label roleLabel = new Label("Select a role to take, or none.");
                                ToggleGroup roleTG = new ToggleGroup();
                                RadioButton role1 = new RadioButton("");
                                RadioButton role2 = new RadioButton("");
                                RadioButton role3 = new RadioButton("");
                                RadioButton role4 = new RadioButton("");
                                RadioButton role5 = new RadioButton("");
                                RadioButton role6 = new RadioButton("");
                                RadioButton role7 = new RadioButton("");
                                RadioButton none = new RadioButton("None");
                                none.setId("none");
                                role1.setToggleGroup(roleTG);
                                role2.setToggleGroup(roleTG);
                                role3.setToggleGroup(roleTG);
                                role4.setToggleGroup(roleTG);
                                role5.setToggleGroup(roleTG);
                                role6.setToggleGroup(roleTG);
                                role7.setToggleGroup(roleTG);
                                none.setToggleGroup(roleTG);
                                none.setSelected(true);

                                GridPane rolePane = new GridPane();
                                rolePane.setId("popGrid");
                                rolePane.add(roleLabel, 0, 0);
                                for (int i = 0; i < availableRoles.size(); i++) {
                                    if (i == 0) {
                                        role1.setText(availableRoles.get(i).getName());
                                        role1.setId("0");
                                        rolePane.add(role1, 0, i + 1);
                                    } else if (i == 1) {
                                        role2.setText(availableRoles.get(i).getName());
                                        role2.setId("1");
                                        rolePane.add(role2, 0, i + 1);
                                    } else if (i == 2) {
                                        role3.setText(availableRoles.get(i).getName());
                                        role3.setId("2");
                                        rolePane.add(role3, 0, i + 1);
                                    } else if (i == 3) {
                                        role4.setText(availableRoles.get(i).getName());
                                        role4.setId("3");
                                        rolePane.add(role4, 0, i + 1);
                                    } else if (i == 4) {
                                        role5.setText(availableRoles.get(i).getName());
                                        role5.setId("4");
                                        rolePane.add(role5, 0, i + 1);
                                    } else if (i == 5) {
                                        role6.setText(availableRoles.get(i).getName());
                                        role6.setId("5");
                                        rolePane.add(role6, 0, i + 1);
                                    } else if (i == 6) {
                                        role7.setText(availableRoles.get(i).getName());
                                        role7.setId("6");
                                        rolePane.add(role7, 0, i + 1);
                                    }
                                }
                                rolePane.add(none, 0, availableRoles.size() + 1);
                                Button roleSubmit = new Button("Submit");
                                roleSubmit.setId("submit");
                                rolePane.add(roleSubmit, 0, availableRoles.size() + 2);
                                Popup rolePopup = new Popup();
                                rolePopup.getContent().add(rolePane);
                                rolePopup.show(primaryStage, 1180, 220);

                                roleSubmit.setOnAction(new EventHandler<ActionEvent>() {
                                    @Override
                                    public void handle(ActionEvent e) {
                                        RadioButton chose = (RadioButton) roleTG.getSelectedToggle();
                                        ImageView cur = playerDiceMap.get(Deadwood.getActivePlayer());
                                        if (!chose.getId().equals("none")) {
                                            Role chosen = availableRoles.get(Integer.parseInt(chose.getId()));
                                            Deadwood.updateRole(chosen);
                                            Deadwood.getActivePlayer().setCanMove(false);
                                            Deadwood.getActivePlayer().setCanAct(true);
                                            Deadwood.getActivePlayer().setCanRehearse(true);
                                            currentPlayerRole.setText(chosen.getName());

                                            if (chosen.isMain()) {
                                                Room currentRoom = LocationManager
                                                        .getPlayerLocation(Deadwood.getActivePlayer());
                                                int roomX = currentRoom.getArea()[0];
                                                int roomY = currentRoom.getArea()[1];
                                                cur.setLayoutX(chosen.getArea()[0] + roomX);
                                                cur.setLayoutY(chosen.getArea()[1] + roomY);
                                            } else {
                                                cur.setLayoutX(chosen.getArea()[0]);
                                                cur.setLayoutY(chosen.getArea()[1]);
                                            }

                                        }

                                        else {

                                            int[] xy = noRole.get(LocationManager
                                                    .getPlayerLocation(Deadwood.getActivePlayer()).getName());
                                            cur.setLayoutX(xy[0]);
                                            cur.setLayoutY(xy[1]);

                                        }
                                        rolePopup.hide();
                                        // showing buttons
                                        move.setVisible(true);
                                        act.setVisible(true);
                                        upgrade.setVisible(true);
                                        rehearse.setVisible(true);
                                        endTurn.setVisible(true);

                                    }
                                });
                            } else {
                                // showing buttons
                                move.setVisible(true);
                                act.setVisible(true);
                                upgrade.setVisible(true);
                                rehearse.setVisible(true);
                                endTurn.setVisible(true);
                            }
                        }
                    });
                } else {
                    System.out.println("You can't move now.");
                }
            }
        });

        // upgrade onClick
        upgrade.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (Deadwood.getActivePlayer().getCanUpgrade()) {
                    Deadwood.getActivePlayer().setCanUpgrade(false);
                    // hiding buttons
                    endTurn.setVisible(false);
                    move.setVisible(false);
                    act.setVisible(false);
                    upgrade.setVisible(false);
                    rehearse.setVisible(false);

                    int[][] upgradeInfo = CastingOffice.validRanks(Deadwood.getActivePlayer());
                    GridPane upgradePane = new GridPane();
                    ToggleGroup upgradeToggleGroup = new ToggleGroup();
                    upgradePane.setId("popGrid");
                    Label moveLabel = new Label("Select an upgrade option:");
                    upgradePane.add(moveLabel, 0, 0);

                    Label rank2 = new Label("Rank 2");
                    Label rank3 = new Label("Rank 3");
                    Label rank4 = new Label("Rank 4");
                    Label rank5 = new Label("Rank 5");
                    Label rank6 = new Label("Rank 6");

                    upgradePane.add(rank2, 0, 1);
                    upgradePane.add(rank3, 0, 2);
                    upgradePane.add(rank4, 0, 3);
                    upgradePane.add(rank5, 0, 4);
                    upgradePane.add(rank6, 0, 5);

                    for (RadioButton rb : upgrades) {
                        int buttonRank = Integer.parseInt(rb.getId().substring(0, 1));
                        int isMoney = Integer.parseInt(rb.getId().substring(1)); // money is 0, credits is 1
                        if (upgradeInfo[buttonRank - 2][isMoney + 1] == 1) {
                            rb.setToggleGroup(upgradeToggleGroup);
                            upgradePane.add(rb, isMoney + 1, buttonRank - 1);
                        }
                    }
                    RadioButton none = new RadioButton("None");
                    none.setId("none");
                    none.setToggleGroup(upgradeToggleGroup);
                    none.setSelected(true);
                    upgradePane.add(none, 0, 6);
                    Button submitUpgrade = new Button("Submit");
                    submitUpgrade.setId("submit");
                    upgradePane.add(submitUpgrade, 0, 7);
                    Popup upgradePopup = new Popup();
                    upgradePopup.getContent().add(upgradePane);
                    upgradePopup.show(primaryStage);

                    submitUpgrade.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent e) {

                            RadioButton selectedUpgrade = (RadioButton) upgradeToggleGroup.getSelectedToggle();
                            if (!selectedUpgrade.getId().equals("none")) {
                                int buttonRank = Integer.parseInt(selectedUpgrade.getId().substring(0, 1));
                                int isMoney = Integer.parseInt(selectedUpgrade.getId().substring(1));
                                boolean upgradeWithMoney = isMoney == 0 ? true : false;
                                CastingOffice.upgrade(Deadwood.getActivePlayer(), buttonRank, upgradeWithMoney);
                                currentPlayerCredits.setText(Integer.toString(Deadwood.getActivePlayer().getCredits()));
                                currentPlayerMoney
                                        .setText("$" + Integer.toString(Deadwood.getActivePlayer().getMoney()));
                                currentPlayerRank.setText("" + Deadwood.getActivePlayer().getRank());
                            }
                            upgrade.setId("deactivatedUpgrade");
                            upgradePopup.hide();
                            // showing buttons
                            endTurn.setVisible(true);
                            move.setVisible(true);
                            act.setVisible(true);
                            upgrade.setVisible(true);
                            rehearse.setVisible(true);
                        }
                    });
                }
            }
        });

        // act onClick
        act.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Player p = Deadwood.getActivePlayer();
                Room loc = LocationManager.getPlayerLocation(p);
                String roomName = loc.getName();
                if (p.getCanAct()) {
                    // if the scene wraps and playerAct returns true, flip the
                    // scene back over, and decremetn activeScenes
                    if (PlayerActions.playerAct(p)) {
                        scenes.get(roomName).setImage(null);
                        Deadwood.decrementScenes();
                    }
                    act.setId("deactivatedAct");
                    rehearse.setId("deactivatedRehearse");
                    shotsMap.get(roomName).get(loc.getShots()).setImage(null);
                } else {
                    System.out.println("You need a role to be able to act");
                }
                // showing buttons
                endTurn.setVisible(true);
                move.setVisible(true);
                act.setVisible(true);
                upgrade.setVisible(true);
                rehearse.setVisible(true);
                // update player info
                currentPlayer.setText(p.getName());
                currentPlayerCredits.setText(Integer.toString(p.getCredits()));
                currentPlayerMoney.setText("$" + Integer.toString(p.getMoney()));
                currentPlayerRank.setText("" + p.getRank());
                currentPlayerPracticeChips.setText(Integer.toString(p.getPracticeChips()));
            }
        });

        // rehearse onClick
        rehearse.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (Deadwood.getActivePlayer().getCanRehearse()) {
                    PlayerActions.playerRehearse(Deadwood.getActivePlayer());
                    currentPlayerPracticeChips.setText(Integer.toString(Deadwood.getActivePlayer().getPracticeChips()));
                    rehearse.setId("deactivatedRehearse");
                    act.setId("deactivatedAct");
                } else {
                    System.out.println("You cant rehearse now!");
                }
            }
        });

        // endTurn onClick
        endTurn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                endTurn();
                Player p = Deadwood.getActivePlayer();
                if (!p.getCanAct()) {
                    act.setId("deactivatedAct");
                } else {
                    act.setId("act");
                }

                if (!p.getCanMove()) {
                    move.setId("deactivatedMove");
                } else {
                    move.setId("move");
                }

                if (!p.getCanRehearse()) {
                    rehearse.setId("deactivatedRehearse");
                } else {
                    rehearse.setId("rehearse");
                }

                if (!p.getCanUpgrade()) {
                    upgrade.setId("deactivatedUpgrade");
                } else {
                    upgrade.setId("upgrade");
                }
                // updating player info
                currentPlayer.setText(p.getName());
                currentPlayerCredits.setText(Integer.toString(p.getCredits()));
                currentPlayerMoney.setText("$" + Integer.toString(p.getMoney()));
                currentPlayerRank.setText("" + p.getRank());
                String roleName = (p.getCanAct() ? p.getRole().getName() : "N/A");
                currentPlayerRole.setText(roleName);
                currentPlayerPracticeChips.setText(Integer.toString(p.getPracticeChips()));

                // if next day time
                if (Deadwood.getNumActiveScenes() == 1) {
                    // iterate through and set each scene image back to the cardback
                    for (Map.Entry<String, ImageView> entry : scenes.entrySet()) {
                        entry.getValue().setImage(cardBack);

                    }
                    // iterate through and reset each shot counter
                    for (Map.Entry<String, ArrayList<ImageView>> entry : shotsMap.entrySet()) {
                        ArrayList<ImageView> temp = entry.getValue();
                        for (int i = 0; i < temp.size(); i++) {
                            temp.get(i).setImage(shot);
                        }
                    }
                    Deadwood.endDay();

                }
            }
        });

        // adding buttons to grid pane
        actions.add(move, 0, 0);
        actions.add(upgrade, 1, 0);
        actions.add(act, 0, 1);
        actions.add(rehearse, 1, 1);
        actions.add(endTurn, 0, 2, 2, 1);

        ui.getChildren().add(actions);
        back.getChildren().add(ui);

        GridPane popGrid = new GridPane();
        // creating 7 radio buttons and adding them to a toggle group
        Label pc = new Label("Select a player count:");
        ToggleGroup group = new ToggleGroup();
        RadioButton r2 = new RadioButton("2 Players");
        RadioButton r3 = new RadioButton("3 Players");
        RadioButton r4 = new RadioButton("4 Players");
        RadioButton r5 = new RadioButton("5 Players");
        RadioButton r6 = new RadioButton("6 Players");
        RadioButton r7 = new RadioButton("7 Players");
        RadioButton r8 = new RadioButton("8 Players");
        Button countSubmit = new Button("Submit");
        countSubmit.setId("submit");

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
        popGrid.add(countSubmit, 0, 8);

        popGrid.setId("popGrid");
        // create a popup
        Popup countPopup = new Popup();
        countPopup.getContent().add(popGrid);
        EventHandler<ActionEvent> playerPopup = new EventHandler<ActionEvent>() {

            public void handle(ActionEvent e) {
                // hiding buttons
                endTurn.setVisible(false);
                move.setVisible(false);
                act.setVisible(false);
                upgrade.setVisible(false);
                rehearse.setVisible(false);
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
                Button nameSubmit = new Button("Submit");
                nameSubmit.setId("submit");
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
                popGrid2.add(nameSubmit, 0, playerCount + 1);
                Popup namePopup = new Popup();
                namePopup.getContent().add(popGrid2);
                namePopup.show(primaryStage);

                nameSubmit.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {
                        names.add(tf1.getText());
                        names.add(tf2.getText());
                        for (int j = 3; j <= innerPlayerCount; j++) {
                            if (j == 3) {
                                names.add(tf3.getText());
                            } else if (j == 4) {
                                names.add(tf4.getText());
                            } else if (j == 5) {
                                names.add(tf5.getText());
                            } else if (j == 6) {
                                names.add(tf6.getText());
                            } else if (j == 7) {
                                names.add(tf7.getText());
                            } else if (j == 8) {
                                names.add(tf8.getText());
                            }
                        }
                        Deadwood.initializePlayers(names);

                        // setting up dice
                        for (int i = 0; i < Deadwood.getPlayers().length; i++) {
                            Player p = Deadwood.getPlayers()[i];
                            int index = p.getIconIndex();
                            int rank = p.getRank();
                            diceArray[i] = new Image(basePath + Deadwood.iconNames[index][rank - 1], 0, 0, true, true);
                            diceViewArray[i] = new ImageView(diceArray[i]);
                            playerDiceMap.put(p, diceViewArray[i]);
                        }
                        int count = 0;
                        for (Map.Entry<Player, ImageView> entry : playerDiceMap.entrySet()) {
                            // Player p = entry.getKey();
                            ImageView cur = entry.getValue();
                            cur.setLayoutX(991 + count * 20);
                            cur.setLayoutY(280);
                            cur.setPreserveRatio(true);
                            cur.setFitHeight(46);
                            root.getChildren().add(cur);
                            count += 1;
                        }

                        rehearse.setId("deactivatedRehearse");
                        act.setId("deactivatedAct");
                        upgrade.setId("deactivatedUpgrade");
                        currentPlayer.setText(Deadwood.getActivePlayer().getName());
                        currentPlayerCredits.setText(Integer.toString(Deadwood.getActivePlayer().getCredits()));
                        currentPlayerMoney.setText("$" + Integer.toString(Deadwood.getActivePlayer().getMoney()));
                        currentPlayerRole.setText("N/A");
                        currentPlayerPracticeChips.setText("0");

                        namePopup.hide();
                        // showing buttons
                        endTurn.setVisible(true);
                        move.setVisible(true);
                        act.setVisible(true);
                        upgrade.setVisible(true);
                        rehearse.setVisible(true);
                        currentPlayerRank.setText("" + Deadwood.getActivePlayer().getRank());
                    }
                });
                countPopup.hide();
            }
        };

        countSubmit.setOnAction(playerPopup);
        Scene mainScene = new Scene(root, 1725, 900);
        mainScene.getStylesheets().add("/assets/css/style.css");
        // mainScene.getStylesheets().add("temp/style.css");
        primaryStage.setTitle("Deadwood");
        primaryStage.setScene(mainScene);
        primaryStage.show();
        countPopup.show(primaryStage);
        // hiding buttons
        endTurn.setVisible(false);
        move.setVisible(false);
        act.setVisible(false);
        upgrade.setVisible(false);
        rehearse.setVisible(false);
    }

    public void endTurn() {
        Deadwood.endTurn();
        System.out.println("endTurn clicked");
    }

    // method to create radio buttons for all rooms for moving
    public void rooms() {
        ToggleGroup locationsGroup = new ToggleGroup();
        RadioButton office = new RadioButton("Casting Office");
        RadioButton trailer = new RadioButton("Trailer");
        RadioButton hotel = new RadioButton("Hotel");
        RadioButton saloon = new RadioButton("Saloon");
        RadioButton church = new RadioButton("Church");
        RadioButton ranch = new RadioButton("Ranch");
        RadioButton mainStreet = new RadioButton("Main Street");
        RadioButton secretHideout = new RadioButton("Secret Hideout");
        RadioButton bank = new RadioButton("Bank");
        RadioButton trainStation = new RadioButton("Train Station");
        RadioButton jail = new RadioButton("Jail");
        RadioButton generalStore = new RadioButton("General Store");

        office.setToggleGroup(locationsGroup);
        trailer.setToggleGroup(locationsGroup);
        hotel.setToggleGroup(locationsGroup);
        saloon.setToggleGroup(locationsGroup);
        church.setToggleGroup(locationsGroup);
        ranch.setToggleGroup(locationsGroup);
        mainStreet.setToggleGroup(locationsGroup);
        secretHideout.setToggleGroup(locationsGroup);
        bank.setToggleGroup(locationsGroup);
        trainStation.setToggleGroup(locationsGroup);
        jail.setToggleGroup(locationsGroup);
        generalStore.setToggleGroup(locationsGroup);

        office.setId("office");
        trailer.setId("trailer");
        hotel.setId("Hotel");
        saloon.setId("Saloon");
        church.setId("Church");
        ranch.setId("Ranch");
        mainStreet.setId("Main Street");
        secretHideout.setId("Secret Hideout");
        bank.setId("Bank");
        trainStation.setId("Train Station");
        jail.setId("Jail");
        generalStore.setId("General Store");

        ArrayList<RadioButton> locationButtons = new ArrayList<RadioButton>();
        locationButtons.add(office);
        locationButtons.add(trailer);
        locationButtons.add(hotel);
        locationButtons.add(saloon);
        locationButtons.add(church);
        locationButtons.add(ranch);
        locationButtons.add(mainStreet);
        locationButtons.add(secretHideout);
        locationButtons.add(bank);
        locationButtons.add(trainStation);
        locationButtons.add(jail);
        locationButtons.add(generalStore);
        View.locations = locationButtons;
    }

    // mwthod for creating upgrade radio buttons
    public void upgrades() {
        // ToggleGroup locationsGroup = new ToggleGroup();
        RadioButton m2 = new RadioButton("4 Dollars");
        RadioButton m3 = new RadioButton("10 Dollars");
        RadioButton m4 = new RadioButton("18 Dollars");
        RadioButton m5 = new RadioButton("28 Dollars");
        RadioButton m6 = new RadioButton("40 Dollars");
        RadioButton c2 = new RadioButton("5 Credits");
        RadioButton c3 = new RadioButton("10 Credits");
        RadioButton c4 = new RadioButton("15 Credits");
        RadioButton c5 = new RadioButton("20 Credits");
        RadioButton c6 = new RadioButton("25 Credits");

        m2.setId("20");
        m3.setId("30");
        m4.setId("40");
        m5.setId("50");
        m6.setId("60");
        c2.setId("21");
        c3.setId("31");
        c4.setId("41");
        c5.setId("51");
        c6.setId("61");

        /*
         * m2.setToggleGroup(locationsGroup);
         * m3.setToggleGroup(locationsGroup);
         * m4.setToggleGroup(locationsGroup);
         * m5.setToggleGroup(locationsGroup);
         * m6.setToggleGroup(locationsGroup);
         * c2.setToggleGroup(locationsGroup);
         * c3.setToggleGroup(locationsGroup);
         * c4.setToggleGroup(locationsGroup);
         * c5.setToggleGroup(locationsGroup);
         * c6.setToggleGroup(locationsGroup);
         */

        ArrayList<RadioButton> upgradeButtons = new ArrayList<RadioButton>();
        upgradeButtons.add(m2);
        upgradeButtons.add(m3);
        upgradeButtons.add(m4);
        upgradeButtons.add(m5);
        upgradeButtons.add(m6);
        upgradeButtons.add(c2);
        upgradeButtons.add(c3);
        upgradeButtons.add(c4);
        upgradeButtons.add(c5);
        upgradeButtons.add(c6);
        View.upgrades = upgradeButtons;

    }
}