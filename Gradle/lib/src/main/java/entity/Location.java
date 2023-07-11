package entity;

import java.util.List;

public class Location extends Entity {
	private List<String> otherNames;
	private String located;
	private String position;
	private String gradeType;
	private String grade;
	private String worship;

	private String image;

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public List<String> getOtherNames() {
		return otherNames;
	}

	public void setOtherNames(List<String> otherNames) {
		this.otherNames = otherNames;
	}

	public String getLocated() {
		return located;
	}

	public void setLocated(String located) {
		this.located = located;
	}

	public String getGradeType() {
		return gradeType;
	}

	public void setGradeType(String gradeType) {
		this.gradeType = gradeType;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getWorship() {
		return worship;
	}

	public void setWorship(String worship) {
		this.worship = worship;
	}


	@Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n Tên: ").append(id)
	    	.append("\n Tên khác: ").append(otherNames != null ? String.join(", ", otherNames) : "")
	    	.append("\n Vị trí: ").append(located)
	    	.append("\n Tọa độ: ").append(position)
	    	.append("\n Tôn thờ: ").append(worship).append("\n")
	    	.append("\n Loại hình xếp hạng").append(gradeType)
	    	.append("\n Xếp hạng: ").append(grade);

        return sb.toString();
    }

}
