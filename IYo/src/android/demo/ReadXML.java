package android.demo;

import java.io.InputStream;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class ReadXML {

    private Document doc = null;
    public ReadXML(InputStream inputStream) throws Exception{
	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	DocumentBuilder db = dbf.newDocumentBuilder();
	doc = db.parse(inputStream);
    }
    
    public Vector<Building> viewXML(){
	Element element = doc.getDocumentElement();
	
	NodeList nodeList = doc.getElementsByTagName("building");
	
	Building building = new Building();
	Vector<Building> vecBuilding = new Vector<Building>();
	for (int i = 0; i < nodeList.getLength(); i++)
	{
	    Node fatherNode = nodeList.item(i);
	
	    NodeList childNodes = fatherNode.getChildNodes();
		
	    Vector<String> vecContent = new Vector<String>();
	
	    for (int j = 0; j < childNodes.getLength(); j++)
	    {
		Node childNode = childNodes.item(j);
		if (childNode instanceof Element){
		    if (childNode.getFirstChild() != null){
			vecContent.add(childNode.getFirstChild().getNodeValue());
		    }
		    else
			vecContent.add("null");
		}
	    }
		int num = Integer.parseInt(vecContent.get(0));
		String name = vecContent.get(1);
		Double leftLat = Double.parseDouble(vecContent.get(2));
		Double leftLong = Double.parseDouble(vecContent.get(3));
		Double rightLat = Double.parseDouble(vecContent.get(4));
		Double rightLong = Double.parseDouble(vecContent.get(5));
		String openTime = vecContent.get(6);
		String introduction = vecContent.get(7);
		String news = vecContent.get(8);
		
//	        building = new Building(vecContent.get(0), vecContent.get(1), vecContent.get(2), 
//	        	vecContent.get(3), vecContent.get(4), vecContent.get(5), 
//	        	vecContent.get(6), vecContent.get(0), vecContent.get(7));
		building = new Building(num, name, leftLat, leftLong, rightLat, rightLong, openTime, introduction, news);
		vecContent.clear();
		
		vecBuilding.add(building);
	}    
	
	return vecBuilding;
    }
}
