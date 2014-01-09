package ar.alme.morninformer.mail;

import javax.mail.Message.RecipientType;

import org.codemonkey.simplejavamail.Email;

public class EmailBuilder
{

	private String fromName;
	private String fromAddress;
	private String subject;
	private String recipientName;
	private String recipientAddress;

	public Email build()
	{
		Email email = new Email();

		email.setFromAddress(fromName, fromAddress);
		email.setReplyToAddress(fromName, fromAddress);
		email.addRecipient(recipientName, recipientAddress, RecipientType.TO);

		// TODO - perhaps add a "logging" mail account, local?
		// email.addRecipient(name, address, type);

		email.setSubject(subject);

		return email;
	}

	public EmailBuilder setFromName(String fromName)
	{
		this.fromName = fromName;
		return this;
	}

	public EmailBuilder setFromAddress(String fromAddress)
	{
		this.fromAddress = fromAddress;
		return this;
	}

	public EmailBuilder setSubject(String subject)
	{
		this.subject = subject;
		return this;
	}

	public EmailBuilder setRecipient(String contactName, String emailAddress)
	{
		this.setRecipientName(contactName);
		this.setRecipientAddress(emailAddress);
		return this;
	}

	public EmailBuilder setRecipientName(String recipientName)
	{
		this.recipientName = recipientName;
		return this;
	}

	public EmailBuilder setRecipientAddress(String recipientAddress)
	{
		this.recipientAddress = recipientAddress;
		return this;
	}

}
