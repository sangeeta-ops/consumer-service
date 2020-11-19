package com.prokarma.subscriber.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.prokarma.subscriber.converter.MessageRequestConverter;
import com.prokarma.subscriber.entity.AuditEntity;
import com.prokarma.subscriber.model.MessageRequest;
import com.prokarma.subscriber.repository.AuditDataRepository;

@Service
public class DefaultConsumerService implements ConsumerService {

    private static final Logger logger = LoggerFactory.getLogger(DefaultConsumerService.class);

    @Autowired
    private AuditDataRepository auditDataRepository;

    @Autowired
    private MessageRequestConverter messageRequestConverter;

    @Override
    @KafkaListener(topics = "${cloudkarafka.topic}")
    public void consumeService(String messageRequestString) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        MessageRequest messageRequest =
                objectMapper.readValue(messageRequestString, MessageRequest.class);
        MessageRequest maskMessageRequest = messageRequestConverter.maskConversion(messageRequest);
        logger.info("Started to consume messageRequest : {} ", maskMessageRequest);
        AuditEntity auditEntity = buildAuditEntity(messageRequestString, messageRequest);
        auditDataRepository.save(auditEntity);
        logger.info("Finished to consume messageRequest : {} ", maskMessageRequest);

    }


    private AuditEntity buildAuditEntity(String messageRequestString,
            MessageRequest messageRequest) {
        AuditEntity auditEntity = new AuditEntity();
        auditEntity.setCustomerNumber(messageRequest.getCustomerNumber());
        auditEntity.setPayload(messageRequestString);
        return auditEntity;
    }



}
