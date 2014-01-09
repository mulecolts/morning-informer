package ar.alme.morninformer.feeds;

public class UnknownNewsSourceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UnknownNewsSourceException() {
	}

	public UnknownNewsSourceException(String arg0) {
		super(arg0);
	}

	public UnknownNewsSourceException(Throwable arg0) {
		super(arg0);
	}

	public UnknownNewsSourceException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
