package com.amazon.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Builder
public class BetResponse {
	private int id;
	private int statusCode;
	private String message;
}