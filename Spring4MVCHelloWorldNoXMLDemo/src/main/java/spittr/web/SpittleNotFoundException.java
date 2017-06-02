package spittr.web;

public class SpittleNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private long id;
	private static final long serialVersionUID = 1L;
public SpittleNotFoundException(long id) {
	// TODO Auto-generated constructor stubthi
	this.id = id;
}
	public long getSpittleId() {
		// TODO Auto-generated method stub
		return id;
	}

}
