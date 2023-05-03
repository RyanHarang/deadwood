import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;

public class XMLParser {

    public static void main(String args[]) {

        Document cardDoc = null;
        Document boardDoc = null;
        try {
            cardDoc = getDocFromFile("xml/cards.xml");
            boardDoc = getDocFromFile("xml/board.xml");
            readCardData(cardDoc);
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

    public static void readCardData(Document d) {
        Element root = d.getDocumentElement();
        NodeList cards = root.getElementsByTagName("card");

        for (int i = 0; i < cards.getLength(); i++) {
            System.out.println("Printing information for card " + (i + 1));

            Node card = cards.item(i);
            NodeList children = card.getChildNodes();
            String cardName = card.getAttributes().getNamedItem("name").getNodeValue();
            System.out.println("Card Name = " + cardName);
            String img = card.getAttributes().getNamedItem("img").getNodeValue();
            System.out.println("img = " + img);
            String budget = card.getAttributes().getNamedItem("budget").getNodeValue();
            System.out.println("Budget = " + budget);

            for (int j = 0; j < children.getLength(); j++) {
                Node sub = children.item(j);

                if ("scene".equals(sub.getNodeName())) {
                    String sceneNum = sub.getAttributes().getNamedItem("number").getNodeValue();
                    System.out.println("sceneNum = " + sceneNum);
                    String sceneText = sub.getTextContent();
                    System.out.println("sceneText = " + sceneText);

                } else if ("part".equals(sub.getNodeName())) {
                    String partName = sub.getAttributes().getNamedItem("name").getNodeValue();
                    System.out.println("partName = " + partName);
                    String partLvl = sub.getAttributes().getNamedItem("level").getNodeValue();
                    System.out.println("partLvl = " + partLvl);

                    NodeList partChildren = sub.getChildNodes();
                    for (int k = 0; k < partChildren.getLength(); k++) {
                        Node partSub = partChildren.item(k);
                        if ("area".equals(partSub.getNodeName())) {
                            String partX = partSub.getAttributes().getNamedItem("x").getNodeValue();
                            System.out.print("x=" + partX);
                            String partY = partSub.getAttributes().getNamedItem("y").getNodeValue();
                            System.out.print(" | y=" + partY);
                            String partH = partSub.getAttributes().getNamedItem("h").getNodeValue();
                            System.out.print(" | h=" + partH);
                            String partW = partSub.getAttributes().getNamedItem("w").getNodeValue();
                            System.out.println(" | w=" + partW);

                        } else if ("line".equals(partSub.getNodeName())) {
                            String partLine = partSub.getTextContent();
                            System.out.println("partLine = " + partLine);
                        }
                    }
                }
            }
        }
    }

    public static void readBoardData(Document d) {
        Element root = d.getDocumentElement();
        NodeList sets = root.getElementsByTagName("set");

        for (int i = 0; i < sets.getLength(); i++) {
            System.out.println("Printing information for set " + (i + 1));

            Node set = sets.item(i);
            NodeList children = set.getChildNodes();
            String setName = set.getAttributes().getNamedItem("name").getNodeValue();
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
                    String sceneX = sub.getAttributes().getNamedItem("x").getNodeValue();
                    System.out.print("Scene Dimensions: x=" + sceneX);
                    String sceneY = sub.getAttributes().getNamedItem("y").getNodeValue();
                    System.out.print(" | y=" + sceneY);
                    String sceneH = sub.getAttributes().getNamedItem("h").getNodeValue();
                    System.out.print(" | h=" + sceneH);
                    String sceneW = sub.getAttributes().getNamedItem("w").getNodeValue();
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
                                    String takeX = takeSubSub.getAttributes().getNamedItem("x").getNodeValue();
                                    System.out.print("Take Dimensions: x=" + takeX);
                                    String takeY = takeSubSub.getAttributes().getNamedItem("y").getNodeValue();
                                    System.out.print(" | y=" + takeY);
                                    String takeH = takeSubSub.getAttributes().getNamedItem("h").getNodeValue();
                                    System.out.print(" | h=" + takeH);
                                    String takeW = takeSubSub.getAttributes().getNamedItem("w").getNodeValue();
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
                            String partLvl = partSub.getAttributes().getNamedItem("level").getNodeValue();
                            System.out.println("partLvl " + ((k + 1) / 2) + " = " + partLvl);

                            NodeList partGrandchildren = partSub.getChildNodes();
                            for (int h = 0; h < partGrandchildren.getLength(); h++) {
                                Node partSubSub = partGrandchildren.item(h);
                                if ("area".equals(partSubSub.getNodeName())) {
                                    String partX = partSubSub.getAttributes().getNamedItem("x").getNodeValue();
                                    System.out.print("Part Dimensions: x=" + partX);
                                    String partY = partSubSub.getAttributes().getNamedItem("y").getNodeValue();
                                    System.out.print(" | y=" + partY);
                                    String partH = partSubSub.getAttributes().getNamedItem("h").getNodeValue();
                                    System.out.print(" | h=" + partH);
                                    String partW = partSubSub.getAttributes().getNamedItem("w").getNodeValue();
                                    System.out.println(" | w=" + partW);

                                } else if ("line".equals(partSubSub.getNodeName())) {
                                    String line = partSubSub.getTextContent();
                                    System.out.println("Part " + ((h + 1) / 2) + " Line = " + line);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}