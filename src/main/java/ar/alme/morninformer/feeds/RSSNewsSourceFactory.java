package ar.alme.morninformer.feeds;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.methods.GetMethod;

import ar.alme.morninformer.NewsSourceDescription;

public class RSSNewsSourceFactory
{

	private static Map<NewsSourceDescription, String> feedDirectory = new HashMap<NewsSourceDescription, String>();

	private static RSSNewsSourceFactory instance;

	public static RSSNewsSourceFactory instance()
	{
		if (instance == null)
			instance = new RSSNewsSourceFactory();
		return instance;
	}

	public RSSNewsSource createLaNacionUltimas() throws UnknownNewsSourceException
	{
		NewsSourceDescription feedDescription = NewsSourceDescription.RSS_LA_NACION_ULTIMAS;
		String url = feedDirectory.get(feedDescription);
		if (url != null) {
			RSSNewsSource newsSource = new RSSNewsSource(feedDescription, url, new LNFeedReader());
			return newsSource;
		} else
			throw new UnknownNewsSourceException(feedDescription.toString());
	}

	public RSSNewsSource createClarin() throws UnknownNewsSourceException
	{
		NewsSourceDescription feedDescription = NewsSourceDescription.RSS_CLARIN;
		String url = feedDirectory.get(feedDescription);
		if (url != null) {
			RSSNewsSource newsSource = new RSSNewsSource(feedDescription, url, new ClarinFeedReader());
			return newsSource;
		} else
			throw new UnknownNewsSourceException(feedDescription.toString());
	}

	public RSSNewsSource createTwitter(String screenName)
	{
		NewsSourceDescription feedDescription = NewsSourceDescription.TWITTER;
		String baseUrl = feedDirectory.get(feedDescription);

		if (baseUrl != null) {
			// the description should not mutate
			NewsSourceDescription feedDescriptionWithProperty = feedDescription.withProperty("screenName", screenName);
			String url = this.buildTwitterTimelineURL(baseUrl, screenName);
			RSSNewsSource newsSource = new RSSNewsSource(feedDescriptionWithProperty, url, new TwitterFeedReader());
			return newsSource;
		} else
			// should not be null, that would mean you didn't tell the factory
			// to learn about twitter, so, do it! (developer problem)
			throw new RuntimeException(new UnknownNewsSourceException(feedDescription.toString()));
	}

	private String buildTwitterTimelineURL(String baseUrl, String screenName)
	{
		try {
			HttpMethod method = new GetMethod(baseUrl);
			method.setQueryString(new NameValuePair[] { new NameValuePair("screen_name", screenName), });
			return method.getURI().toString();
		} catch (URIException e) {
			throw new RuntimeException("Building twitter GET url", e);
		}
	}

	public void learn(NewsSourceDescription rssName, String url)
	{
		feedDirectory.put(rssName, url);
	}

	public RSSNewsSource create(String name) throws UnknownNewsSourceException
	{
		if (name.equalsIgnoreCase("CLARIN")) {
			return this.createClarin();
		} else if (name.equalsIgnoreCase("LA NACION ULTIMAS")) {
			return this.createLaNacionUltimas();
		} else
			throw new UnknownNewsSourceException(name);
	}

}
