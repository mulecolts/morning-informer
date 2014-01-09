package ar.alme.morninformer.news;

import java.util.Date;
import java.util.List;

import ar.alme.morninformer.NewsSourceDescription;

public interface NewsReport
{

	public abstract void setTitle(String feedTitle);

	public abstract void setAuthor(String author);

	public abstract void setDate(Date date);

	public abstract void addPieceOfNews(PieceOfNews pieceOfNews);

	public abstract String getTitle();

	public abstract String getAuthor();

	public abstract Date getDate();

	public abstract List<PieceOfNews> getPiecesOfNews();

	public abstract NewsSourceDescription getNewsSource();

}