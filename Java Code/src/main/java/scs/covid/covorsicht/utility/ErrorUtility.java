package scs.covid.covorsicht.utility;

public enum ErrorUtility {

	CITY_NOT_EXIST("SUCCESS", "Success"), NO_COUNTRY_EXISTS("NO_COUNTRY_EXISTS", "Country does not exist"),
	NO_ERROR("SUCCESS", "Success");

	private String name;
	private String message;

	private ErrorUtility(String name, String message) {
		this.name = name;
		this.message = message;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


}
