package com.sirra.appcore.htmlgeneration;

import java.io.*;

import nu.xom.*;

/**
 * Assists with generation of HTML using XML.
 * Initial use is for sending out HTML emails with tables from the server.
 */
public class HtmlHelper {
	
	public HtmlHelper() {
		
	}
	
	// Designed to be publicly accessible
	public Element tableElement;
	public Element trElement;
	public Element tdElement;
	
	public void startTable() {
		tableElement = new Element("table");
		tableElement.addAttribute(new Attribute("style", "border-collapse: collapse"));
	}
	
	boolean isOdd = true;
	public void addRow(String... values) {
		trElement = new Element("tr");
		tableElement.appendChild(trElement);

		String odd = "";
		if(isOdd) {
			odd = "background: #F5FFFA;";
		}
		isOdd = !isOdd;
		
		for(String value: values) {
			Element tdElement = new Element("td");
			
			tdElement.addAttribute(new Attribute("style", "border: 1px solid #ddd; padding: 2px 7px; " + odd));
			tdElement.appendChild(value);
			
			trElement.appendChild(tdElement);
		}
	}
	
	public String format(Element element) {
		try {
			OutputStream out = new ByteArrayOutputStream();
			Serializer serializer = new MySerializer(out);
			serializer.setIndent(4);
			serializer.setMaxLength(64);
			
			Document doc = new Document(element);
			serializer.write(doc);
			
			return out.toString();
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
}
