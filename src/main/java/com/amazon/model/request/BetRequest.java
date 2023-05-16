package com.amazon.model.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Builder
public class BetRequest {
	private int id;
	private String name;
	private double amount;
	private String game;
	private long timestamp;
}