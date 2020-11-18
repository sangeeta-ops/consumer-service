package com.prokarma.subscriber.service;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prokarma.subscriber.entity.AuditEntity;
import com.prokarma.subscriber.entity.ErrorEntity;
import com.prokarma.subscriber.masking.CustomerDataMaskingUtil;
import com.prokarma.subscriber.model.MessageRequest;
import com.prokarma.subscriber.repository.AuditDataRepository;
import com.prokarma.subscriber.repository.ErrorDataRepository;

@Service
public class DefaultSubscriberService implements SubscriberService {

	private static final Logger logger = LoggerFactory.getLogger(DefaultSubscriberService.class);

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
			logger.info("Started to consume messageRequest : {} ", messageRequest);
			AuditEntity auditEntity = buildAuditEntity(messageRequestString, messageRequest);
			auditDataRepository.save(auditEntity);
			messageRequest = CustomerDataMaskingUtil.maskCustomerData(messageRequest);
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