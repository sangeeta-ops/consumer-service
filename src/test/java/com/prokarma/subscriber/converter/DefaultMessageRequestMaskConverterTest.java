package com.prokarma.subscriber.converter;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import com.prokarma.subscriber.model.Address;
import com.prokarma.subscriber.model.MessageRequest;
import com.prokarma.subscriber.util.CustomerStatusEnum;

@ExtendWith(MockitoExtension.class)
class DefaultMessageRequestMaskConverterTest {

    @InjectMocks
    DefaultMessageRequestMaskConverter defaultMessageRequestMaskConverter;

    @Test
    void testConvert() throws Exception {
        MessageRequest messageRequest = buildMessageRequestObject();
        MessageRequest expectedMessageRequest =
                defaultMessageRequestMaskConverter.convert(messageRequest);
        assertNotNull(expectedMessageRequest);
        assertTrue(expectedMessageRequest.getCustomerNumber().contains("****"));
        assertTrue(expectedMessageRequest.getBirthDate().contains("**-**"));
        assertTrue(expectedMessageRequest.getEmail().contains("****"));

    }

    private MessageRequest buildMessageRequestObject() {
        MessageRequest messageRequest = new MessageRequest();
        Address address = buildAddressObject();
        messageRequest.setAddress(address);
        messageRequest.setCountry("India");
        messageRequest.setCountryCode("IN");
        messageRequest.setCustomerNumber("CUST123456");
        messageRequest.setCustomerStatus(CustomerStatusEnum.OPEN);
        messageRequest.setEmail("David@gmail.com");
        messageRequest.setFirstName("David David");
        messageRequest.setLastName("Willam Willam");
        messageRequest.setMobileNumber("9912101210");
        messageRequest.setBirthDate("02-02-2020");
        return messageRequest;
    }

    private Address buildAddressObject() {
        Address address = new Address();
        address.setAddressLine1("Shivane");
        address.setAddressLine2("Pune");
        address.setPostalCode("41102");
        address.setStreet("Pune");
        return address;
    }

}
