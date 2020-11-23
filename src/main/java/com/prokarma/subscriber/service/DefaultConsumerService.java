package com.prokarma.subscriber.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import com.prokarma.subscriber.converter.DefaultMessageRequestMaskConverter;
import com.prokarma.subscriber.entity.Audit;
import com.prokarma.subscriber.model.MessageRequest;
import com.prokarma.subscriber.repository.AuditDataRepository;
import com.prokarma.subscriber.util.ObjectMapperUtil;

@Service
public class DefaultConsumerService implements ConsumerService {

    private static final Logger logger = LoggerFactory.getLogger(DefaultConsumerService.class);

    @Autowired
    private AuditDataRepository auditDataRepository;

    @Autowired
    private DefaultMessageRequestMaskConverter messageRequestConverter;

    @Override
    @KafkaListener(topics = "${cloudkarafka.topic}")
    public void consumeService(String messageRequestString) throws Exception {
        MessageRequest messageRequest =
                ObjectMapperUtil.returnObjectFromJsonString(messageRequestString);
        MessageRequest maskMessageRequest = messageRequestConverter.convert(messageRequest);
        logger.info("Started to consume messageRequest : {} ", maskMessageRequest);
        Audit auditEntity = buildAuditEntity(messageRequestString, messageRequest);
        auditDataRepository.save(auditEntity);
        logger.info("Finished to consume messageRequest : {} ", maskMessageRequest);

    }


    private Audit buildAuditEntity(String messageRequestString,
            MessageRequest messageRequest) {
        Audit auditEntity = new Audit();
        auditEntity.setCustomerNumber(messageRequest.getCustomerNumber());
        auditEntity.setPayload(messageRequestString);
        return auditEntity;
    }



}
