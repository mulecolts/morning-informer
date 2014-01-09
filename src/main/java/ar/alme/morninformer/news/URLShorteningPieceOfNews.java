package ar.alme.morninformer.news;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ar.alme.utils.BitlyURLShortener;
import ar.alme.utils.URLShorteningServiceException;

public class URLShorteningPieceOfNews implements PieceOfNews {

	private Logger logger = LoggerFactory
			.getLogger(URLShorteningPieceOfNews.class);

	private final PieceOfNews pieceOfNews;

	public URLShorteningPieceOfNews(PieceOfNews pieceOfNews) {
		this.pieceOfNews = pieceOfNews;
	}

	/**
	 * @return
	 * @see ar.alme.morninformer.news.PieceOfNews#getTitle()
	 */
	public String getTitle() {
		return pieceOfNews.getTitle();
	}

	/**
	 * @return
	 * @see ar.alme.morninformer.news.PieceOfNews#getDescription()
	 */
	public String getDescription() {
		return pieceOfNews.getDescription();
	}

	/**
	 * @return
	 * @see ar.alme.morninformer.news.PieceOfNews#getLink()
	 */
	public String getLink() {
		try {
			String shortened = BitlyURLShortener.getInstance().shorten(
					this.pieceOfNews.getLink());
			return shortened;
		} catch (URLShorteningServiceException e) {
			// well, service not working or otherwise, return the original link
			logger.info("URL Shortening Service probably not working", e);
			return this.pieceOfNews.getLink();
		}
	}
}
