package ar.alme.morninformer.mail;

import java.net.UnknownHostException;

import org.codemonkey.simplejavamail.Email;
import org.codemonkey.simplejavamail.Mailer;
import org.codemonkey.simplejavamail.TransportStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ar.alme.morninformer.ChannelOpeningException;
import ar.alme.morninformer.ChannelType;
import ar.alme.morninformer.ContactData;
import ar.alme.morninformer.InteractionChannel;
import ar.alme.morninformer.news.NewsReport;
import ar.alme.morninformer.news.URLShorteningReport;
import ar.alme.morninformer.users.Preferences;
import ar.alme.utils.HTMLEditor;
import ar.alme.utils.PlainTextEditor;
import ar.mil.cideso.correo.AutenticacionException;
import ar.mil.cideso.correo.Conexion;
import ar.mil.cideso.correo.ConexionException;
import ar.mil.cideso.correo.ConexionFactory;

public class EmailInteractionChannel implements InteractionChannel
{

	private Logger logger = LoggerFactory.getLogger(EmailInteractionChannel.class);

	private EmailBuilder emailBuilder;
	private HTMLEditor htmlEditor;
	private PlainTextEditor plainEditor;

	private EmailInteractionChannelConfiguration configuration;

	// TODO not being actually used
	@Deprecated
	private Conexion emailConnection;

	public EmailInteractionChannel(EmailInteractionChannelConfiguration configuration)
	{
		super();
		this.configuration = configuration;
		this.emailBuilder = new EmailBuilder();
		this.htmlEditor = new HTMLEditor();
		this.plainEditor = new PlainTextEditor();

		this.initializeEmailBuilder();
		this.initializeEmailConnection();
	}

	private void initializeEmailConnection()
	{
		emailConnection = ConexionFactory.crear(configuration.getAccountData());
	}

	private void initializeEmailBuilder()
	{
		emailBuilder.setFromAddress(configuration.getUserName());
		emailBuilder.setFromName(configuration.getDisplayableName());
		emailBuilder.setSubject(configuration.getNewsEmailSubject());
	}

	public void open() throws ChannelOpeningException
	{
		try {
			this.emailConnection.conectar();
		} catch (UnknownHostException e) {
			logger.error(Messages.getString("Logs.OPENING_MAIL_CHANNEL"), e);
			throw new ChannelOpeningException(Messages.getString("Errors.UNKNOWN_HOST"), e);
		} catch (AutenticacionException e) {
			logger.error(Messages.getString("Logs.OPENING_MAIL_CHANNEL"), e);
			throw new ChannelOpeningException(Messages.getString("Errors.INVALID_AUTH"), e);
		} catch (ConexionException e) {
			logger.error(Messages.getString("Logs.OPENING_MAIL_CHANNEL"), e);
			throw new ChannelOpeningException(Messages.getString("Errors.UNKNOWN"), e);
		}
	}

	public void sendNewsReport(ContactData contactData, NewsReport newsReport)
	{
		if (contactData.getChannelType().equals(this.getChannelType())) {
			Email email = this.buildEmail((EmailContactData) contactData, newsReport);
			this.getMailer().sendMail(email);
		}
	}

	private Email buildEmail(EmailContactData contactData, NewsReport newsReport)
	{

		Email email = emailBuilder.setRecipient(contactData.getContactName(), contactData.getEmailAddress()).build();

		// do some magic and get the text according to the contact's
		// interests! <- <b>NO!</b> this is informer's responsibility

		Object format = contactData.getPreference(Preferences.EMAIL_FORMAT);

		if (format.equals(Preferences.EMAIL_FORMAT_HTML)) {
			String mailText = this.htmlEditor.writeReport(newsReport);
			email.setTextHTML(mailText);
		} else {
			String mailText = this.plainEditor.writeReport(new URLShorteningReport(newsReport));
			email.setText(mailText);
		}

		return email;
	}

	private Mailer getMailer()
	{
		// TODO why not instance variable? new mailers every time? (perhaps
		// there was a reason for this)
		return new Mailer(this.getSMTPHost(), this.getSMTPPort(), this.getUserName(), this.getPassword(), TransportStrategy.SMTP_TLS);
	}

	public ChannelType getChannelType()
	{
		return ChannelType.EMAIL;
	}

	public String getPassword()
	{
		return configuration.getPassword();
	}

	public String getUserName()
	{
		return configuration.getUserName();
	}

	public int getSMTPPort()
	{
		return configuration.getSMTPPort();
	}

	public String getSMTPHost()
	{
		return configuration.getSMTPHost();
	}
}
