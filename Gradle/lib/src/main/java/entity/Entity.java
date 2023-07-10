package entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class Entity {

	protected String id;
	protected String description;
	protected String url;
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getName() {
		return id;
	}

	public void setName(String name) {
		this.id = name;
	}
	

	public abstract String toString();
}
