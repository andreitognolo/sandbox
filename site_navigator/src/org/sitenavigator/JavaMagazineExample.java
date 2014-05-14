package org.sitenavigator;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.xml.sax.SAXException;

public class JavaMagazineExample {

	public static void main(String[] args) throws ClientProtocolException,
			IOException, IllegalStateException, SAXException {
		DevMedia.extractInformation(6, "REVISTA JAVA MAGAZINE");
	}
}
