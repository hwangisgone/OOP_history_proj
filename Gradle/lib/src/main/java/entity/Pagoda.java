package entity;

public class Pagoda extends Location {
	private String religion;

	public String getReligion() {
		return religion;
	}

	public void setReligion(String religion) {
		this.religion = religion;
	}

	@Override
    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        sb.append("--> Pagoda {")
	    	.append("\n religion='").append(religion)
	    	.append("\n}");

        return sb.toString();
    }
}
