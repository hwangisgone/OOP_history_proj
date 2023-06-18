/*
	- Character class is used to model the entities which are historical character
	- Each character has an unique ID, which is inherited from Entity class
	- Character has relationship with other Entity, such as:	
		1. dynasty entity
 */

package data.entity;

import data.entity.Entity;
import data.entity.utils.Date;
import exception.data.NoIdException;
import java.util.Map;


public class Character extends Entity {

	private Date dateOfBirth;
	private Date dateOfDeath;
	
	public Character(Map<String, String> map) throws NoIdException {
		super(map);
		dateOfBirth = new Date(map.get("dateOfBirth"));
		dateOfDeath = new Date(map.get("dateOfDeath"));
	}	// close Character

}	// close 