package com.fiftynineebays.dto.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ResultDTO {

	private boolean successful = true;
	private String message;
	private HttpStatus status;

	public void setForbidden(String message) {
		if (message != null) this.message = message;
		this.successful = false;
		this.status = HttpStatus.FORBIDDEN;
	}

	public void setBadRequest(String message) {
		if (message != null) this.message = message;
		this.successful = false;
		this.status = HttpStatus.BAD_REQUEST;
	}

	public void setNotFound(String message) {
		if (message != null) this.message = message;
		this.successful = false;
		this.status = HttpStatus.NOT_FOUND;
	}

	public void setServerError(String message) {
		if (message != null) this.message = message;
		this.successful = false;
		this.status = HttpStatus.INTERNAL_SERVER_ERROR;
	}

	public ResponseEntity<?> asResponseEntity() {
		if (status == null) {
			status = HttpStatus.OK;
		}
		return new ResponseEntity(this, status);
	}
	
}