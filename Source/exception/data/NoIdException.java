/*
	This an exception to alert when constructing an entity object without passing ID property
 */

package exception.data;

public class NoIdException extends Exception {
	
	public NoIdException() {
		super("Not found ID property while constructing the entity");
	}	// close 

}	// close NoIdException