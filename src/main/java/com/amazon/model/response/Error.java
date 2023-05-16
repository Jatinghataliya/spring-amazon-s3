package com.amazon.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Builder
public class Error {
	private int errorId;
	private int errorCode;
	private String errorMessage;
	private boolean status;
}