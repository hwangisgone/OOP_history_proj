package entity;

import java.util.List;

import util.ExtraStringUtil;

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
        ExtraStringUtil.appendNotNull(sb, "\n Tên khác: ", otherNames);
        ExtraStringUtil.appendNotNull(sb, "\n Toạ độ: ", position);
        ExtraStringUtil.appendNotNull(sb, "\n Tôn thờ: ", worship); sb.append("\n");
        ExtraStringUtil.appendNotNull(sb, "\n\n Loại hình xếp hạng: ", gradeType);
        ExtraStringUtil.appendNotNull(sb, "\n Xếp hạng: ", grade);

        return sb.toString();
    }

}
