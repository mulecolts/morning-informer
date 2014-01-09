package ar.alme.morninformer.core.feeds;

import java.util.List;

import org.apache.camel.component.rss.RssUtils;
import org.junit.Before;
import org.junit.Test;

import ar.alme.morninformer.feeds.LNFeedReader;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;

public class LNFeedReaderTest {

	private static final String LN_FEED_URI = "http://servicios.lanacion.com.ar/herramientas/rss/origen=2";
	private LNFeedReader reader;

	@Before
	public void setUp() {
		reader = new LNFeedReader();
	}

	@SuppressWarnings("unchecked")
	@Test
	public void test() throws Exception {
		SyndFeed feed = RssUtils.createFeed(LN_FEED_URI);
		System.out.println(reader.getAuthor(feed));
		System.out.println(reader.getFeedTitle(feed));
		System.out.println(reader.getDate(feed));
		List<SyndEntry> entries = feed.getEntries();
		for (SyndEntry entry : entries) {
			System.out.println();
			System.out.println(reader.getTitle(entry));
			System.out.println(reader.getDescription(entry));
			System.out.println(reader.getLink(entry));
		}
	}

}
