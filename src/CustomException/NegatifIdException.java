package CustomException;

/**
 * @author Nathaniel Larouche
 * Exception lanc� lorsque un nombre negatif est d�tect�
 */
public class NegatifIdException extends Exception{
	private int id;
	public NegatifIdException() {
		
	}
	public NegatifIdException(int id) {
		super();
		this.id=id;
	}
	@Override
	public String toString() {
		return "NegatifIdException [id=" + id + " need to be superior or equal to 0]";
	}
	
}
