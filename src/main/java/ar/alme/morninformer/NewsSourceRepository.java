package ar.alme.morninformer;

import java.util.HashMap;
import java.util.Map;

import ar.alme.morninformer.feeds.RSSNewsSourceFactory;
import ar.alme.morninformer.feeds.UnknownNewsSourceException;

public class NewsSourceRepository {

	private Map<NewsSourceDescription, NewsSource> cache = new HashMap<NewsSourceDescription, NewsSource>();

	private boolean isTwitter(NewsSourceDescription description) {
		return description.getType().equals(NewsSourceDescription.TYPE_TWITTER);
	}

	private boolean isRSS(NewsSourceDescription description) {
		return description.getType().equals(NewsSourceDescription.TYPE_RSS);

	}

	public NewsSource get(NewsSourceDescription description)
			throws UnknownNewsSourceException {
		NewsSource newsSource = this.cache.get(description);

		if (newsSource == null) {
			if (this.isRSS(description)) {
				newsSource = RSSNewsSourceFactory.instance().create(
						description.getName());
			} else if (this.isTwitter(description)) {
				newsSource = RSSNewsSourceFactory.instance().createTwitter(
						description.getProperty("screenName"));
			} else
				throw new UnknownNewsSourceException(description.toString());
			this.cache.put(description, newsSource);
		}

		return newsSource;
	}

}
