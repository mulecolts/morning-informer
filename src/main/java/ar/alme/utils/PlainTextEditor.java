package ar.alme.utils;

import java.text.DateFormat;
import java.util.List;

import ar.alme.morninformer.news.NewsReport;
import ar.alme.morninformer.news.PieceOfNews;

public class PlainTextEditor
{

	private static final String NEW_LINE = "\n";

	public String writeReport(NewsReport report)
	{
		String text = "";

		text += report.getTitle();
		text += NEW_LINE;
		text += this.getDateFormat().format(report.getDate());
		text += NEW_LINE + NEW_LINE;

		List<PieceOfNews> piecesOfNews = report.getPiecesOfNews();
		for (PieceOfNews poNews : piecesOfNews) {
			text += poNews.getTitle();
			text += NEW_LINE;
			String description = poNews.getDescription();
			if (description != null && !description.isEmpty()) {
				text += description;
				text += NEW_LINE;
			}
			text += poNews.getLink();
			text += NEW_LINE + NEW_LINE;
		}

		return text;
	}

	private DateFormat getDateFormat()
	{
		return DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG);
	}

}
