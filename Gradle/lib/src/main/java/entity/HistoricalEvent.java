package entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import util.ExtraStringUtil;

public class HistoricalEvent extends Entity{

	@JsonProperty("time")
	protected String time;

	@JsonProperty("dynasty_related")
	protected List<String> dynasty_related;

	@JsonProperty("related_to")
	protected List<String> related_to;

	@JsonProperty("characters_related")
	private List<String> characters_related;

	@JsonProperty("result")
	private String result;

	@JsonProperty("location")
	private String location;

	@JsonProperty("id")
	private String id;

	@JsonProperty("description")
	private String description;

	@JsonProperty("url")
	private String url;

	public String getTime() {
		return this.time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public List<String> getDynasty() {
		return this.dynasty_related;
	}

	public void setDynasty(List<String> dynasty) {
		this.dynasty_related = dynasty;
	}

	public List<String> getRelatedTo() {
		return this.related_to;
	}

	public void setRelatedTo(List<String> relatedTo) {
		this.related_to = relatedTo;
	}

	public List<String> getCharacters() {
		return this.characters_related;
	}

	public void setCharacters(List<String> charaters) {
		this.characters_related = charaters;
	}

	public String getResult() {
		return this.result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
	}


	public HistoricalEvent() {
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("\n Thời gian: ").append(time)
				.append("\n Nhà nước liên quan: ").append(ExtraStringUtil.addComma(dynasty_related))
				.append("\n Địa điểm: ").append(location)
				.append("\n Kết quả: ").append(result);
		return sb.toString();
	}
}