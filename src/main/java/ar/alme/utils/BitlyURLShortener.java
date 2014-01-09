package ar.alme.utils;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class BitlyURLShortener {
	private static BitlyURLShortener instance;

	public static BitlyURLShortener getInstance() {
		return instance;
	}

	public static void setInstance(BitlyURLShortener instance) {
		BitlyURLShortener.instance = instance;
	}

	private final String login;
	private final String apiKey;

	public BitlyURLShortener(String login, String apiKey) {
		this.login = login; // "morninginformer"
		this.apiKey = apiKey; // "R_9fa320eaeac71b10366e10f35b61fe71"
	}

	public String shorten(String longUrl) throws URLShorteningServiceException {

		try {
			HttpMethod method = new GetMethod("http://api.bit.ly/shorten");

			method.setQueryString(new NameValuePair[] {
					new NameValuePair("longUrl", longUrl),
					new NameValuePair("version", "2.0.1"),
					new NameValuePair("login", login),
					new NameValuePair("apiKey", apiKey),
					new NameValuePair("format", "xml"),
					new NameValuePair("history", "1") });
			new HttpClient().executeMethod(method);
			String responseXml = method.getResponseBodyAsString();

			// parse response
			DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
			Document document = docBuilder.parse(new InputSource(
					new StringReader(responseXml)));
			NodeList nodeList = document.getElementsByTagName("shortUrl");
			Node item = nodeList.item(0);
			return item.getTextContent();
		} catch (Exception e) {
			// don't really care what happens here, just wrap it up and relaunch
			// (won't do any more than logging or something like that)
			throw new URLShorteningServiceException(e);
		}
	}

}
