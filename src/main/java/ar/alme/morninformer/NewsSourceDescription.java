package ar.alme.morninformer;

import java.util.Properties;

public class NewsSourceDescription
{
	public static final String TYPE_RSS = "RSS";
	public static final String TYPE_TWITTER = "TWITTER";

	public static final NewsSourceDescription RSS_LA_NACION_ULTIMAS = new NewsSourceDescription(TYPE_RSS, "LA NACION ULTIMAS");
	public static final NewsSourceDescription RSS_CLARIN = new NewsSourceDescription(TYPE_RSS, "CLARIN");
	public static final NewsSourceDescription TWITTER = new NewsSourceDescription(TYPE_TWITTER);

	private String type;
	private Properties properties;

	public NewsSourceDescription(String type)
	{
		this(type, "");
	}

	public NewsSourceDescription(String type, String name)
	{
		this.type = type;
		this.properties = new Properties();
		this.setProperty("name", name);
	}

	public String getType()
	{
		return type;
	}

	public String getName()
	{
		return this.getProperty("name");
	}

	public Properties getProperties()
	{
		return this.properties;
	}

	/**
	 * @param key
	 * @return
	 * @see java.util.Properties#getProperty(java.lang.String)
	 */
	public String getProperty(String key)
	{
		return properties.getProperty(key);
	}

	/**
	 * @param key
	 * @param value
	 * @return
	 * @see java.util.Properties#setProperty(java.lang.String, java.lang.String)
	 */
	public Object setProperty(String key, String value)
	{
		return properties.setProperty(key, value);
	}

	public void addProperties(Properties properties2)
	{
		this.properties.putAll(properties2);
	}

	/**
	 * 
	 * @param key
	 * @param value
	 * @return
	 * @return
	 * @see java.util.Properties#setProperty(java.lang.String, java.lang.String)
	 */
	public NewsSourceDescription withProperty(String key, String value)
	{
		NewsSourceDescription newData = new NewsSourceDescription(this.type);
		newData.addProperties((Properties) this.properties.clone());
		newData.setProperty(key, value);

		return newData;
	}

	@Override
	public String toString()
	{
		return this.type + " " + getName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((properties == null) ? 0 : properties.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NewsSourceDescription other = (NewsSourceDescription) obj;
		if (properties == null) {
			if (other.properties != null)
				return false;
		} else if (!properties.equals(other.properties))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

}
