package ar.alme.morninformer.mail;


public class EmailInteractionChannelConfiguration {

	private String displayableName;
	private String newsSubject = "(no subject)";
	private final EmailServer accountData;

	public EmailInteractionChannelConfiguration(EmailServer accountData,
			String newsSubject) {
		this.accountData = accountData;
		this.newsSubject = newsSubject;
	}

	public String getPassword() {
		return accountData.getAuthenticator().getPassword();
	}

	public String getUserName() {
		return accountData.getAuthenticator().getNombreCuentaParaLogin();
	}

	public int getSMTPPort() {
		return accountData.getOutgoingSaliente().getPuerto();
	}

	public String getSMTPHost() {
		return accountData.getOutgoingSaliente().getServidor();
	}

	public String getDisplayableName() {
		return displayableName == null ? getUserName() : displayableName;
	}

	public String getNewsEmailSubject() {
		return newsSubject;
	}

	public void setDisplayableName(String displayableName) {
		this.displayableName = displayableName;
	}

	public void setNewsSubject(String newsSubject) {
		this.newsSubject = newsSubject;
	}

	public EmailServer getAccountData() {
		return accountData;
	}

}
