package com.bocxy.Property.common;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Map;

@JsonPropertyOrder({"Status","Message","Data"})
@Component
public class ResponseDo {

    private int Status;
	private String Message;
	private Object Data;
	private boolean result;
	private String token;
	private String newUser;
	
	public int getStatus() {
		return Status;
	}
	public void setStatus(int status) {
		this.Status = status;
	}
	public String getMessage() {
		return Message;
	}
	public void setMessage(String message) {
		this.Message = message;
	}
	public Object getData() {
		return Data;
	}
	public void setData(Object data) {
		this.Data = data;
	}
	
	public boolean isResult() {
		return result;
	}
	public void setResult(boolean result) {
		this.result = result;
	}
	
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getNewUser() {
		return newUser;
	}
	public void setNewUser(String newUser) {
		this.newUser = newUser;
	}
	public ResponseDo setResponse(int Status,String Message,Map<String, Object> Data) {
		ResponseDo res = new ResponseDo();
		res.setStatus(Status);
		res.setMessage(Message);
		if (Data != null)
			res.setData(Data);
		return res;
	}
	
	public ResponseDo setSuccessResponse(Map<String, Object> Data) {
		ResponseDo res = new ResponseDo();
		res.setStatus(1);
		res.setMessage("Success");
		res.setData(Data);
		return res;
	}
	
	public ResponseDo setSuccessResponse(String message) {
		ResponseDo res = new ResponseDo();
		res.setStatus(1);
		res.setMessage(message);
		return res;
	}
	
	public ResponseDo setSuccessResponse(String message,String manualpath) {
		ResponseDo res = new ResponseDo();
		res.setStatus(1);
		res.setMessage(message);
		res.setData(manualpath);
		return res;
	}
	
	public ResponseDo setSuccessResponse(Object Data) {
		ResponseDo res = new ResponseDo();
		res.setStatus(1);
		res.setMessage("Success");
		res.setData(Data);
		return res;
	}
	
	public ResponseDo setSuccessResponseArray(ArrayList<Object> Data) {
		ResponseDo res = new ResponseDo();
		res.setStatus(1);
		res.setMessage("Success");
		res.setData(Data);
		return res;
	}
	
	public ResponseDo setFailureNoDataFoundResponse() {
		ResponseDo res = new ResponseDo();
		res.setStatus(0);
		res.setMessage("No Data Found");
		return res;
	}
	
	public ResponseDo setPassFailureResponse() {
		ResponseDo res = new ResponseDo();
		res.setStatus(0);
		res.setMessage("Invalid Password");
		return res;
	}
	
	
	public ResponseDo setFailureResponse() {
		ResponseDo res = new ResponseDo();
		res.setStatus(0);
		res.setMessage("Failure");
		return res;
	}
	
	public ResponseDo setFailureResponse(String message ) {
		ResponseDo res = new ResponseDo();
		res.setStatus(0);
		res.setMessage(message);
		return res;
	}
	

	public ResponseDo setSuccessResponseBoolean(boolean result) {
		ResponseDo res = new ResponseDo();
		res.setStatus(1);
		res.setMessage("Success");
		res.setResult(result);
		return res;
	}
	
	public ResponseDo userKey(String token,String newUser) {
		
		ResponseDo res = new ResponseDo();
		res.setToken(token);
		res.setNewUser(newUser);
		res.setStatus(1);
		res.setMessage("Success");
		return res;
	}

}
