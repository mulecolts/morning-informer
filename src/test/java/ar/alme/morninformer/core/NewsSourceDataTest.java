package ar.alme.morninformer.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;

import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

import ar.alme.morninformer.NewsSourceDescription;
import ar.alme.morninformer.NewsSourceDescriptionFactory;

public class NewsSourceDataTest
{

	@Before
	public void setUp() throws Exception
	{
	}

	@Test
	public void test()
	{
		assertFalse(NewsSourceDescription.RSS_CLARIN.equals(NewsSourceDescription.RSS_LA_NACION_ULTIMAS));

		assertEquals(NewsSourceDescription.RSS_CLARIN, NewsSourceDescription.RSS_CLARIN);

		assertEquals(NewsSourceDescription.TWITTER, NewsSourceDescription.TWITTER);
		assertFalse(NewsSourceDescription.TWITTER.equals(NewsSourceDescription.TWITTER.withProperty("foo", "bar")));

		assertEquals(NewsSourceDescription.TWITTER.withProperty("foo", "bar"), NewsSourceDescription.TWITTER.withProperty("foo", "bar"));
		assertNotSame(NewsSourceDescription.TWITTER.withProperty("foo", "bar"), NewsSourceDescription.TWITTER.withProperty("foo", "bar"));
		assertFalse(NewsSourceDescription.TWITTER.withProperty("foo", "bar").withProperty("baz", "qux").equals(NewsSourceDescription.TWITTER.withProperty("foo", "bar")));

		NewsSourceDescription data = NewsSourceDescription.TWITTER.withProperty("foo", "bar").withProperty("baz", "qux");

		assertEquals(data.getProperty("foo"), "bar");
	}

	@Test
	public void factoryTest()
	{
		Properties properties = new Properties();
		NewsSourceDescription data = NewsSourceDescriptionFactory.create("rss", "LA NACION ULTIMAS", properties);

		assertEquals(data, NewsSourceDescription.RSS_LA_NACION_ULTIMAS);

		data = NewsSourceDescriptionFactory.create("rss", "CLARIN", properties);

		assertEquals(data, NewsSourceDescription.RSS_CLARIN);

		properties = new Properties();
		data = NewsSourceDescriptionFactory.create("twitter", properties);

		assertEquals(data, NewsSourceDescription.TWITTER);

		properties = new Properties();
		properties.setProperty("screenName", "foo");
		data = NewsSourceDescriptionFactory.create("twitter", properties);

		assertEquals(data, NewsSourceDescription.TWITTER.withProperty("screenName", "foo"));

		assertFalse(data.equals(NewsSourceDescription.TWITTER));
	}
}
