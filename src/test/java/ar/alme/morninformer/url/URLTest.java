package ar.alme.morninformer.url;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Ignore;
import org.junit.Test;

public class URLTest {

	private static final String URL = "http://www.lanacion.com.ar/1420070"
			+ "-mujica-pidio-terminar-la-polemica-"
			+ "por-la-difusion-del-monitoreo-del-rio-uruguay";

	/*
	 * Ok, today we learned that URL objects should not be used lightly, because
	 * they tend to connect to the Internet and do nasty things but still, they
	 * are useful to validate a URL's syntax, and return the string
	 * representation.
	 */
	@Test
	@Ignore
	public void test() {
		try {
			URL url = new URL(URL);
			assertEquals(url.toExternalForm(), url.toString());
			assertEquals(URL, url.toExternalForm());
			System.out.println(url);
		} catch (MalformedURLException e) {
			fail();
		}
	}

	@Test(expected = MalformedURLException.class)
	public void exception() throws MalformedURLException {
		try {
			// URL url = new URL(URL);
			// URL url = new URL("foo@bar");
			new URL("bar://foo.com");
		} catch (MalformedURLException e) {
			// System.out.println(e);
			// System.out.println(e.getLocalizedMessage());
			System.out.println(e.getMessage());
			throw e;
		}
	}

	@Test
	public void noLink() {
		try {
			new URL("http://no-link-available");
		} catch (MalformedURLException e) {
			// ignore, impossible
			fail();
		}
	}
}
