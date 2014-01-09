package ar.alme.morninformer.news;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import ar.alme.morninformer.NewsSourceDescription;

public class DefaultNewsReport implements NewsReport
{

	private String title;
	private String author;
	private Date date;
	private List<PieceOfNews> piecesOfNews;
	private final NewsSourceDescription source;

	public DefaultNewsReport(NewsSourceDescription source)
	{
		this.source = source;
		this.piecesOfNews = new LinkedList<PieceOfNews>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ar.alme.morninformer.core.news.NewsReport#setTitle(java.lang.String)
	 */
	public void setTitle(String feedTitle)
	{
		this.title = feedTitle;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ar.alme.morninformer.core.news.NewsReport#setAuthor(java.lang.String)
	 */
	public void setAuthor(String author)
	{
		this.author = author;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ar.alme.morninformer.core.news.NewsReport#setDate(java.util.Date)
	 */
	public void setDate(Date date)
	{
		this.date = date;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ar.alme.morninformer.core.news.NewsReport#addPieceOfNews(ar.alme.morninformer
	 * .core.news.PieceOfNews)
	 */
	public void addPieceOfNews(PieceOfNews pieceOfNews)
	{
		this.piecesOfNews.add(pieceOfNews);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ar.alme.morninformer.core.news.NewsReport#getTitle()
	 */
	public String getTitle()
	{
		return title;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ar.alme.morninformer.core.news.NewsReport#getAuthor()
	 */
	public String getAuthor()
	{
		return author;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ar.alme.morninformer.core.news.NewsReport#getDate()
	 */
	public Date getDate()
	{
		return date;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ar.alme.morninformer.core.news.NewsReport#getPiecesOfNews()
	 */
	public List<PieceOfNews> getPiecesOfNews()
	{
		return piecesOfNews;
	}

	@Override
	public NewsSourceDescription getNewsSource()
	{
		return this.source;
	}

}
