package com.prokarma.subscriber.aop;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.prokarma.subscriber.entity.Error;
import com.prokarma.subscriber.repository.ErrorDataRepository;

@Aspect
@Component
public class DefaultSubscriberAspect {

    private static final Logger logger = LoggerFactory.getLogger(DefaultSubscriberAspect.class);

    @Autowired
    private ErrorDataRepository errorDataRepository;

    @AfterThrowing(
            pointcut = "execution(* com.prokarma.subscriber.service.DefaultConsumerService.*(..)) and args(messageRequestString)",
            throwing = "ex")
    public void logError(Exception ex, String messageRequestString) {
        Error errorEntity = buildErrorEntity(messageRequestString, ex);
        errorDataRepository.save(errorEntity);
        logger.info("Error occure in consume service and messageRequest : {} ",
                messageRequestString);
    }

    private Error buildErrorEntity(String messageRequestString, Exception e) {
        Error errorEntity = new Error();
        errorEntity.setPayload(messageRequestString);
        errorEntity.setErrorDescription(e.getMessage());
        errorEntity.setErrorType(e.getClass().getName());
        return errorEntity;
    }

}


