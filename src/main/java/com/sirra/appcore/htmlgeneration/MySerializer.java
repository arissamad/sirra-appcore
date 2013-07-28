package com.sirra.appcore.htmlgeneration;

import java.io.*;

import nu.xom.*;

/**
 * Used by HtmlHelper to NOT output xml declaration.
 */
public class MySerializer extends Serializer {

	public MySerializer(OutputStream out) {
		super(out);
	}
	
	@Override
	public void writeXMLDeclaration() {
		// Do nothing
	}
}
