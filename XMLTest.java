import org.w3c.dom.Document;

public class XMLTest {

   public static void main(String args[]) {

      Document doc = null;
      /* CardParser parsing = new CardParser(); */
      BoardParser parsing = new BoardParser();
      try {
         /* doc = parsing.getDocFromFile("xml/cards.xml"); */
         doc = parsing.getDocFromFile("xml/board.xml");
         /* parsing.readCardData(doc); */
         parsing.readBoardData(doc);

      } catch (Exception e) {
         System.out.println("Error = " + e);
      }
   }
}