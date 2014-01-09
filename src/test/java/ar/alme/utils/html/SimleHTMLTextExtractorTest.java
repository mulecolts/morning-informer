package ar.alme.utils.html;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ar.alme.morninformer.users.ParseException;
import ar.alme.utils.SimpleHTMLTextExtractor;

public class SimleHTMLTextExtractorTest {

	private String htmlSource;
	private String htmlSource2;
	private String htmlSource3;
	private String htmlSource4;
	private String htmlSource5;

	@Before
	public void setUp() throws Exception {
		htmlSource = "<foo>\n\t<bar attr=attribute_text/>\n\t<baz>\n\t\tGood old text\n\t</baz>\n</foo>";
		htmlSource2 = "<foo>\n\t<bar attr=attribute_text/>\n\t<baz>\n\t\tGood old text\n\t</baz>\n\t<qux>\n\t\tMoar text\n\t</qux>\n</foo>";
		htmlSource3 = "<foo";
		htmlSource4 = "text<foo/>";
		htmlSource5 = "<foo/>text";
	}

	@Test
	public void test() throws ParseException {
		SimpleHTMLTextExtractor textExtractor = new SimpleHTMLTextExtractor(
				htmlSource);
		List<String> texts = textExtractor.getText();
		assertTrue(texts.size() == 1);
		String text = texts.get(0);
		assertEquals(text, "\n\t\tGood old text\n\t");
		assertEquals(text.trim(), "Good old text");
		assertEquals("Good old text", textExtractor.getConcatTrimmedText());
	}

	@Test
	public void test2() throws ParseException {
		SimpleHTMLTextExtractor textExtractor = new SimpleHTMLTextExtractor(
				htmlSource2);
		List<String> texts = textExtractor.getText();
		assertTrue(texts.size() == 2);
		String text1 = texts.get(0);
		assertEquals("\n\t\tGood old text\n\t", text1);
		assertEquals("Good old text", text1.trim());
		String text2 = texts.get(1);
		assertEquals("\n\t\tMoar text\n\t", text2);
		assertEquals("Moar text", text2.trim());
		assertEquals("Good old text\nMoar text",
				textExtractor.getConcatTrimmedText());
	}

	@Test(expected = ParseException.class)
	public void test3() throws ParseException {
		SimpleHTMLTextExtractor textExtractor = new SimpleHTMLTextExtractor(
				htmlSource3);
		textExtractor.getText();
	}

	@Test
	public void test4() throws ParseException {
		SimpleHTMLTextExtractor textExtractor = new SimpleHTMLTextExtractor(
				htmlSource4);
		assertEquals("text", textExtractor.getConcatTrimmedText());
	}

	@Test
	public void test5() throws ParseException {
		SimpleHTMLTextExtractor textExtractor = new SimpleHTMLTextExtractor(
				htmlSource5);
		assertEquals("text", textExtractor.getConcatTrimmedText());
	}

	// @Test
	// public void atest5() throws ParseException {
	// SimpleHTMLTextExtractor textExtractor = new SimpleHTMLTextExtractor(
	// "<img width='1' height='1' src='http://clarin.feedsportal.com/c/33088/f/577681/s/19d56a1d/mf.gif' border='0'/><div class='mf-viral'><table border='0'><tr><td valign='middle'><a href=\"http://res.feedsportal.com/viral/sendemail2_es.html?title=Un+video+enviado+por+un+conductor+muestra+c%C3%B3mo+se+maneja+en+la+ruta+7.+Maestr%C3%ADa+de+Clar%C3%ADn.&link=http%3A%2F%2Fwww.clarin.com%2Fsociedad%2Fenviado-conductor-muestra-Maestria-Clarin_3_584971505.html\" target=\"_blank\"><img src=\"http://rss.feedsportal.com/images/emailthis2_es.gif\" border=\"0\" /></a></td><td valign='middle'><a href=\"http://res.feedsportal.com/viral/bookmark_es.cfm?title=Un+video+enviado+por+un+conductor+muestra+c%C3%B3mo+se+maneja+en+la+ruta+7.+Maestr%C3%ADa+de+Clar%C3%ADn.&link=http%3A%2F%2Fwww.clarin.com%2Fsociedad%2Fenviado-conductor-muestra-Maestria-Clarin_3_584971505.html\" target=\"_blank\"><img src=\"http://rss.feedsportal.com/images/bookmark_es.gif\" border=\"0\" /></a></td></tr></table></div><br/><br/><a href=\"http://da.feedsportal.com/r/118010234828/u/89/f/577681/c/33088/s/19d56a1d/a2.htm\"><img src=\"http://da.feedsportal.com/r/118010234828/u/89/f/577681/c/33088/s/19d56a1d/a2.img\" border=\"0\"/></a>");
	// System.out.println(textExtractor.getConcatTrimmedText().length());
	// }
}
