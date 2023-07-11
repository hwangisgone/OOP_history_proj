package entity;

import java.util.List;

public class Dynasty extends Entity {
	List<String> longName;
	List<String> nativeName;
	String yearEnd;
	String yearStart;

	String currency;
	String status;
	String religion;
	String language;
	String governmentType;
	List<String> capitals;


	public List<String> getLongName() {
		return longName;
	}

	public void setLongName(List<String> longName) {
		this.longName = longName;
	}


	public List<String> getNativeName() {
		return nativeName;
	}


	public void setNativeName(List<String> nativeName) {
		this.nativeName = nativeName;
	}

	public String getYearEnd() {
		return yearEnd;
	}


	public void setYearEnd(String yearEnd) {
		this.yearEnd = yearEnd;
	}


	public String getYearStart() {
		return yearStart;
	}


	public void setYearStart(String yearStart) {
		this.yearStart = yearStart;
	}


	public String getCurrency() {
		return currency;
	}


	public void setCurrency(String currency) {
		this.currency = currency;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getReligion() {
		return religion;
	}


	public void setReligion(String religion) {
		this.religion = religion;
	}


	public String getLanguage() {
		return language;
	}


	public void setLanguage(String language) {
		this.language = language;
	}


	public String getGovernmentType() {
		return governmentType;
	}


	public void setGovernmentType(String governmentType) {
		this.governmentType = governmentType;
	}


	public List<String> getCapitals() {
		return capitals;
	}


	public void setCapitals(List<String> capitals) {
		this.capitals = capitals;
	}


	public Dynasty() {
		// TODO Auto-generated constructor stub
	}


	@Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Tên đầy đủ: ").append(longName)
            .append("\n Tên khác: ").append(nativeName)
            .append("\n (").append(yearStart).append(" - ").append(yearEnd).append(")")
            .append("\n Đơn vị tiền tệ: ").append(currency)
            .append("\n Vị thế: ").append(status)
            .append("\n Tôn giáo: ").append(religion)
            .append("\n Ngôn ngữ: ").append(language)
            .append("\n Chính trị: ").append(governmentType)
            .append("\n Thủ đô: ").append(String.join(", ", capitals));
        return sb.toString();
    }

}
