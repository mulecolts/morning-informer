package ar.alme.morninformer.feeds;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.List;

import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;

public class LNFeedReader extends FeedReader
{

	public String getFeedTitle(SyndFeed feed)
	{
		return feed.getTitle();
	}

	public String getAuthor(SyndFeed feed)
	{
		String author = feed.getAuthor();
		return author == null ? "La Nación" : author;
	}

	public Date getDate(SyndFeed feed)
	{
		return feed.getPublishedDate();
	}

	public String getTitle(SyndEntry entry)
	{
		return entry.getTitle();
	}

	@SuppressWarnings("unchecked")
	public String getDescription(SyndEntry entry)
	{
		List<SyndContent> contentList = entry.getContents();
		SyndContent syndContent = contentList.get(0);
		String content = syndContent.getValue();
		String contentType = syndContent.getType();
		if (contentType != null && contentType.equals("xhtml")) {
			return getHTMLDescription(content);
		}
		return content;
	}

	public URL getLink(SyndEntry entry) throws MalformedURLException
	{
		return new URL(entry.getUri());
	}

}
