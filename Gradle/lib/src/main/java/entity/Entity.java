package entity;

public abstract class Entity {
	String name;
	
	public abstract String toString();
	
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}
}
