package entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

// public class HistoricalEventWar extends HistoricalEvent{

// 	@JsonProperty("characters_related")
//     private List<String> characters_related;

// 	@JsonProperty("result")
//     private String result;

// 	@JsonProperty("location")
//     private String location;

//     public List<String> getCharacters() {
// 		return this.characters_related;
// 	}

// 	public void setCharacters(List<String> charaters) {
// 		this.characters_related = charaters;
// 	}

// 	public String getResult() {
// 		return this.result;
// 	}

// 	public void setResult(String result) {
// 		this.result = result;
// 	}

// 	public String getLocation() {
// 		return this.location;
// 	}

// 	public void setLocation(String location) {
// 		this.location = location;
// 	}

// 	public HistoricalEventWar() {
// 	}

// 	@Override
// 	public String toString() {
// 		StringBuilder sb = new StringBuilder();
// 		sb.append("HistoricalEvent{")
// 				.append("\n name=").append(id)
// 				.append("'\n time='").append(time)
// 				.append("'\n dynasty related='").append(dynasty_related)
//                 .append("'\n location='").append(location)
// 				.append("'\n related to='").append(related_to)
//                 .append("'\n characters related='").append(characters_related)
//                 .append("'\n result='").append(result)
// 				.append("\n}");
// 		return sb.toString();
// 	}
// }
public class HistoricalEventWar extends Entity {

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


	public HistoricalEventWar() {
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("\n ").append(id)
			.append("'\n Thời gian ").append(time)
			.append("'\n Triều đại: ").append(dynasty_related)
			.append("'\n Địa điểm: ").append(location)
			.append("'\n Liên quan: ").append(related_to)
			.append("'\n Nhân vật liên quan: ").append(characters_related)
			.append("'\n result='").append(result);

		return sb.toString();
	}
}