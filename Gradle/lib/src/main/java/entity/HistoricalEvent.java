package entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HistoricalEvent extends Entity{
	@JsonProperty("time")
    protected String time;

	@JsonProperty("dynasty_related")
	protected String dynasty_related;

	@JsonProperty("related_to")
	protected List<String> related_to;

	public String getTime() {
		return this.time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getDynasty() {
		return this.dynasty_related;
	}

	public void setDynasty(String dynasty) {
		this.dynasty_related = dynasty;
	}

	public List<String> getRelatedTo() {
		return this.related_to;
	}

	public void setRelatedTo(List<String> relatedTo) {
		this.related_to = related_to;
	}

	public HistoricalEvent() {
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("HistoricalEvent{")
				.append("\n name=").append(id)
				.append("'\n time='").append(time)
				.append("\n dynasty related='").append(dynasty_related)
				.append("'\n related to='").append(related_to)
				.append("\n}");
		return sb.toString();
	}
}

