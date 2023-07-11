package entity;

import java.util.Map;

public abstract class Entity {
	protected String id;
	protected String description;
	protected Map<String, String> additionalInfo;

	public Map<String, String> getAdditionalInfo() {
		return additionalInfo;
	}


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

	@Override
	public abstract String toString();
}
