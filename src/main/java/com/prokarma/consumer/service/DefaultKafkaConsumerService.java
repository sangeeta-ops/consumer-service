package com.prokarma.consumer.service;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prokarma.consumer.entity.AddressEntity;
import com.prokarma.consumer.entity.AuditEntity;
import com.prokarma.consumer.entity.CustomerEntity;
import com.prokarma.consumer.entity.ErrorEntity;
import com.prokarma.consumer.masking.CustomerDataMasking;
import com.prokarma.consumer.model.MessageRequest;
import com.prokarma.consumer.repository.AuditDataRepository;
import com.prokarma.consumer.repository.ErrorDataRepository;
import com.prokarma.consumer.repository.KafkaDataRepository;

@Service
public class DefaultKafkaConsumerService implements kafkaConsumerService {

	private static final Logger logger = LoggerFactory.getLogger(DefaultKafkaConsumerService.class);

	@Autowired
	private KafkaDataRepository kafkaDataRepository;

	@Autowired
	private AuditDataRepository auditDataRepository;

	@Autowired
	private ErrorDataRepository errorDataRepository;

	@Override
	@KafkaListener(topics = "first_topic")
	public void consume(String messageRequestString) {
		ObjectMapper objectMapper = new ObjectMapper();
		MessageRequest messageRequest = null;
		try {
			messageRequest = objectMapper.readValue(messageRequestString, MessageRequest.class);
			AuditEntity auditEntity = buildAuditEntity(messageRequestString, messageRequest);
			auditDataRepository.save(auditEntity);
			messageRequest = CustomerDataMasking.maskCustomerData(messageRequest);
			logger.info("Start to consume messageRequest : {} ", messageRequest);
			CustomerEntity entity = buildCustomerEntityObject(messageRequest);
			entity = kafkaDataRepository.save(entity);
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

	private CustomerEntity buildCustomerEntityObject(MessageRequest messageRequest) {
		CustomerEntity entity = new CustomerEntity();
		entity.setCountry(messageRequest.getCountry());
		entity.setBirthDate(messageRequest.getBirthDate());
		entity.setCountryCode(messageRequest.getCountryCode());
		entity.setCustomerNumber(messageRequest.getCustomerNumber());
		entity.setEmail(messageRequest.getEmail());
		entity.setFirstName(messageRequest.getFirstName());
		entity.setLastName(messageRequest.getLastName());
		entity.setCustomerStatusEnum(messageRequest.getCustomerStatus());
		AddressEntity addressEntity = buildAddressEntityObject(messageRequest);
		entity.setAddress(addressEntity);
		return entity;
	}

	private AddressEntity buildAddressEntityObject(MessageRequest messageRequest) {
		AddressEntity addressEntity = new AddressEntity();
		addressEntity.setAddressLine1(messageRequest.getAddress().getAddressLine1());
		addressEntity.setAddressLine2(messageRequest.getAddress().getAddressLine2());
		addressEntity.setPostalCode(messageRequest.getAddress().getPostalCode());
		addressEntity.setStreet(messageRequest.getAddress().getStreet());
		return addressEntity;
	}

	private ErrorEntity buildErrorEntity(String messageRequestString, Exception e) {
		ErrorEntity errorEntity = new ErrorEntity();
		errorEntity.setPayload(messageRequestString);
		errorEntity.setErrorDescription(e.getMessage());
		errorEntity.setErrorType(e.getClass().getName());
		return errorEntity;
	}

}