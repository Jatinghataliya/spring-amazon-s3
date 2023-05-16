package com.amazon.services;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazon.model.request.BetRequest;
import com.amazon.model.response.BetResponse;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class S3Service {

	private final AmazonS3 s3Client;

	@Value("${aws.s3.bucket}")
	private String bucketName;

	public S3Service(AWSCredentialsProvider awsCredentialsProvider) {
		this.s3Client = AmazonS3ClientBuilder.standard().withCredentials(awsCredentialsProvider)
				.withRegion(Regions.DEFAULT_REGION).build();
	}

	public void uploadFile(String key, InputStream inputStream) throws IOException {
		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentLength(inputStream.available());
		s3Client.putObject(new PutObjectRequest(bucketName, key, inputStream, metadata));
	}

	public void deleteFile(String key) {
		s3Client.deleteObject(bucketName, key);
	}

	public S3Object downloadFile(String key) {
		return s3Client.getObject(bucketName, key);
	}

	public BetResponse putObject(BetRequest betRequest) throws JsonProcessingException {
		BetResponse betResponse = BetResponse.builder().id(1).statusCode(200).message("Success").build();
		String key = UUID.randomUUID().toString();
		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentType("application/json");
		byte[] requestBytes = new ObjectMapper().writeValueAsBytes(betRequest);
		byte[] responseBytes = new ObjectMapper().writeValueAsBytes(betResponse);
		ByteArrayInputStream requestStream = new ByteArrayInputStream(requestBytes);
		ByteArrayInputStream responseStream = new ByteArrayInputStream(responseBytes);
		s3Client.putObject(bucketName, key + "/request.json", requestStream, metadata);
		s3Client.putObject(bucketName, key + "/response.json", responseStream, metadata);
		return betResponse;
	}
}