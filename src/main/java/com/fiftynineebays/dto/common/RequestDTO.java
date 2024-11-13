package com.fiftynineebays.dto.common;

import java.io.BufferedReader;
import java.util.Enumeration;

import org.springframework.http.HttpHeaders;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RequestDTO {
	private JsonNode parsedJson;
	private String jsonString;
	private HttpServletRequest request;
	private HttpHeaders headers;
	private Integer actorUserId;
	private Integer actorCompanyId;
	private String actorType;

	public RequestDTO(HttpServletRequest request) {
		this.request = request;
		parseBody();
		setHeaders();
		setActorUserId();
		setActorCompanyId();
		setActorType();
	}

	public void setActorUserId() {
		if (request.getParameter("actorUserId") != null) {
			this.actorUserId = Integer.parseInt(request.getParameter("actorUserId"));
		}
	}

	public void setActorType(){
		if (request.getParameter("actorType") != null) {
			this.actorType = request.getParameter("actorType");
		}
	}

	public void setActorCompanyId() {
		if (request.getParameter("actorCompanyId") != null) {
			this.actorCompanyId = Integer.parseInt(request.getParameter("actorCompanyId"));
		}
	}

	private void setHeaders() {
		HttpHeaders headers = new HttpHeaders();
		
		Enumeration<String> headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String headerName = (String) headerNames.nextElement();
			String headerValue = request.getHeader(headerName);
			headers.set(headerName, headerValue);
		}
	}
	public JsonNode parseBody() {
		try {
			StringBuilder buffer = new StringBuilder();
			BufferedReader reader = request.getReader();
			String line;
			while((line = reader.readLine()) != null){
				buffer.append(line);
			}
			jsonString = buffer.toString();
			parsedJson = new ObjectMapper().readTree(buffer.toString());
			return parsedJson;
		} catch (Exception e) {
			return null;
		}

	}
}