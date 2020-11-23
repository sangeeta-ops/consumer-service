package com.prokarma.subscriber.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@Tag("integration")
@SpringBootTest
public class DefaultConsumerIntegrationTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ConsumerService consumerService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void testConsumeServiceWithSuccess() throws Exception {
        String messageRequestString = buildMessageRequestStringForSucessResponse();
        assertDoesNotThrow(() -> consumerService.consumeService(messageRequestString));
    }

    @Test
    void testConsumeServiceWithErrorResponse() throws Exception {
        String messageRequestString = buildMessageRequestStringForErrorResponse();
        Throwable exception = assertThrows(Exception.class,
                () -> consumerService.consumeService(messageRequestString));
        assertNotNull(exception);
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
