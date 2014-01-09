package ar.alme.morninformer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import ar.alme.morninformer.feeds.FeedLoadException;
import ar.alme.morninformer.feeds.UnknownNewsSourceException;
import ar.alme.morninformer.news.NewsReport;
import ar.alme.morninformer.users.UserProfile;

public class Informer
{

	private Map<ChannelType, InteractionChannel> channels = new HashMap<ChannelType, InteractionChannel>();
	private List<UserProfile> users = new LinkedList<UserProfile>();
	private NewsSourceRepository newsSourceRepository = new NewsSourceRepository();

	public void addUserProfile(UserProfile userProfile)
	{
		this.users.add(userProfile);
	}

	public void addInteractionChannel(InteractionChannel channel)
	{
		this.channels.put(channel.getChannelType(), channel);
	}

	public void inform() throws UnknownNewsSourceException
	{
		try {
			// load all the will-be-needed reports first, so to avoid fetching
			// one same report for every user that wants it
			List<NewsReport> reports = getAllDesiredReports();

			for (UserProfile user : users) {
				List<ContactData> userContactDataList = user.getContactDataList();
				List<NewsReport> filteredReports = filterReportsForSingleUser(reports, user);
				this.dispatch(filteredReports, userContactDataList);
			}

		} catch (FeedLoadException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns only the reports to which the user is subscribed.
	 * 
	 * @param reports
	 * @param user
	 * @return
	 */
	private List<NewsReport> filterReportsForSingleUser(List<NewsReport> reports, UserProfile user)
	{
		Collection<NewsSourceDescription> desiredNewsSources = user.getDesiredNewsSources();
		List<NewsReport> filteredReports = new ArrayList<NewsReport>(desiredNewsSources.size());
		for (NewsReport report : reports)
			if (desiredNewsSources.contains(report.getNewsSource()))
				filteredReports.add(report);
		return filteredReports;
	}

	/**
	 * Fetches all the news reports according to what the user group is
	 * interested in.
	 * 
	 * @return
	 * @throws FeedLoadException
	 * @throws UnknownNewsSourceException
	 */
	private List<NewsReport> getAllDesiredReports() throws FeedLoadException, UnknownNewsSourceException
	{
		Collection<NewsSource> targetNewsSources = this.getAllUsersDesiredSources();
		List<NewsReport> reports = this.getNewsReports(targetNewsSources);
		return reports;
	}

	private void dispatch(List<NewsReport> filteredReports, List<ContactData> contactDataList)
	{
		for (ContactData contactData : contactDataList) {
			ChannelType destChannelType = contactData.getChannelType();
			InteractionChannel interactionChannel = this.channels.get(destChannelType);
			for (NewsReport newsReport : filteredReports)
				interactionChannel.sendNewsReport(contactData, newsReport);
		}
	}

	private Collection<NewsSource> getAllUsersDesiredSources() throws UnknownNewsSourceException
	{
		HashSet<NewsSource> targetNewsSources = new HashSet<NewsSource>();

		for (UserProfile user : users)
			for (NewsSourceDescription newsSourceDescription : user.getDesiredNewsSources()) {
				NewsSource newsSource = this.newsSourceRepository.get(newsSourceDescription);
				targetNewsSources.add(newsSource);
			}
		// TODO contemplate non-existing or non-instantiated news sources

		return targetNewsSources;
	}

	private List<NewsReport> getNewsReports(Collection<NewsSource> targetNewsSources) throws FeedLoadException
	{
		List<NewsReport> newsReports = new LinkedList<NewsReport>();
		for (NewsSource source : targetNewsSources) {
			NewsReport newsReport = source.latestNews();
			newsReports.add(newsReport);
		}
		return newsReports;
	}

}
