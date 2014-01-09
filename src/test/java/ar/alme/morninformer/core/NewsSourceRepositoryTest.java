package ar.alme.morninformer.core;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;

import org.junit.Before;
import org.junit.Test;

import ar.alme.morninformer.NewsSource;
import ar.alme.morninformer.NewsSourceDescription;
import ar.alme.morninformer.NewsSourceRepository;
import ar.alme.morninformer.feeds.RSSNewsSourceFactory;
import ar.alme.morninformer.feeds.UnknownNewsSourceException;

public class NewsSourceRepositoryTest
{
	private static final String URL_CLARIN = "http://clarin.feedsportal.com/c/33088/f/577681/index.rss";
	private static final String URL_LA_NACION_ULTIMAS = "http://servicios.lanacion.com.ar/herramientas/rss/origen=2";
	private static final String URL_TWITTER = "https://api.twitter.com/1/statuses/user_timeline.rss";
	private NewsSourceRepository repository;

	@Before
	public void setUp()
	{
		repository = new NewsSourceRepository();
		RSSNewsSourceFactory.instance().learn(NewsSourceDescription.RSS_CLARIN, URL_CLARIN);
		RSSNewsSourceFactory.instance().learn(NewsSourceDescription.RSS_LA_NACION_ULTIMAS, URL_LA_NACION_ULTIMAS);
		RSSNewsSourceFactory.instance().learn(NewsSourceDescription.TWITTER, URL_TWITTER);
	}

	@Test
	public void testRSSs() throws UnknownNewsSourceException
	{
		NewsSource newsSource = repository.get(NewsSourceDescription.RSS_CLARIN);
		NewsSource newsSource1 = repository.get(NewsSourceDescription.RSS_CLARIN);

		assertSame(newsSource, newsSource1);

		NewsSource newsSourceLN = repository.get(NewsSourceDescription.RSS_LA_NACION_ULTIMAS);

		assertNotSame(newsSource1, newsSourceLN);
		assertNotSame(newsSource, newsSourceLN);
		assertFalse(newsSource1.equals(newsSourceLN));
		assertFalse(newsSource.equals(newsSourceLN));

		NewsSource newsSourceLN2 = repository.get(NewsSourceDescription.RSS_LA_NACION_ULTIMAS);

		assertSame(newsSourceLN, newsSourceLN2);
	}

	@Test
	public void testTwitter() throws UnknownNewsSourceException
	{
		NewsSource newsSource = repository.get(NewsSourceDescription.TWITTER.withProperty("screenName", "mnsaldivar"));
		NewsSource newsSource1 = repository.get(NewsSourceDescription.TWITTER.withProperty("screenName", "mnsaldivar"));

		assertSame(newsSource, newsSource1);

		NewsSource newsSource2 = repository.get(NewsSourceDescription.TWITTER.withProperty("screenName", "rodrigouroz"));

		assertNotSame(newsSource1, newsSource2);
		assertNotSame(newsSource, newsSource2);

		NewsSource newsSource3 = repository.get(NewsSourceDescription.TWITTER.withProperty("screenName", "mnsaldivar").withProperty("foo", "bar"));
		assertNotSame(newsSource3, newsSource2);
	}
}
