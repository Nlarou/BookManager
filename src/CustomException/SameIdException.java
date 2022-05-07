package CustomException;

/**
 * @author Nathaniel Larouche
 * Exception lancé lorsque deux id sont égal.
 */
public class SameIdException extends Exception  {
	
	private int id1;
	private int id2;
	public SameIdException() {
		
	}
	public SameIdException(int id1, int id2) {
		super();
		this.id1=id1;
		this.id2=id2;
	}
	@Override
	public String toString() {
		return "SameIdException ["+id1+" = "+ id2+"]";
	}
}
