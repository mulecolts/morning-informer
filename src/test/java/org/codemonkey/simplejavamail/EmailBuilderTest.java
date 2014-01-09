package org.codemonkey.simplejavamail;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.mail.Message.RecipientType;

import org.junit.Before;
import org.junit.Test;

import ar.alme.morninformer.mail.EmailBuilder;

public class EmailBuilderTest {

	private static final String SUBJECT = "Test";
	private static final String EMAIL_ADDRESS = "baz@qux.com";
	private static final String CONTACT_NAME = "Baz";
	private static final String FROM_NAME = "Foo Bar";
	private static final String FROM_ADDRESS = "foo@bar.com";
	private EmailBuilder emailBuilder;

	@Before
	public void startUp() {
		emailBuilder = new EmailBuilder();
	}

	@Test
	public void test() {
		emailBuilder.setFromAddress(FROM_ADDRESS);
		emailBuilder.setFromName(FROM_NAME);
		emailBuilder.setRecipient(CONTACT_NAME, EMAIL_ADDRESS);
		emailBuilder.setSubject(SUBJECT);

		Email email = emailBuilder.build();
		assertEquals(FROM_ADDRESS, email.getFromRecipient().getAddress());
		assertEquals(FROM_NAME, email.getFromRecipient().getName());
		assertEquals(FROM_NAME, email.getReplyToRecipient().getName());
		assertEquals(FROM_NAME, email.getReplyToRecipient().getName());

		List<Recipient> recipients = email.getRecipients();
		for (Recipient recipient : recipients) {
			if (recipient.getType().equals(RecipientType.TO)) {
				assertEquals(CONTACT_NAME, recipient.getName());
				assertEquals(EMAIL_ADDRESS, recipient.getAddress());
			}
		}

	}
}
