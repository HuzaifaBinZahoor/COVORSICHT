package scs.covid.covorsicht.utility;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonResponse {

	ObjectMapper mapper = new ObjectMapper();

	private String status;
	private String message;
	private Object payLoad;


	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getPayLoad() {
		return payLoad;
	}

	public void setPayLoad(Object payLoad) {
		try {
			this.payLoad = mapper.writeValueAsString(payLoad);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.payLoad = null;
		}
	}
}
