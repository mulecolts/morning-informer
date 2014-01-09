package ar.alme.utils;

import java.util.LinkedList;
import java.util.List;

import ar.alme.morninformer.users.ParseException;

public class SimpleHTMLTextExtractor
{
	private String htmlSource;

	public SimpleHTMLTextExtractor(String htmlSource)
	{
		super();
		this.htmlSource = htmlSource;
	}

	public List<String> getText() throws ParseException
	{
		LinkedList<String> texts = new LinkedList<String>();

		// check if there is text before the HTML code itself
		int firstBraceIndex = htmlSource.indexOf("<");
		if (firstBraceIndex == -1)
			throw new ParseException("Not HTML");
		if (firstBraceIndex > 0) {
			String substring = htmlSource.substring(0, firstBraceIndex);
			if (!substring.trim().isEmpty())
				texts.add(substring);
		}
		this.omNomNom(htmlSource.substring(firstBraceIndex), texts);
		return texts;
	}

	private void omNomNom(String source, LinkedList<String> texts) throws ParseException
	{
		int beginIndex, endIndex;

		beginIndex = source.indexOf(">");
		if (beginIndex == -1)
			throw new ParseException();

		String croppedSource = source.substring(beginIndex + 1);
		String trimmedCroppedSource = croppedSource.trim();
		if (trimmedCroppedSource.isEmpty())
			return; // reached the end
		else { // not empty
			endIndex = croppedSource.indexOf("<");
			if (endIndex == -1) {
				// no more tags ahead, is there any text left?
				if (!trimmedCroppedSource.isEmpty()) {
					texts.add(croppedSource);
				}
				return;
			}
		}

		String targetSource = croppedSource.substring(0, endIndex);
		if (!targetSource.trim().isEmpty()) {
			texts.add(targetSource);
		}
		this.omNomNom(croppedSource.substring(endIndex), texts);
	}

	public String getConcatTrimmedText() throws ParseException
	{
		List<String> text = this.getText();
		String result = "";
		for (int i = 0; i < text.size(); i++) {
			result += text.get(i).trim();
			if (!(i == text.size() - 1)) // not last line
				result += "\n";
		}
		return result;
	}
}