package ar.alme.morninformer.core;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import ar.alme.morninformer.ChannelOpeningException;
import ar.alme.morninformer.InteractionChannel;
import ar.alme.morninformer.NewsSource;
import ar.alme.morninformer.NewsSourceDescription;
import ar.alme.morninformer.feeds.FeedLoadException;
import ar.alme.morninformer.feeds.RSSNewsSourceFactory;
import ar.alme.morninformer.feeds.UnknownNewsSourceException;
import ar.alme.morninformer.mail.EmailContactData;
import ar.alme.morninformer.mail.EmailInteractionChannel;
import ar.alme.morninformer.mail.EmailInteractionChannelConfiguration;
import ar.alme.morninformer.news.NewsReport;
import ar.alme.morninformer.news.URLShorteningReport;
import ar.alme.morninformer.users.Preferences;
import ar.alme.utils.BitlyURLShortener;
import ar.mil.cideso.correo.configuracion.ProtocoloCorreo;
import ar.mil.cideso.correo.configuracion.ProtocoloCorreoFactory;
import ar.mil.cideso.correo.configuracion.ServidorCorreo;

public class MailInteractionChannelTest {

	private static final String LN_URL = "http://servicios.lanacion.com.ar/herramientas/rss/origen=2";
	private NewsSource newsSource;
	private InteractionChannel mailChannel;
	private EmailContactData contactData;

	@Before
	public void setUp() throws UnknownNewsSourceException {
		RSSNewsSourceFactory.instance().learn(
				NewsSourceDescription.RSS_LA_NACION_ULTIMAS, LN_URL);

		newsSource = RSSNewsSourceFactory.instance().createLaNacionUltimas();

		contactData = new EmailContactData("Alme", "abdala.alejo@gmail.com");
		contactData.setPreference(Preferences.EMAIL_FORMAT,
				Preferences.EMAIL_FORMAT_HTML);
		BitlyURLShortener.setInstance(new BitlyURLShortener("morninginformer",
				"R_9fa320eaeac71b10366e10f35b61fe71"));
		ServidorCorreo accountData = new ServidorCorreo("morning.informer",
				"gmail.com", "morninformer", getProtoSaliente(),
				getProtoEntrante());
		EmailInteractionChannelConfiguration configuration = new EmailInteractionChannelConfiguration(
				accountData, "Morning Informer for you!");
		configuration.setDisplayableName("Morning Informer");

		mailChannel = new EmailInteractionChannel(configuration);
	}

	private ProtocoloCorreo getProtoEntrante() {
		ProtocoloCorreoFactory factory = new ProtocoloCorreoFactory()
				.setHabilitarSSL(true);
		ProtocoloCorreo proto = factory.crearIMAP("imap.gmail.com");
		return proto;
		// return ProtocoloCorreoFactory.crearIMAPDefault("imap.gmail.com");
	}

	private ProtocoloCorreo getProtoSaliente() {
		return ProtocoloCorreoFactory.crearSMTPDefault("smtp.gmail.com");
	}

	@Ignore
	// not a test, more of a runner
	@Test
	public void test() throws FeedLoadException, ChannelOpeningException {
		mailChannel.open();
		NewsReport latestNews = newsSource.latestNews();
		mailChannel.sendNewsReport(contactData, new URLShorteningReport(
				latestNews));
	}

}
