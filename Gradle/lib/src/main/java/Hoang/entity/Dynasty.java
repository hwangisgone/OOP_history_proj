package Hoang.entity;

import java.util.List;

public class Dynasty extends Entity {
	List<String> longName;
	List<String> nativeName;
	String name;
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


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
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

	
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Country{")
                .append("longName=").append(longName)
                .append("\n, nativeName=").append(nativeName)
                .append("\n, name='").append(name).append('\'')
                .append("\n, yearEnd='").append(yearEnd).append('\'')
                .append("\n, yearStart='").append(yearStart).append('\'')
                .append("\n, currency='").append(currency).append('\'')
                .append("\n, status='").append(status).append('\'')
                .append("\n, religion='").append(religion).append('\'')
                .append("\n, language='").append(language).append('\'')
                .append("\n, governmentType='").append(governmentType).append('\'')
                .append("\n, capitals=").append(capitals)
                .append("\n}");
        return sb.toString();
    }
}
