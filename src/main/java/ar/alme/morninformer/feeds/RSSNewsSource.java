package ar.alme.morninformer.feeds;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.apache.camel.component.rss.RssUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ar.alme.morninformer.NewsSource;
import ar.alme.morninformer.NewsSourceDescription;
import ar.alme.morninformer.news.DefaultNewsReport;
import ar.alme.morninformer.news.DefaultPieceOfNews;
import ar.alme.morninformer.news.NewsReport;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;

public class RSSNewsSource implements NewsSource {

	private static URL NO_LINK;

	static {
		initializeConstants();
	}

	private static void initializeConstants() {
		try {
			NO_LINK = new URL("http://no-link-available");
		} catch (MalformedURLException e) {
			// ignore, impossible
		}
	}

	private Logger logger = LoggerFactory.getLogger(RSSNewsSource.class);

	private final String url;
	private final FeedReader feedReader;
	private final NewsSourceDescription feedName;

	public RSSNewsSource(NewsSourceDescription feedName, String url,
			FeedReader feedReader) {
		super();
		this.feedName = feedName;
		this.url = url;
		this.feedReader = feedReader;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ar.alme.mule.morninformer.core.news.NewsSource#latestNews()
	 */
	public final NewsReport latestNews() throws FeedLoadException {
		try {
			SyndFeed feed = this.loadFeed();
			NewsReport report = new DefaultNewsReport(this.feedName);

			// General feed attributes
			report.setTitle(feedReader.getFeedTitle(feed));
			report.setAuthor(feedReader.getAuthor(feed));
			report.setDate(feedReader.getDate(feed));

			// Load latest news, feed dependent
			this.loadPiecesOfNews(feed, report);

			return report;
		} catch (FeedLoadException e) {
			logger.error("Feed creation", e);
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	private void loadPiecesOfNews(SyndFeed feed, NewsReport report) {
		List<SyndEntry> entries = feed.getEntries();

		for (SyndEntry entry : entries) {
			String title = feedReader.getTitle(entry);
			String description = feedReader.getDescription(entry);
			URL link = this.getURL(entry);
			DefaultPieceOfNews pieceOfNews = new DefaultPieceOfNews(title,
					description, link);
			report.addPieceOfNews(pieceOfNews);
		}
	}

	private URL getURL(SyndEntry entry) {
		URL link;
		try {
			link = feedReader.getLink(entry);
			return link;
		} catch (MalformedURLException e) {
			logger.warn("Malformed URL: " + e.getMessage());
			return NO_LINK;
		}
	}

	private SyndFeed loadFeed() throws FeedLoadException {
		try {
			return RssUtils.createFeed(this.url);
		} catch (Exception e) {
			throw new FeedLoadException(this);
		}
	}

	@Override
	public NewsSourceDescription getName() {
		return feedName;
	}

}
