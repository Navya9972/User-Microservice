package com.connected.car.user.entity;


import lombok.Data;

@Data
public class ApiResponse {
	private boolean ApiSuccess;
	private String responseMessage;
	private Object responseData;


	public ApiResponse(boolean ApiSuccess, String responseMessage, Object responseData) {
		super();
		
		this.ApiSuccess = ApiSuccess;
		this.responseMessage = responseMessage;
		this.responseData = responseData;
	}


	public ApiResponse(boolean ApiSuccess, String responseMessage) {
		super();
		this.ApiSuccess = ApiSuccess;
		this.responseMessage = responseMessage;
	}

	@Override
	public String toString() {
		return "ApiResponse [ApiSuccess=" + ApiSuccess + ", responseMessage=" + responseMessage
				+ ", responseData=" + responseData + "]";
	}

	
	

}
