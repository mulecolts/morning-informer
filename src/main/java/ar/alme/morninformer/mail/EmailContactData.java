package ar.alme.morninformer.mail;

import java.util.Properties;

import ar.alme.morninformer.ChannelType;
import ar.alme.morninformer.ContactData;

public class EmailContactData implements ContactData
{

	private String contactName;
	private String emailAddress;
	private Properties preferences;

	public EmailContactData(String contactName, String emailAddress)
	{
		super();
		this.contactName = contactName;
		this.emailAddress = emailAddress;
		this.preferences = new Properties();
	}

	public ChannelType getChannelType()
	{
		return ChannelType.EMAIL;
	}

	public Properties getPreferences()
	{
		return preferences;
	}

	public String getContactName()
	{
		return this.contactName;
	}

	public String getEmailAddress()
	{
		return this.emailAddress;
	}

	public void setPreference(Object key, Object value)
	{
		this.preferences.put(key, value);
	}

	@Override
	public Object getPreference(Object preferenceKey)
	{
		Object preferenceValue = preferences.get(preferenceKey);
		return preferenceValue == null ? Messages.getString("Prefs.PREF_NOT_FOUND") : preferenceValue;
	}

}
