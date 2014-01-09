package ar.alme.morninformer;

import java.util.List;
import java.util.NoSuchElementException;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ar.alme.morninformer.feeds.RSSNewsSourceFactory;
import ar.alme.morninformer.feeds.UnknownNewsSourceException;
import ar.alme.morninformer.mail.EmailInteractionChannel;
import ar.alme.morninformer.mail.EmailInteractionChannelConfiguration;
import ar.alme.morninformer.users.UserLoadException;
import ar.alme.morninformer.users.UserProfile;
import ar.alme.morninformer.users.XMLUserLoader;
import ar.alme.utils.BitlyURLShortener;
import ar.mil.cideso.correo.configuracion.ProtocoloCorreo;
import ar.mil.cideso.correo.configuracion.ProtocoloCorreoFactory;
import ar.mil.cideso.correo.configuracion.ServidorCorreo;

public class InformerMain {

	private Logger logger = LoggerFactory.getLogger(InformerMain.class);

	private static final String CONFIGURATION_FILENAME = "configuration.ini";
	private static final String KEY_USERS_XML_PATH = "USERS_XML_PATH";
	private static final String KEY_LOGIN = "LOGIN";
	private static final String KEY_APIKEY = "APIKEY";
	private static final String KEY_URL_LA_NACION_ULTIMAS = "URL_LA_NACION_ULTIMAS";
	private static final String KEY_URL_CLARIN = "URL_CLARIN";
	private static final String KEY_URL_TWITTER = "URL_TWITTER";
	private static final String KEY_USER = "USUARIO";
	private static final String KEY_DOMAIN = "DOMINIO";
	private static final String KEY_PASSWORD = "PASSWORD";
	private static final String KEY_NEWS_SUBJECT = "NEWS_SUBJECT";
	private static final String KEY_INCOMING_SERVER = "INCOMING_SERVER";
	private static final String KEY_DISPLAYABLE_NAME = "DISPLAYABLE_NAME";
	private static final String KEY_OUTGOING_SERVER = "OUTGOING_SERVER";

	private XMLUserLoader userLoader;

	public void setUp() throws Exception {
	}

	public static void main(String[] args) throws Throwable {
		BasicConfigurator.configure();
		new InformerMain().run();
	}

	public void run() {
		try {
			Informer informer = new Informer();

			// Get all the configuration values, throw exception if missing
			PropertiesConfiguration configuration;
			configuration = new PropertiesConfiguration(CONFIGURATION_FILENAME);
			configuration.setThrowExceptionOnMissing(true);
			String login = configuration.getString(KEY_LOGIN);
			String apiKey = configuration.getString(KEY_APIKEY);
			String urlLn = configuration.getString(KEY_URL_LA_NACION_ULTIMAS);
			String urlClarin = configuration.getString(KEY_URL_CLARIN);
			String urlTwitter = configuration.getString(KEY_URL_TWITTER);
			String usersFilePath = configuration.getString(KEY_USERS_XML_PATH);
			String username = configuration.getString(KEY_USER);
			String domain = configuration.getString(KEY_DOMAIN);
			String password = configuration.getString(KEY_PASSWORD);
			String newsSubject = configuration.getString(KEY_NEWS_SUBJECT);
			String displayableName = configuration
					.getString(KEY_DISPLAYABLE_NAME);
			String incomingServer = configuration
					.getString(KEY_INCOMING_SERVER);
			String outgoingServer = configuration
					.getString(KEY_OUTGOING_SERVER);

			logger.info("Configuration file loaded successfully");

			// Set up the bitly shortener
			BitlyURLShortener.setInstance(new BitlyURLShortener(login, apiKey));

			// Set up the valid news sources
			RSSNewsSourceFactory.instance().learn(
					NewsSourceDescription.RSS_LA_NACION_ULTIMAS, urlLn);
			RSSNewsSourceFactory.instance().learn(
					NewsSourceDescription.RSS_CLARIN, urlClarin);
			RSSNewsSourceFactory.instance().learn(
					NewsSourceDescription.TWITTER, urlTwitter);

			// Load users
			userLoader = new XMLUserLoader();
			List<UserProfile> users = userLoader.loadUsers(usersFilePath);

			logger.info("User profiles loaded successfully");

			// Set up the interaction channels (email only, for now)
			EmailInteractionChannelConfiguration channelConfiguration = this
					.getEmailConfiguration(username, domain, password,
							newsSubject, displayableName, incomingServer,
							outgoingServer);

			// Tell the informer about the users and the interaction channels
			for (UserProfile user : users) {
				informer.addUserProfile(user);
			}
			informer.addInteractionChannel(new EmailInteractionChannel(
					channelConfiguration));

			logger.info("Commencing...");
			// Work!
			informer.inform();
		} catch (NoSuchElementException e) {
			logger.error("Missing configuration file keys", e);
		} catch (ConfigurationException e) {
			logger.error("Missing configuration file");
		} catch (UserLoadException e) {
			logger.error("Error loading user profiles", e);
		} catch (UnknownNewsSourceException e) {
			logger.error(
					"An unknown news source was specified in the users profile file",
					e);
		}
	}

	private EmailInteractionChannelConfiguration getEmailConfiguration(
			String user, String domain, String password, String newsSubject,
			String displayableName, String incomingServer, String outgoingServer) {
		ServidorCorreo accountData = new ServidorCorreo(user, domain, password,
				this.getOutgoingProtocol(outgoingServer),
				this.getIncomingProtocol(incomingServer));
		EmailInteractionChannelConfiguration emailConfiguration = new EmailInteractionChannelConfiguration(
				accountData, newsSubject);
		emailConfiguration.setDisplayableName(displayableName);

		return emailConfiguration;
	}

	private ProtocoloCorreo getIncomingProtocol(String incomingServer) {
		ProtocoloCorreoFactory factory = new ProtocoloCorreoFactory()
				.setHabilitarSSL(true);
		ProtocoloCorreo proto = factory.crearIMAP(incomingServer);
		return proto;
	}

	private ProtocoloCorreo getOutgoingProtocol(String outgoingServer) {
		return ProtocoloCorreoFactory.crearSMTPDefault(outgoingServer);
	}
}
