import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class SDNReader {
	public static void main(String[] args) throws IOException {

		URL xmlUrl = new URL("https://www.treasury.gov/ofac/downloads/sdn.xml");
		InputStream in = xmlUrl.openStream();
		Document doc = parse(in);

		//System.out.println(doc.getElementsByTagName("publshInformation").item(0).getTextContent());

		NodeList sdnList = doc.getElementsByTagName("sdnEntry");
		System.out.println("Lenth: "+sdnList.getLength());

		for(int i=0;i<1;i++){
			//System.out.println(sdnList.item(i).getTextContent());
			Node node = sdnList.item(i);
			System.out.println("\ncurrrent element: "+node.getNodeName());
			if (node.getNodeType() == Node.ELEMENT_NODE) {

				Element eElement = (Element) node;

				System.out.println("uid: " + eElement.getElementsByTagName("uid").item(0).getTextContent());
				System.out.println("lastName: " + eElement.getElementsByTagName("lastName").item(0).getTextContent());
				System.out.println("sdnType: " + eElement.getElementsByTagName("sdnType").item(0).getTextContent());

				NodeList programList = eElement.getElementsByTagName("programList");
				System.out.println("Program list: "+programList.getLength());
				for(int j=0;j<programList.getLength();j++){
					Element program = (Element)programList.item(i);
					System.out.println(program.getElementsByTagName("program").item(0).getTextContent());
				}
			}
		}
	}

	public static Document parse (InputStream is) {
        Document ret = null;
        DocumentBuilderFactory domFactory;
        DocumentBuilder builder;

        try {
            domFactory = DocumentBuilderFactory.newInstance();
            domFactory.setValidating(false);
            domFactory.setNamespaceAware(false);
            builder = domFactory.newDocumentBuilder();

            ret = builder.parse(is);
        }
        catch (Exception ex) {
            System.err.println("unable to load XML: " + ex);
        }
        return ret;
    }
}