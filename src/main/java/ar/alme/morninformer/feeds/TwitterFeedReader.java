package ar.alme.morninformer.feeds;

import java.util.Date;
import java.util.List;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;

public class TwitterFeedReader extends DefaultFeedReader {

	/*
	 * (non-Javadoc)
	 * 
	 * @see ar.alme.morninformer.core.feeds.DefaultFeedReader#getAuthor(com.sun.
	 * syndication.feed.synd.SyndFeed)
	 */
	@Override
	public String getAuthor(SyndFeed feed) {
		String feedTitle = this.getFeedTitle(feed);
		return feedTitle.substring(feedTitle.indexOf('/') + 2,
				feedTitle.length());
	}

	/*
	 * Return the latest entry's published date.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Date getDate(SyndFeed feed) {
		List<SyndEntry> entries = feed.getEntries();
		// we can assume we will always have entries.
		return entries.get(0).getPublishedDate();
	}

	/*
	 * No description, it is just the same as the title.
	 */
	@Override
	public String getDescription(SyndEntry entry) {
		return "";
	}

}
