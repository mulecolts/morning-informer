package ar.alme.morninformer.users;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import ar.alme.morninformer.ContactData;
import ar.alme.morninformer.NewsSourceDescription;
import ar.alme.morninformer.NewsSourceDescriptionFactory;
import ar.alme.morninformer.mail.EmailContactData;

public class XMLUserLoader {
	private Logger logger = LoggerFactory.getLogger(XMLUserLoader.class);

	public List<UserProfile> loadUsers(String filePath)
			throws UserLoadException {
		try {
			DocumentBuilder documentBuilder = DocumentBuilderFactory
					.newInstance().newDocumentBuilder();
			Document document = documentBuilder.parse(filePath);
			Element rootElement = document.getDocumentElement();
			NodeList userProfilesNodeList = rootElement
					.getElementsByTagName("UserProfile");

			List<UserProfile> userProfiles = new LinkedList<UserProfile>();
			for (int i = 0; i < userProfilesNodeList.getLength(); i++) {
				userProfiles.add(this
						.buildUserProfile((Element) userProfilesNodeList
								.item(i)));
			}
			return userProfiles;

		} catch (ParserConfigurationException e) {
			logger.error("Importing users", e);
			throw new UserLoadException(
					"Parsing users file: misconfigured parser");
		} catch (SAXException e) {
			logger.error("Parsing users file", e);
			throw new UserLoadException(
					"Parsing users file: malformed XML file");
		} catch (IOException e) {
			logger.error("Parsing users file", e);
			throw new UserLoadException("Parsing users file: general IO error");
		}

	}

	private UserProfile buildUserProfile(Element element) {

		String userName = element.getAttribute("name");
		List<ContactData> contactDataList = this.buildContactDataLists(element
				.getElementsByTagName("ContactData"));
		List<NewsSourceDescription> newsSourceNames = this
				.buildNewsSourceNamesList(element
						.getElementsByTagName("NewsSource"));

		UserProfile userProfile = UserProfileFactory.createUserProfile(
				userName, contactDataList, newsSourceNames);
		return userProfile;
	}

	private List<NewsSourceDescription> buildNewsSourceNamesList(
			NodeList newsSourceNodeList) {
		List<NewsSourceDescription> newsSources = new LinkedList<NewsSourceDescription>();
		for (int i = 0; i < newsSourceNodeList.getLength(); i++) {
			NewsSourceDescription newsSource = this
					.buildNewsSource((Element) newsSourceNodeList.item(i));
			newsSources.add(newsSource);
		}
		return newsSources;
	}

	private NewsSourceDescription buildNewsSource(Element item) {
		String type = item.getAttribute("type");
		// Element identifierElement = (Element) item.getElementsByTagName(
		// "Identifier").item(0);
		// String identifier = identifierElement.getTextContent();

		Properties properties = new Properties();
		NodeList childNodes = item.getElementsByTagName("Property");
		for (int i = 0; i < childNodes.getLength(); i++) {
			Element element = (Element) childNodes.item(i);
			properties.setProperty(element.getAttribute("key"),
					element.getTextContent());
		}
		return NewsSourceDescriptionFactory.create(type, properties);
	}

	private List<ContactData> buildContactDataLists(NodeList contactDataNodeList) {
		LinkedList<ContactData> contactDataList = new LinkedList<ContactData>();
		for (int i = 0; i < contactDataNodeList.getLength(); i++) {
			ContactData contactData = this
					.buildContactData((Element) contactDataNodeList.item(i));
			contactDataList.add(contactData);
		}
		return contactDataList;
	}

	private ContactData buildContactData(Element item) {
		String channelType = item.getAttribute("channelType");
		if (channelType.equalsIgnoreCase("email")) {
			return this.buildEmailContactData(item);
		}
		return null;
	}

	private ContactData buildEmailContactData(Element item) {
		String contactName = item.getElementsByTagName("ContactName").item(0)
				.getTextContent();
		String emailAddress = item.getElementsByTagName("EmailAddress").item(0)
				.getTextContent();
		ContactData contactData = new EmailContactData(contactName,
				emailAddress);
		NodeList preferencesNodeList = item.getElementsByTagName("Preference");
		for (int i = 0; i < preferencesNodeList.getLength(); i++) {
			Element preferenceElement = (Element) preferencesNodeList.item(i);
			Preferences key = Preferences.valueOf(preferenceElement
					.getAttribute("key"));
			Preferences value = Preferences.valueOf(preferenceElement
					.getTextContent());
			contactData.setPreference(key, value);
		}
		return contactData;
	}

}
