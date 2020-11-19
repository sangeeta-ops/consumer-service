package com.prokarma.subscriber.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.fasterxml.jackson.core.JsonParseException;
import com.prokarma.subscriber.repository.AuditDataRepository;


@ExtendWith(MockitoExtension.class)
public class DefaultConsumerServiceTest {

    @InjectMocks
    private DefaultConsumerService defaultConsumerService;

    @Mock
    private AuditDataRepository auditDataRepository;

    @Test
    void testConsumeServiceWithSuccess() throws Exception {
        String messageRequestString = buildMessageRequestStringForSucessResponse();
        assertDoesNotThrow(() -> defaultConsumerService.consumeService(messageRequestString));
    }

    @Test
    void testConsumeServiceWithErrorResponse() throws Exception {
        String messageRequestString = buildMessageRequestStringForErrorResponse();
        Throwable exception = assertThrows(JsonParseException.class,
                () -> defaultConsumerService.consumeService(messageRequestString));
        assertNotNull(exception.getMessage());
    }

    private String buildMessageRequestStringForSucessResponse() {
        String jsonString =
                "{\"customerNumber\":\"CUST123456\",\"firstName\":\"David David\",\"lastName\":\"Willam Willam\",\"country\":\"India\",\"countryCode\":\"IN\",\"mobileNumber\":\"9912101210\",\"email\":\"David@gmail.com\",\"customerStatus\":\"OPEN\",\"birthDate\":\"02-02-2020\",\"address\":{\"addressLine1\":\"Shivane\",\"addressLine2\":\"Pune\",\"postalCode\":\"41102\",\"street\":\"pune\"}}";
        return jsonString;
    }


    private String buildMessageRequestStringForErrorResponse() {
        String jsonString = "Invalid data";
        return jsonString;
    }



}

