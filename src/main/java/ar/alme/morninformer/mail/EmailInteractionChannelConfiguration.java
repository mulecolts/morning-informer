package ar.alme.morninformer.mail;

import ar.mil.cideso.correo.configuracion.ServidorCorreo;

public class EmailInteractionChannelConfiguration {

	private String displayableName;
	private String newsSubject = Messages.getString("Defaults.SUBJECT"); //$NON-NLS-1$
	private final ServidorCorreo accountData;

	public EmailInteractionChannelConfiguration(ServidorCorreo accountData,
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
		return accountData.getProtocoloSaliente().getPuerto();
	}

	public String getSMTPHost() {
		return accountData.getProtocoloSaliente().getServidor();
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

	public ServidorCorreo getAccountData() {
		return accountData;
	}

}
