package entity;

public class Festival extends Entity {
	String location; // Địa điểm, cử hành bởi
	String date; // Ngày, ngày (âm lịch)
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	String type; // Kiểu

	@Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n Tên: ").append(id);
        if (location != null) {
        	sb.append("\n Địa điểm: ").append(location);
		}
		if (date != null) {
        	sb.append("'\n Thời gian: ").append(date);
		}
		if (type != null) {
        	sb.append("'\n Loại: ").append(type);
		}
        return sb.toString();
    }
}
