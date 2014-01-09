package ar.alme.morninformer.news;

import java.net.URL;

public class DefaultPieceOfNews implements PieceOfNews {

	private final String title;
	private final String description;
	private final URL link;

	public DefaultPieceOfNews(String title, String description, URL link) {
		this.title = title;
		this.description = description;
		this.link = link;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ar.alme.morninformer.core.news.PieceOfNews#getTitle()
	 */
	public String getTitle() {
		return title;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ar.alme.morninformer.core.news.PieceOfNews#getDescription()
	 */
	public String getDescription() {
		return description;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ar.alme.morninformer.core.news.PieceOfNews#getLink()
	 */
	public String getLink() {
		return link.toExternalForm();
	}

}
