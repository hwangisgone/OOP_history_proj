/*
	- Character class is used to model the entities which are historical character
	- Each character has an unique ID, which is inherited from Entity class
	- Character has relationship with other Entity, such as:	
		1. dynasty entity
 */

package Source.data.entity;

import Source.data.entity.Entity;
import Source.data.entity.utils.Date;
import Source.exception.data.NoIdException;
import java.util.Map;


public class Character extends Entity {

	private Date dateOfBirth;
	private Date dateOfDeath;
	
	public Character(Map<String, String> map) throws NoIdException {
		super(map);
		dateOfBirth = new Date(map.get("dateOfBirth"));
		dateOfDeath = new Date(map.get("dateOfDeath"));
	}	// close Character


	@Override
	public String toString() {
		StringBuffer info = new StringBuffer("");
		info.append("ID: " + this.getProperty("id") + "\n");
		info.append("Name: " + this.getProperty("name") + "\n");
		info.append("Date of Birth: " + this.dateOfBirth + "\n");
		info.append("Date of Death: " + this.dateOfDeath + "\n");
		info.append("Father: " + this.getProperty("father") + "\n");
		info.append("Mother: " + this.getProperty("mother") + "\n");
		info.append("Dynasty: " + this.getProperty("dynasty") + "\n");
		info.append("Biography: " + this.getProperty("career&bio") + "\n");
		return info.toString();
	}	// close toString

}	// close 