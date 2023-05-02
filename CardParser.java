import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;

public class CardParser {

    public Document getDocFromFile(String filename)
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

    public void readCardData(Document d) {
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
                }

                else if ("part".equals(sub.getNodeName())) {
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
}