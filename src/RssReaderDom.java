import java.net.URL;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.StringReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class RssReaderDom {

    public static void main(String[] args) throws Exception {

        URL url = new URL("https://www.europapress.es/rss/rss.aspx");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

        // Leer los datos de la URL en un String
        InputStream in = url.openStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder out = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            out.append(line);
        }
        reader.close();
        in.close();
        String response = out.toString();

        // Imprime la respuesta para depurar
        System.out.println(response);

        // Ahora, intenta analizar la respuesta
        Document doc = dBuilder.parse(new InputSource(new StringReader(response)));
        doc.getDocumentElement().normalize();

        System.out.println("Root element: " + doc.getDocumentElement().getNodeName());

        NodeList nList = doc.getElementsByTagName("item");
        for (int i = 0; i < nList.getLength(); i++) {
            Node nNode = nList.item(i);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                System.out.println("Title: " + eElement.getElementsByTagName("title").item(0).getTextContent());
                System.out.println("URL: " + eElement.getElementsByTagName("link").item(0).getTextContent());
                System.out.println("Description: " + eElement.getElementsByTagName("description").item(0).getTextContent());
                System.out.println("Publication Date: " + eElement.getElementsByTagName("pubDate").item(0).getTextContent());
                System.out.println("Category: " + eElement.getElementsByTagName("category").item(0).getTextContent());
            }
        }
    }
}
