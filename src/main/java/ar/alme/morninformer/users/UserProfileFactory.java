package ar.alme.morninformer.users;

import java.util.List;

import ar.alme.morninformer.ContactData;
import ar.alme.morninformer.NewsSourceDescription;
import ar.alme.morninformer.mail.EmailContactData;

public class UserProfileFactory {

	/**
	 * For comfortable testing
	 * 
	 * @param name
	 * @param contactName
	 * @param emailAddress
	 * @param emailFormat
	 * @param newsSources
	 * @return
	 */
	public static UserProfile createUserProfile(String name,
			String contactName, String emailAddress, Preferences emailFormat,
			NewsSourceDescription... newsSources) {

		UserProfile userProfile = new UserProfile();
		userProfile.setName(name);
		ContactData emailContactData = new EmailContactData(contactName,
				emailAddress);
		emailContactData.setPreference(Preferences.EMAIL_FORMAT, emailFormat);
		userProfile.addContactData(emailContactData);
		for (NewsSourceDescription newsSource : newsSources)
			userProfile.addNewsSource(newsSource);
		return userProfile;
	}

	public static UserProfile createUserProfile(String userName,
			List<ContactData> contactDataList,
			List<NewsSourceDescription> newsSourceNames) {

		UserProfile userProfile = new UserProfile();
		userProfile.setName(userName);
		for (ContactData contactData : contactDataList)
			userProfile.addContactData(contactData);
		for (NewsSourceDescription newsSource : newsSourceNames)
			userProfile.addNewsSource(newsSource);
		return userProfile;
	}

}
