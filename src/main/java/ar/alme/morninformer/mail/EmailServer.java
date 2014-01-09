package ar.alme.morninformer.mail;

import ar.mil.cideso.correo.configuracion.CorreoAuthenticator;
import ar.mil.cideso.correo.configuracion.ProtocoloCorreo;
import ar.mil.cideso.correo.configuracion.ServidorCorreo;

public class EmailServer {
	
	private ServidorCorreo sc;
	
	public EmailServer(String user, String domain, String password, ProtocoloCorreo outgoing, ProtocoloCorreo incoming){
		sc = new ServidorCorreo(user, domain, password, outgoing, incoming);
	}
	
	
	public CorreoAuthenticator getAuthenticator(){
		return sc.getAuthenticator();
	}
	
	public ProtocoloCorreo getOutgoingSaliente(){
		return sc.getProtocoloSaliente();
	}
	
	public ServidorCorreo getInside(){
		return this.sc;
	}
}
