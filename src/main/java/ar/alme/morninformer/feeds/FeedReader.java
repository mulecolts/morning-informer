package ar.alme.morninformer.feeds;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ar.alme.morninformer.users.ParseException;
import ar.alme.utils.SimpleHTMLTextExtractor;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;

public abstract class FeedReader
{
	protected Logger logger = LoggerFactory.getLogger(ClarinFeedReader.class);

	/**
	 * Gets the title for the whole feed
	 * 
	 * @param feed
	 *            the SyndFeed being analyzed
	 * @return the title for the feed
	 */
	public abstract String getFeedTitle(SyndFeed feed);

	/**
	 * Gets the author for the feed (service provider).
	 * 
	 * @param feed
	 *            the SyndFeed being analyzed
	 * @return the feed's author
	 */
	public abstract String getAuthor(SyndFeed feed);

	/**
	 * Gets the feed intended date.
	 * 
	 * @param feed
	 *            the SyndFeed being analyzed
	 * @return a Date object representing the feed's publish date
	 */
	public abstract Date getDate(SyndFeed feed);

	/**
	 * Gets the entry's title. For news feeds, that would mean the article's
	 * title.
	 * 
	 * @param entry
	 *            the SyndEntry being analyzed
	 * @return the article's title
	 */
	public abstract String getTitle(SyndEntry entry);

	/**
	 * For news feeds, gets the article's lead.
	 * 
	 * @param entry
	 *            the SyndEntry being analyzed
	 * @return the article's lead
	 */
	public abstract String getDescription(SyndEntry entry);

	/**
	 * Gets the URL object that leads to the article's page in the service
	 * provider's web site.
	 * 
	 * @param entry
	 *            the SyndEntry being analyzed
	 * @return a URL object that leads to the article's web page
	 * @throws MalformedURLException
	 *             given the case that the URL is either not well formed or
	 *             absent
	 */
	public abstract URL getLink(SyndEntry entry) throws MalformedURLException;

	protected String getHTMLDescription(String content)
	{
		try {
			return new SimpleHTMLTextExtractor(content).getConcatTrimmedText();
		} catch (ParseException e) {
			logger.error("Parsing HTML content description", e);
			return "No lead available";
		}
	}

}
