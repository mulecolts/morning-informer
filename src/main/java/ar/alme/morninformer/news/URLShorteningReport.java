package ar.alme.morninformer.news;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ar.alme.morninformer.NewsSourceDescription;

public class URLShorteningReport implements NewsReport {

	private NewsReport report;

	public URLShorteningReport(NewsReport report) {
		super();
		this.report = report;
	}

	/**
	 * @param feedTitle
	 * @see ar.alme.morninformer.news.DefaultNewsReport#setTitle(java.lang.String)
	 */
	public void setTitle(String feedTitle) {
		report.setTitle(feedTitle);
	}

	/**
	 * @param author
	 * @see ar.alme.morninformer.news.DefaultNewsReport#setAuthor(java.lang.String)
	 */
	public void setAuthor(String author) {
		report.setAuthor(author);
	}

	/**
	 * @param date
	 * @see ar.alme.morninformer.news.DefaultNewsReport#setDate(java.util.Date)
	 */
	public void setDate(Date date) {
		report.setDate(date);
	}

	/**
	 * @param pieceOfNews
	 * @see ar.alme.morninformer.news.DefaultNewsReport#addPieceOfNews(ar.alme.morninformer.core.news.BasePieceOfNews)
	 */
	public void addPieceOfNews(PieceOfNews pieceOfNews) {
		report.addPieceOfNews(pieceOfNews);
	}

	/**
	 * @return
	 * @see ar.alme.morninformer.news.DefaultNewsReport#getTitle()
	 */
	public String getTitle() {
		return report.getTitle();
	}

	/**
	 * @return
	 * @see ar.alme.morninformer.news.DefaultNewsReport#getAuthor()
	 */
	public String getAuthor() {
		return report.getAuthor();
	}

	/**
	 * @return
	 * @see ar.alme.morninformer.news.DefaultNewsReport#getDate()
	 */
	public Date getDate() {
		return report.getDate();
	}

	/**
	 * @return
	 * @see ar.alme.morninformer.news.DefaultNewsReport#getPiecesOfNews()
	 */
	public List<PieceOfNews> getPiecesOfNews() {
		List<PieceOfNews> piecesOfNews = report.getPiecesOfNews();
		List<PieceOfNews> urlEncodingPieces = new ArrayList<PieceOfNews>(
				piecesOfNews.size());
		for (PieceOfNews poNews : piecesOfNews) {
			urlEncodingPieces.add(new URLShorteningPieceOfNews(poNews));
		}

		return urlEncodingPieces;
	}

	@Override
	public NewsSourceDescription getNewsSource() {
		return report.getNewsSource();
	}

}
