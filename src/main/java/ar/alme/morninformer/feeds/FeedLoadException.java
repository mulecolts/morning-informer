package ar.alme.morninformer.feeds;

import ar.alme.morninformer.NewsSource;

public class FeedLoadException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private NewsSource rssNewsSource;

	public FeedLoadException() {
	}

	public FeedLoadException(String arg0) {
		super(arg0);
	}

	public FeedLoadException(Throwable arg0) {
		super(arg0);
	}

	public FeedLoadException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public FeedLoadException(NewsSource rssNewsSource) {
		this.setRssNewsSource(rssNewsSource);
	}

	public NewsSource getRssNewsSource() {
		return rssNewsSource;
	}

	private void setRssNewsSource(NewsSource rssNewsSource) {
		this.rssNewsSource = rssNewsSource;
	}

}
