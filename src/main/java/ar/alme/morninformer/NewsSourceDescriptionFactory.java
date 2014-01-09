package ar.alme.morninformer;

import java.util.Properties;

public class NewsSourceDescriptionFactory {

	public static NewsSourceDescription create(String type,
			Properties properties) {
		NewsSourceDescription data = new NewsSourceDescription(
				type.toUpperCase());
		data.addProperties(properties);
		return data;
	}

	public static NewsSourceDescription create(String type, String name,
			Properties properties) {
		NewsSourceDescription data = new NewsSourceDescription(
				type.toUpperCase(), name);
		data.addProperties(properties);
		return data;
	}

	public static NewsSourceDescription create(String type) {
		NewsSourceDescription data = new NewsSourceDescription(
				type.toUpperCase());
		return data;
	}

	public static NewsSourceDescription create(String type, String name) {
		NewsSourceDescription data = new NewsSourceDescription(
				type.toUpperCase(), name);
		return data;
	}
}
