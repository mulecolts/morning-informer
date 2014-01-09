package ar.alme.morninformer;

import java.util.Properties;

public interface ContactData {

	ChannelType getChannelType();

	Properties getPreferences();

	Object getPreference(Object preferenceKey);

	void setPreference(Object key, Object value);

}
