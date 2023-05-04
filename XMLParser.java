import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import java.util.ArrayList;

public class XMLParser {

    public static void main(String args[]) {

        Document cardDoc = null;
        Document boardDoc = null;
        try {
            cardDoc = getDocFromFile("xml/cards.xml");
            boardDoc = getDocFromFile("xml/board.xml");
            // readCardData(cardDoc);
            readBoardData(boardDoc);

        } catch (Exception e) {
            System.out.println("Error = " + e);
        }
    }

    public static Document getDocFromFile(String filename)
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

    public static ArrayList<Scene> readCardData(Document d) {
        ArrayList<Scene> scenes = new ArrayList<Scene>();
        Role roleHolder = null;
        ArrayList<Role> roles = new ArrayList<Role>();
        ArrayList<ArrayList<Role>> rolesLists = new ArrayList<ArrayList<Role>>(40);

        int[] areas = new int[4];
        Element root = d.getDocumentElement();
        NodeList cards = root.getElementsByTagName("card");

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

                        } else if ("line".equals(partSub.getNodeName())) {
                            partLine = partSub.getTextContent();
                        }

                    }
                    int[] aCopy = new int[4];
                    System.arraycopy(areas, 0, aCopy, 0, 4);
                    roleHolder = new Role(partName, partLine, partLvl, aCopy, true);
                    roles.add(roleHolder);
                }

            }

            ArrayList<Role> alCopy = new ArrayList<Role>(roles);
            rolesLists.add(i, alCopy);
            Scene sceneHolder = new Scene(cardName, sceneText, budget, sceneNum, rolesLists.get(i));
            scenes.add(sceneHolder);
            roles.clear();
        }
        // To witness content in String form
        System.out.println("Scenes.size() = " + scenes.size());
        for (int a = 0; a < scenes.size(); a++) {
            System.out.println("Scene at index " + a + " is: " +
                    scenes.get(a).toString());
        }

        return scenes;
    }

    public static Room[] readBoardData(Document d) {
        Element root = d.getDocumentElement();
        NodeList sets = root.getElementsByTagName("set");
        NodeList trailer = root.getElementsByTagName("trailer");
        NodeList office = root.getElementsByTagName("office");

        Room[] rooms = new Room[12];
        String setName = "", line = "";
        int partLvl = 0, partX = 0, partY = 0, partW = 0, partH = 0, sceneX = 0, sceneY = 0, sceneH = 0, sceneW = 0,
                takeX = 0, takeY = 0, takeH = 0, takeW = 0;

        System.out.println("Printing information for trailer.");
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
                        // need to find out why I need to divide by two here, I have no idea why k
                        // 'misses' on values 0, 2, 4, 6... (even values)
                        System.out.println("Trailer neighbor " + ((k + 1) / 2) + " = " + neighbor);
                    }
                }
            } else if ("area".equals(sub.getNodeName())) {
                sceneX = Integer.parseInt(sub.getAttributes().getNamedItem("x").getNodeValue());
                System.out.print("Scene Dimensions: x=" + sceneX);
                sceneY = Integer.parseInt(sub.getAttributes().getNamedItem("y").getNodeValue());
                System.out.print(" | y=" + sceneY);
                sceneH = Integer.parseInt(sub.getAttributes().getNamedItem("h").getNodeValue());
                System.out.print(" | h=" + sceneH);
                sceneW = Integer.parseInt(sub.getAttributes().getNamedItem("w").getNodeValue());
                System.out.println(" | w=" + sceneW);
            }
        }

        Node of = office.item(0);
        NodeList ofChildren = of.getChildNodes();
        
        for (int o = 0; o <ofChildren.getLength(); o++) {
            Node sub = ofChildren.item(o);

            if ("neighbors".equals(sub.getNodeName())) {

                NodeList neighborChildren = sub.getChildNodes();
                for (int k = 0; k < neighborChildren.getLength(); k++) {
                    Node neighborSub = neighborChildren.item(k);

                    if ("neighbor".equals(neighborSub.getNodeName())) {
                        String neighbor = neighborSub.getAttributes().getNamedItem("name").getNodeValue();
                        // need to find out why I need to divide by two here, I have no idea why k
                        // 'misses' on values 0, 2, 4, 6... (even values)
                        System.out.println("Office neighbor " + ((k + 1) / 2) + " = " + neighbor);
                    }
                }
            } else if ("area".equals(sub.getNodeName())) {
                sceneX = Integer.parseInt(sub.getAttributes().getNamedItem("x").getNodeValue());
                System.out.print("Scene Dimensions: x=" + sceneX);
                sceneY = Integer.parseInt(sub.getAttributes().getNamedItem("y").getNodeValue());
                System.out.print(" | y=" + sceneY);
                sceneH = Integer.parseInt(sub.getAttributes().getNamedItem("h").getNodeValue());
                System.out.print(" | h=" + sceneH);
                sceneW = Integer.parseInt(sub.getAttributes().getNamedItem("w").getNodeValue());
                System.out.println(" | w=" + sceneW);
            }
        }

        for (int i = 0; i < sets.getLength(); i++) {
            System.out.println("Printing information for set " + (i + 1));

            Node set = sets.item(i);
            NodeList children = set.getChildNodes();
            setName = set.getAttributes().getNamedItem("name").getNodeValue();
            System.out.println("Set Name = " + setName);

            for (int j = 0; j < children.getLength(); j++) {
                Node sub = children.item(j);

                if ("neighbors".equals(sub.getNodeName())) {

                    NodeList neighborChildren = sub.getChildNodes();
                    for (int k = 0; k < neighborChildren.getLength(); k++) {
                        Node neighborSub = neighborChildren.item(k);

                        if ("neighbor".equals(neighborSub.getNodeName())) {
                            String neighbor = neighborSub.getAttributes().getNamedItem("name").getNodeValue();
                            // need to find out why I need to divide by two here, I have no idea why k
                            // 'misses' on values 0, 2, 4, 6... (even values)
                            System.out.println("neighbor " + ((k + 1) / 2) + " = " + neighbor);
                        }
                    }
                } else if ("area".equals(sub.getNodeName())) {
                    sceneX = Integer.parseInt(sub.getAttributes().getNamedItem("x").getNodeValue());
                    System.out.print("Scene Dimensions: x=" + sceneX);
                    sceneY = Integer.parseInt(sub.getAttributes().getNamedItem("y").getNodeValue());
                    System.out.print(" | y=" + sceneY);
                    sceneH = Integer.parseInt(sub.getAttributes().getNamedItem("h").getNodeValue());
                    System.out.print(" | h=" + sceneH);
                    sceneW = Integer.parseInt(sub.getAttributes().getNamedItem("w").getNodeValue());
                    System.out.println(" | w=" + sceneW);
                } else if ("takes".equals(sub.getNodeName())) {
                    NodeList takeChildren = sub.getChildNodes();
                    for (int k = 0; k < takeChildren.getLength(); k++) {
                        Node takeSub = takeChildren.item(k);

                        if ("take".equals(takeSub.getNodeName())) {
                            String takeNum = takeSub.getAttributes().getNamedItem("number").getNodeValue();
                            // need to find out why I need to divide by two here, I have no idea why k
                            // 'misses' on values 0, 2, 4, 6... (even values)
                            System.out.println("take " + ((k + 1) / 2) + " = " + takeNum);

                            NodeList takeGrandchildren = takeSub.getChildNodes();
                            for (int h = 0; h < takeGrandchildren.getLength(); h++) {
                                Node takeSubSub = takeGrandchildren.item(h);
                                if ("area".equals(takeSubSub.getNodeName())) {
                                    takeX = Integer
                                            .parseInt(takeSubSub.getAttributes().getNamedItem("x").getNodeValue());
                                    System.out.print("Take Dimensions: x=" + takeX);
                                    takeY = Integer
                                            .parseInt(takeSubSub.getAttributes().getNamedItem("y").getNodeValue());
                                    System.out.print(" | y=" + takeY);
                                    takeH = Integer
                                            .parseInt(takeSubSub.getAttributes().getNamedItem("h").getNodeValue());
                                    System.out.print(" | h=" + takeH);
                                    takeW = Integer
                                            .parseInt(takeSubSub.getAttributes().getNamedItem("w").getNodeValue());
                                    System.out.println(" | w=" + takeW);
                                }
                            }
                        }
                    }
                } else if ("parts".equals(sub.getNodeName())) {
                    NodeList partChildren = sub.getChildNodes();
                    for (int k = 0; k < partChildren.getLength(); k++) {
                        Node partSub = partChildren.item(k);

                        if ("part".equals(partSub.getNodeName())) {
                            String partName = partSub.getAttributes().getNamedItem("name").getNodeValue();
                            // need to find out why I need to divide by two here, I have no idea why k
                            // 'misses' on values 0, 2, 4, 6... (even values)
                            System.out.println("partName " + ((k + 1) / 2) + " = " + partName);
                            partLvl = Integer.parseInt(partSub.getAttributes().getNamedItem("level").getNodeValue());
                            System.out.println("partLvl " + ((k + 1) / 2) + " = " + partLvl);

                            NodeList partGrandchildren = partSub.getChildNodes();
                            for (int h = 0; h < partGrandchildren.getLength(); h++) {
                                Node partSubSub = partGrandchildren.item(h);
                                if ("area".equals(partSubSub.getNodeName())) {
                                    partX = Integer
                                            .parseInt(partSubSub.getAttributes().getNamedItem("x").getNodeValue());
                                    System.out.print("Part Dimensions: x=" + partX);
                                    partY = Integer
                                            .parseInt(partSubSub.getAttributes().getNamedItem("y").getNodeValue());
                                    System.out.print(" | y=" + partY);
                                    partH = Integer
                                            .parseInt(partSubSub.getAttributes().getNamedItem("h").getNodeValue());
                                    System.out.print(" | h=" + partH);
                                    partW = Integer
                                            .parseInt(partSubSub.getAttributes().getNamedItem("w").getNodeValue());
                                    System.out.println(" | w=" + partW);

                                } else if ("line".equals(partSubSub.getNodeName())) {
                                    line = partSubSub.getTextContent();
                                    System.out.println("partLine = " + line);
                                }
                            }
                        }
                    }
                }
            }
        }
        return rooms;
    }
}