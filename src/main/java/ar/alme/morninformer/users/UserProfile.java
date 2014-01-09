package ar.alme.morninformer.users;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ar.alme.morninformer.ContactData;
import ar.alme.morninformer.NewsSourceDescription;


public class UserProfile {

	private List<NewsSourceDescription> newsSources = new ArrayList<NewsSourceDescription>();
	private List<ContactData> contactDataList = new ArrayList<ContactData>();
	private String name;

	public String getName() {
		return name;
	}

	public void addNewsSource(NewsSourceDescription newsSource) {
		this.newsSources.add(newsSource);
	}

	public void setName(String name) {
		this.name = name;
	}

	public void addContactData(ContactData contactData) {
		this.contactDataList.add(contactData);
	}

	public Collection<NewsSourceDescription> getDesiredNewsSources() {
		return this.newsSources;
	}

	public List<ContactData> getContactDataList() {
		return this.contactDataList;
	}

}
