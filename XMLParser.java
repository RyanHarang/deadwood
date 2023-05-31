import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.util.ArrayList;

public class XMLParser {
    private Document cardDoc = null;
    private Document boardDoc = null;

    public XMLParser() {
        setCardDoc("assets/xml/cards.xml");
        setBoardDoc("assets/xml/board.xml");
    }

    private void setCardDoc(String fileName) {
        try {
            cardDoc = getDocFromFile(fileName);
        } catch (Exception e) {
            System.out.println("Error = " + e);
        }
    }

    private void setBoardDoc(String fileName) {
        try {
            boardDoc = getDocFromFile(fileName);
        } catch (Exception e) {
            System.out.println("Error = " + e);
        }
    }

    private Document getDocFromFile(String filename)
            throws ParserConfigurationException {
        {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = null;
            try {
                doc = db.parse(filename);
            } catch (Exception ex) {
                System.out.println("XML parse failure");
                ex.printStackTrace();
            }
            return doc;
        } // exception handling
    }

    public ArrayList<SceneCard> readCardData() {
        Element root = cardDoc.getDocumentElement();
        NodeList cards = root.getElementsByTagName("card");
        ArrayList<SceneCard> scenes = new ArrayList<SceneCard>();
        Role roleHolder = null;
        ArrayList<Role> roles = new ArrayList<Role>();
        ArrayList<ArrayList<Role>> rolesLists = new ArrayList<ArrayList<Role>>(40);
        int[] areas = new int[4];

        for (int i = 0; i < cards.getLength(); i++) {
            Node card = cards.item(i);
            NodeList children = card.getChildNodes();
            String cardName = "", img = "", sceneText = "", partName = "", partLine = "";
            int budget = 0, sceneNum = 0, partLvl = 0, partX = 0, partY = 0, partH = 0, partW = 0;
            cardName = card.getAttributes().getNamedItem("name").getNodeValue();
            img = card.getAttributes().getNamedItem("img").getNodeValue();
            budget = Integer.parseInt(card.getAttributes().getNamedItem("budget").getNodeValue());
            for (int j = 0; j < children.getLength(); j++) {
                Node sub = children.item(j);
                if ("scene".equals(sub.getNodeName())) {
                    sceneNum = Integer.parseInt(sub.getAttributes().getNamedItem("number").getNodeValue());
                    sceneText = sub.getTextContent();
                } else if ("part".equals(sub.getNodeName())) {
                    partName = sub.getAttributes().getNamedItem("name").getNodeValue();
                    partLvl = Integer.parseInt(sub.getAttributes().getNamedItem("level").getNodeValue());
                    NodeList partChildren = sub.getChildNodes();
                    for (int k = 0; k < partChildren.getLength(); k++) {
                        Node partSub = partChildren.item(k);

                        if ("area".equals(partSub.getNodeName())) {
                            partX = Integer.parseInt(partSub.getAttributes().getNamedItem("x").getNodeValue());
                            partY = Integer.parseInt(partSub.getAttributes().getNamedItem("y").getNodeValue());
                            partH = Integer.parseInt(partSub.getAttributes().getNamedItem("h").getNodeValue());
                            partW = Integer.parseInt(partSub.getAttributes().getNamedItem("w").getNodeValue());
                            areas[0] = partX;
                            areas[1] = partY;
                            areas[2] = partH;
                            areas[3] = partW;
                        } else

                        if ("line".equals(partSub.getNodeName())) {
                            partLine = partSub.getTextContent();
                        }
                    }

                    int[] aCopy = new int[4];
                    System.arraycopy(areas, 0, aCopy, 0, 4);
                    roleHolder = new Role(partName, partLine, partLvl, aCopy, true);
                    // roleHolder = new Role(partName, partLine, partLvl, true);
                    roles.add(roleHolder);
                }
            }
            ArrayList<Role> alCopy = new ArrayList<Role>(roles);
            rolesLists.add(i, alCopy);
            SceneCard sceneHolder = new SceneCard(img, cardName, sceneText, budget, sceneNum, rolesLists.get(i));
            scenes.add(sceneHolder);
            roles.clear();
        }
        return scenes;
    }

    public Room[] readBoardData() {
        Element root = boardDoc.getDocumentElement();
        NodeList sets = root.getElementsByTagName("set");
        NodeList trailer = root.getElementsByTagName("trailer");
        NodeList office = root.getElementsByTagName("office");
        ArrayList<ArrayList<String>> allAdjs = new ArrayList<ArrayList<String>>();
        ArrayList<String> adjs = new ArrayList<String>();
        ArrayList<Role> roles = new ArrayList<Role>();
        Room[] rooms = new Room[12];
        int[] areas = new int[4], sceneAreas = new int[4];
        String partName = "", setName = "", line = "";
        int partLvl = 0, partX = 0, partY = 0, partH = 0, partW = 0, takes = 0,
                takeX = 0, takeY = 0, takeH = 0, takeW = 0,
                sceneX = 0, sceneY = 0, sceneH = 0, sceneW = 0;
        // creating a trailer room specially at index 0 of the rooms array
        Node tr = trailer.item(0);
        NodeList trChildren = tr.getChildNodes();
        for (int t = 0; t < trChildren.getLength(); t++) {
            Node sub = trChildren.item(t);
            if ("neighbors".equals(sub.getNodeName())) {
                NodeList neighborChildren = sub.getChildNodes();
                for (int k = 0; k < neighborChildren.getLength(); k++) {
                    Node neighborSub = neighborChildren.item(k);
                    if ("neighbor".equals(neighborSub.getNodeName())) {
                        String neighbor = neighborSub.getAttributes().getNamedItem("name").getNodeValue();
                        adjs.add(neighbor);
                    }
                }
                ArrayList<String> alCopy = new ArrayList<String>(adjs);
                allAdjs.add(alCopy);
                adjs.clear();
            } else if ("area".equals(sub.getNodeName())) {
                sceneX = Integer.parseInt(sub.getAttributes().getNamedItem("x").getNodeValue());
                sceneAreas[0] = sceneX;
                sceneY = Integer.parseInt(sub.getAttributes().getNamedItem("y").getNodeValue());
                sceneAreas[1] = sceneY;
                sceneH = Integer.parseInt(sub.getAttributes().getNamedItem("h").getNodeValue());
                sceneAreas[2] = sceneH;
                sceneW = Integer.parseInt(sub.getAttributes().getNamedItem("w").getNodeValue());
                sceneAreas[3] = sceneW;
                int[] aCopy = new int[4];
                System.arraycopy(sceneAreas, 0, aCopy, 0, 4);
                rooms[0] = new Room(0, aCopy, "trailer", null);
                // rooms[0] = new Room(0, "trailer", null);
            }
        }
        // creating an office room speciall at index 1 in the room array
        Node of = office.item(0);
        NodeList ofChildren = of.getChildNodes();
        for (int o = 0; o < ofChildren.getLength(); o++) {
            Node sub = ofChildren.item(o);
            if ("neighbors".equals(sub.getNodeName())) {
                NodeList neighborChildren = sub.getChildNodes();
                for (int k = 0; k < neighborChildren.getLength(); k++) {
                    Node neighborSub = neighborChildren.item(k);
                    if ("neighbor".equals(neighborSub.getNodeName())) {
                        String neighbor = neighborSub.getAttributes().getNamedItem("name").getNodeValue();
                        adjs.add(neighbor);
                    }
                }
                ArrayList<String> alCopy = new ArrayList<String>(adjs);
                allAdjs.add(alCopy);
                adjs.clear();
            } else if ("area".equals(sub.getNodeName())) {
                sceneX = Integer.parseInt(sub.getAttributes().getNamedItem("x").getNodeValue());
                sceneAreas[0] = sceneX;
                sceneY = Integer.parseInt(sub.getAttributes().getNamedItem("y").getNodeValue());
                sceneAreas[1] = sceneY;
                sceneH = Integer.parseInt(sub.getAttributes().getNamedItem("h").getNodeValue());
                sceneAreas[2] = sceneH;
                sceneW = Integer.parseInt(sub.getAttributes().getNamedItem("w").getNodeValue());
                sceneAreas[3] = sceneW;
                int[] aCopy = new int[4];
                System.arraycopy(sceneAreas, 0, aCopy, 0, 4);
                rooms[1] = new Room(0, aCopy, "office", null);
                // rooms[1] = new Room(0, "office", null);
            }
        }
        for (int i = 0; i < sets.getLength(); i++) {
            Node set = sets.item(i);
            NodeList children = set.getChildNodes();
            setName = set.getAttributes().getNamedItem("name").getNodeValue();
            for (int j = 0; j < children.getLength(); j++) {
                Node sub = children.item(j);
                if ("neighbors".equals(sub.getNodeName())) {
                    NodeList neighborChildren = sub.getChildNodes();
                    for (int k = 0; k < neighborChildren.getLength(); k++) {
                        Node neighborSub = neighborChildren.item(k);
                        if ("neighbor".equals(neighborSub.getNodeName())) {
                            String neighbor = neighborSub.getAttributes().getNamedItem("name").getNodeValue();
                            adjs.add(neighbor);
                        }
                    }
                    ArrayList<String> alCopy = new ArrayList<String>(adjs);
                    allAdjs.add(alCopy);
                    adjs.clear();
                } else if ("area".equals(sub.getNodeName())) {
                    sceneX = Integer.parseInt(sub.getAttributes().getNamedItem("x").getNodeValue());
                    sceneAreas[0] = sceneX;
                    sceneY = Integer.parseInt(sub.getAttributes().getNamedItem("y").getNodeValue());
                    sceneAreas[1] = sceneY;
                    sceneH = Integer.parseInt(sub.getAttributes().getNamedItem("h").getNodeValue());
                    sceneAreas[2] = sceneH;
                    sceneW = Integer.parseInt(sub.getAttributes().getNamedItem("w").getNodeValue());
                    sceneAreas[3] = sceneW;
                } else if ("takes".equals(sub.getNodeName())) {
                    NodeList takeChildren = sub.getChildNodes();
                    for (int k = 0; k < takeChildren.getLength(); k++) {
                        Node takeSub = takeChildren.item(k);
                        if ("take".equals(takeSub.getNodeName())) {
                            takes++;

                            NodeList takeGrandchildren = takeSub.getChildNodes();
                            for (int h = 0; h < takeGrandchildren.getLength(); h++) {
                                Node takeSubSub = takeGrandchildren.item(h);
                                if ("area".equals(takeSubSub.getNodeName())) {
                                    takeX = Integer
                                            .parseInt(takeSubSub.getAttributes().getNamedItem("x").getNodeValue());
                                    areas[0] = takeX;
                                    takeY = Integer
                                            .parseInt(takeSubSub.getAttributes().getNamedItem("y").getNodeValue());
                                    areas[1] = takeY;
                                    takeH = Integer
                                            .parseInt(takeSubSub.getAttributes().getNamedItem("h").getNodeValue());
                                    areas[2] = takeH;
                                    takeW = Integer
                                            .parseInt(takeSubSub.getAttributes().getNamedItem("w").getNodeValue());
                                    areas[3] = takeW;
                                }
                            }
                        }
                    }
                } else if ("parts".equals(sub.getNodeName())) {
                    NodeList partChildren = sub.getChildNodes();
                    for (int k = 0; k < partChildren.getLength(); k++) {
                        Node partSub = partChildren.item(k);
                        if ("part".equals(partSub.getNodeName())) {
                            partName = partSub.getAttributes().getNamedItem("name").getNodeValue();
                            partLvl = Integer.parseInt(partSub.getAttributes().getNamedItem("level").getNodeValue());
                            NodeList partGrandchildren = partSub.getChildNodes();
                            for (int h = 0; h < partGrandchildren.getLength(); h++) {
                                Node partSubSub = partGrandchildren.item(h);

                                if ("area".equals(partSubSub.getNodeName())) {
                                    partX = Integer
                                            .parseInt(partSubSub.getAttributes().getNamedItem("x").getNodeValue());
                                    areas[0] = partX;
                                    partY = Integer
                                            .parseInt(partSubSub.getAttributes().getNamedItem("y").getNodeValue());
                                    areas[1] = partY;
                                    partH = Integer
                                            .parseInt(partSubSub.getAttributes().getNamedItem("h").getNodeValue());
                                    areas[2] = partH;
                                    partW = Integer
                                            .parseInt(partSubSub.getAttributes().getNamedItem("w").getNodeValue());
                                    areas[3] = partW;

                                } else if ("line".equals(partSubSub.getNodeName())) {
                                    line = partSubSub.getTextContent();
                                }
                            }
                            int[] areaCopy = new int[4];
                            System.arraycopy(areas, 0, areaCopy, 0, 4);
                            Role roleHolder = new Role(partName, line, partLvl, areaCopy, false);
                            // Role roleHolder = new Role(partName, line, partLvl, false);
                            roles.add(roleHolder);
                        }
                    }
                }
            }
            ArrayList<Role> roleCopy = new ArrayList<Role>(roles);
            int[] sceneAreaCopy = new int[4];
            System.arraycopy(sceneAreas, 0, sceneAreaCopy, 0, 4);
            rooms[i + 2] = new Room(takes, sceneAreaCopy, setName, roleCopy);
            // rooms[i + 2] = new Room(takes, setName, roleCopy);
            takes = 0;
            roles.clear();
        }
        ArrayList<Room> neighbors = new ArrayList<Room>();
        // now assigning neighbors to rooms using previously created allAdjs
        for (int h = 0; h < rooms.length; h++) {
            // loop through rooms, and for each room, get neighbors from allAdjs
            ArrayList<String> temp = allAdjs.get(h);
            for (int j = 0; j < temp.size(); j++) {
                // for each room adjacent to room at index h of array
                // loop through room array to find room with same name and
                // add it to neighbors array
                // create copy of neighbors array and pass to setAdjacents for each room
                for (int w = 0; w < rooms.length; w++) {
                    if (rooms[w].getName().equals(temp.get(j))) {
                        neighbors.add(rooms[w]);
                    }
                }
            }
            ArrayList<Room> neighborCopy = new ArrayList<Room>(neighbors);
            rooms[h].setAdjacents(neighborCopy);
            neighbors.clear();
        }
        for (Room room : rooms) {
            System.out.println(room.toString());
        }
        return rooms;
    }
}