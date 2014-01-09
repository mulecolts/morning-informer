package ar.alme.morninformer.feeds;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;

public class ClarinFeedReader extends FeedReader {

	public String getFeedTitle(SyndFeed feed) {
		return feed.getTitle();
	}

	public String getAuthor(SyndFeed feed) {
		String author = feed.getAuthor();
		return author == null ? "Clarín" : author;
	}

	public Date getDate(SyndFeed feed) {
		return feed.getPublishedDate();
	}

	public String getTitle(SyndEntry entry) {
		return entry.getTitle();
	}

	public String getDescription(SyndEntry entry) {
		SyndContent description = entry.getDescription();
		String content = description.getValue();
		String contentType = description.getType();
		if (contentType != null && contentType.equals("text/html")) {
			return getHTMLDescription(content);
		}
		return content;
	}

	public URL getLink(SyndEntry entry) throws MalformedURLException {
		return new URL(entry.getUri());
	}

}
