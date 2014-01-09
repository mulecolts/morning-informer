package ar.alme.utils;

import java.text.DateFormat;
import java.util.List;

import ar.alme.morninformer.news.NewsReport;
import ar.alme.morninformer.news.PieceOfNews;

public class HTMLEditor
{

	private static final String NEW_LINE = "<br/>";
	private String text;

	public String writeReport(NewsReport report)
	{
		text = "";

		addBold(report.getTitle());
		newLine();
		add(getFormattedDate(report));
		newLine();

		List<PieceOfNews> piecesOfNews = report.getPiecesOfNews();
		for (PieceOfNews poNews : piecesOfNews) {
			newLine();
			addBold(poNews.getTitle());
			newLine();
			String description = poNews.getDescription();
			if (description != null && !description.isEmpty()) {
				add(description);
				newLine();
			}
			addLink(poNews.getLink(), "Ir a la nota");
			newLine();
		}

		return text;
	}

	private String getFormattedDate(NewsReport report)
	{
		return this.getDateFormat().format(report.getDate());
	}

	private void addLink(String link, String text)
	{
		this.text += "<a href=" + link + ">" + text + "</a>";
	}

	private void addBold(String text)
	{
		this.text += "<b>" + text + "</b>";
	}

	private void add(String text)
	{
		this.text += text;
	}

	private void newLine()
	{
		text += NEW_LINE;
	}

	private DateFormat getDateFormat()
	{
		return DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG);
	}

}
