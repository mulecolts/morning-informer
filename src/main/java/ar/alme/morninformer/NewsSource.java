package ar.alme.morninformer;

import ar.alme.morninformer.feeds.FeedLoadException;
import ar.alme.morninformer.news.NewsReport;

public interface NewsSource {

	/**
	 * Gets a news report with the latest news from this news source.
	 * 
	 * @return
	 */
	public abstract NewsReport latestNews() throws FeedLoadException;

	public abstract NewsSourceDescription getName();

}