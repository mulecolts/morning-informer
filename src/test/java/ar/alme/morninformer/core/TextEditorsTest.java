package ar.alme.morninformer.core;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import ar.alme.morninformer.NewsSourceDescription;
import ar.alme.morninformer.news.DefaultNewsReport;
import ar.alme.morninformer.news.DefaultPieceOfNews;
import ar.alme.morninformer.news.NewsReport;
import ar.alme.morninformer.news.PieceOfNews;
import ar.alme.utils.HTMLEditor;
import ar.alme.utils.PlainTextEditor;

public class TextEditorsTest {

	public static final String TITLE = "REPORT TITLE";
	public static final String AUTHOR = "REPORT AUTHOR";
	public static final String TITLE_1 = "TITLE 1";
	public static final String DESCRIPTION_1 = "Description 1";
	public static final String TITLE_2 = "TITLE 2";
	public static final String DESCRIPTION_2 = "Description 2";
	public static Date DATE = null;
	public static URL LINK_1 = null;
	public static URL LINK_2 = null;
	private PlainTextEditor plainEditor;
	private HTMLEditor htmlEditor;
	private NewsReport newsReport;

	@BeforeClass
	public static void setUpClass() throws MalformedURLException {
		TextEditorsTest.DATE = new Date();
		TextEditorsTest.LINK_1 = new URL("http://article1.test.com");
		TextEditorsTest.LINK_2 = new URL("http://article2.test.com");
	}

	@Before
	public void setUp() throws Exception {
		this.plainEditor = new PlainTextEditor();
		this.htmlEditor = new HTMLEditor();
		this.newsReport = new TextEditorsTest.TestNewsReport(
				NewsSourceDescription.RSS_CLARIN);
	}

	@Ignore
	@Test
	public void testPlain() {
		// can't really think of a useful way of testing this
		System.out.println(plainEditor.writeReport(newsReport));
	}

	@Test
	public void testHTML() {
		// can't really think of a useful way of testing this
		System.out.println(htmlEditor.writeReport(newsReport));
	}

	public class TestNewsReport extends DefaultNewsReport {

		public TestNewsReport(NewsSourceDescription source) {
			super(source);
		}

		@Override
		public String getTitle() {
			return TITLE;
		}

		@Override
		public String getAuthor() {
			return AUTHOR;
		}

		@Override
		public Date getDate() {
			return DATE;
		}

		@Override
		public List<PieceOfNews> getPiecesOfNews() {
			ArrayList<PieceOfNews> list = new ArrayList<PieceOfNews>(2);
			list.add(new DefaultPieceOfNews(TITLE_1, DESCRIPTION_1, LINK_1));
			list.add(new DefaultPieceOfNews(TITLE_2, DESCRIPTION_2, LINK_2));
			return list;
		}

	}
}
