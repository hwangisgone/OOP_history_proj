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
	private String url;
	
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

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
        sb.append("Location {")
	    	.append("\n name='").append(name)
	    	.append("'\n otherNames='").append(otherNames)
	    	.append("'\n located='").append(located)
	    	.append("'\n position='").append(position)
	    	.append("'\n worship='").append(worship)
	    	.append("'\n gradeType='").append(gradeType)
	    	.append("'\n grade='").append(grade)
	    	.append("'\n image=").append(image)
	    	.append("\n}");

        return sb.toString();
    }

}
