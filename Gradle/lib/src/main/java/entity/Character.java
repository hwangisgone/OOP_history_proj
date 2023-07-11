/*
	- Character class is used to model the entities which are historical character
	- Each character has an unique ID, which is inherited from Entity class
	- Character has relationship with other Entity, such as:
		1. dynasty entity
 */

package entity;

import java.util.HashMap;
import java.util.Map;

import crawldata.crawler.crawlm.data.Date;


public class Character extends Entity {



	private Date dateOfBirth;		// can be convert to String by toString
	private Date dateOfDeath;		// can be convert to String by toString
	private String fullName;
	private String father;
	private String mother;
	private String dynasty;
	private String biography;

	public Character() {

	}

	public Character(Map<String, String> map) {
		setID(map.get("id"));
		setDescription(map.get("description"));
		dateOfBirth = new Date(map.get("dateOfBirth"));
		dateOfDeath = new Date(map.get("dateOfDeath"));
		this.fullName = map.get("name");
		this.father = map.get("father");
		this.mother = map.get("mother");
		this.dynasty = map.get("dynasty");
		this.biography = map.get("biography");
	}	// close Character

	public Map<String, String> toMap() {
		Map<String, String> conversion = new HashMap<>();

		conversion.put("id", id);
		conversion.put("description", description);

		conversion.put("dateOfBirth", dateOfBirth.toString());
		conversion.put("dateOfDeath", dateOfDeath.toString());
		conversion.put("name", fullName);
		conversion.put("father", father);
		conversion.put("mother", mother);
		conversion.put("dynasty", dynasty);
		conversion.put("biography", biography);

		return conversion;
	}

	// getters for fields
	public String getFullName() {
		if (fullName == null || fullName.isBlank()) {
			return id;

		} else {
			return fullName;
		}

	}	// close getFullName

	public String getDateOfBirth() {
		return dateOfBirth.toString();
	}	// close getDateOfBirth

	public String getDateOfDeath() {
		return dateOfDeath.toString();
	}	// close getDateOfDeath

	public String getFather() {
		return father;
	}	// close getFather

	public String getMother() {
		return mother;
	}	// close getMother

	public String getDynasty() {
		return dynasty;
	}	// close getDynasty

	public String getBiography() {
		return biography;
	}	// close getBiography

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public void setDateOfDeath(Date dateOfDeath) {
		this.dateOfDeath = dateOfDeath;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public void setFather(String father) {
		this.father = father;
	}

	public void setMother(String mother) {
		this.mother = mother;
	}

	public void setDynasty(String dynasty) {
		this.dynasty = dynasty;
	}

	public void setBiography(String biography) {
		this.biography = biography;
	}

	@Override
	public String toString() {
		StringBuffer info = new StringBuffer("");
		info.append("Tên: " + getID() + "\n");
		info.append("Tên đầy đủ: " + getFullName() + "\n");
		info.append("Ngày sinh: " + getDateOfBirth() + "\n");
		info.append("Ngày mất: " + getDateOfDeath() + "\n");
		info.append("Bố: " + getFather() + "\n");
		info.append("Mẹ: " + getMother() + "\n");
		info.append("Triều đại: " + getDynasty() + "\n");
		return info.toString();
	}	// close toString

}	// close