package com.amazon.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.amazon.model.request.BetRequest;
import com.amazon.model.response.BetResponse;
import com.amazon.services.S3Service;
import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
public class BettingController {

	@Autowired
	private S3Service s3Service;
	
	@PostMapping("/bet")
    public BetResponse placeBet(@RequestBody BetRequest betRequest) throws JsonProcessingException {
        return s3Service.putObject(betRequest);
    }
}
