package com.prokarma.subscriber.converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.springframework.stereotype.Component;
import com.prokarma.subscriber.model.MessageRequest;

@Component
public class MessageRequestConverter {

    public MessageRequest maskConversion(MessageRequest messageRequest) {
        messageRequest.setCustomerNumber(
                messageRequest.getCustomerNumber().replaceAll(("\\w(?<=\\w{7})"), "*"));
        LocalDate birthDate = messageRequest.getBirthDate();
        String formattedDate = birthDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        formattedDate = formattedDate.replaceAll(("\\d(?=[^-]*?-)"), "*");
        messageRequest.setBirthDate(LocalDate.parse(formattedDate));
        messageRequest.setEmail(messageRequest.getEmail().replaceAll((".(?<!.{5})"), "*"));

        return messageRequest;
    }

}
