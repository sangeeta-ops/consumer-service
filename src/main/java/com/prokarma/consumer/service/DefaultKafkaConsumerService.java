package com.prokarma.consumer.service;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prokarma.consumer.entity.AuditEntity;
import com.prokarma.consumer.entity.ErrorEntity;
import com.prokarma.consumer.masking.CustomerDataMasking;
import com.prokarma.consumer.model.MessageRequest;
import com.prokarma.consumer.repository.AuditDataRepository;
import com.prokarma.consumer.repository.ErrorDataRepository;

@Service
public class DefaultKafkaConsumerService implements kafkaConsumerService {

	private static final Logger logger = LoggerFactory.getLogger(DefaultKafkaConsumerService.class);

	@Autowired
	private AuditDataRepository auditDataRepository;

	@Autowired
	private ErrorDataRepository errorDataRepository;

	@Override
	@KafkaListener(topics = "${cloudkarafka.topic}")
	public void consume(String messageRequestString) {
		ObjectMapper objectMapper = new ObjectMapper();
		MessageRequest messageRequest = null;
		try {
			messageRequest = objectMapper.readValue(messageRequestString, MessageRequest.class);
			AuditEntity auditEntity = buildAuditEntity(messageRequestString, messageRequest);
			auditDataRepository.save(auditEntity);
			messageRequest = CustomerDataMasking.maskCustomerData(messageRequest);
			logger.info("Start to consume messageRequest : {} ", messageRequest);
			logger.info("Finished to consume messageRequest : {} ", messageRequest);
		} catch (Exception e) {
			ErrorEntity errorEntity = buildErrorEntity(messageRequestString, e);
			errorDataRepository.save(errorEntity);
			logger.info("Finished to consume messageRequest : {} ", messageRequest);
		}
	}

	private AuditEntity buildAuditEntity(String messageRequestString, MessageRequest messageRequest) {
		AuditEntity auditEntity = new AuditEntity();
		auditEntity.setCustomerNumber(messageRequest.getCustomerNumber());
		auditEntity.setPayload(messageRequestString);
		return auditEntity;
	}

	private ErrorEntity buildErrorEntity(String messageRequestString, Exception e) {
		ErrorEntity errorEntity = new ErrorEntity();
		errorEntity.setPayload(messageRequestString);
		errorEntity.setErrorDescription(e.getMessage());
		errorEntity.setErrorType(e.getClass().getName());
		return errorEntity;
	}

}