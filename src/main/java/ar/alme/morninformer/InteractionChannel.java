package ar.alme.morninformer;

import ar.alme.morninformer.news.NewsReport;

public interface InteractionChannel
{

	public ChannelType getChannelType();

	public void open() throws ChannelOpeningException;

	public abstract void sendNewsReport(ContactData contactData, NewsReport newsReport);

}