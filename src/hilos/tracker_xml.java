package hilos;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class tracker_xml extends Thread {
    String Peer_port;
    String torrentid;
    String torrentip;

    @Override
    public void run() {
        while(true){
            File file = new File("./tracker/files/peers.xml");
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = null;
            try {
                db = dbf.newDocumentBuilder();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            }
            Document doc = null;
            try {
                doc = db.parse(file);
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            doc.getDocumentElement().normalize();
            System.out.println("Lista de peers: " + doc.getDocumentElement().getNodeName());
            NodeList nodeList = doc.getElementsByTagName("peer");
            for (int itr = 0; itr < nodeList.getLength(); itr++)
            {
                Node node = nodeList.item(itr);
                System.out.println(node.getNodeName()+(itr+1));
                if (node.getNodeType() == Node.ELEMENT_NODE)
                {
                    Element eElement = (Element) node;
                    System.out.println("  IP:"+eElement.getElementsByTagName("ip").item(0).getTextContent());
                    System.out.println("  Puerto: "+ eElement.getElementsByTagName("port").item(0).getTextContent());
                    System.out.println("  Torrents en el peer:");
                    NodeList nodeListTorrent = eElement.getElementsByTagName("torrents");
                    Node nodos = nodeListTorrent.item(0);
                    Element eElementos = (Element) nodos;
                    NodeList nodeListTorrentid = eElementos.getElementsByTagName("torrentid");
                    //for (int items=0; items < nodeListTorrentid.getLength();items++) {
                        Node nodosid = nodeListTorrentid.item(0);
                        Element eElementosid = (Element) nodosid;
                        System.out.println("\t"+nodosid.getNodeName()+":"+eElementos.getTextContent());
                    //}

                }
            }
            try {
                tracker_xml.sleep(8000);
                System.out.println("\n\n");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }
}