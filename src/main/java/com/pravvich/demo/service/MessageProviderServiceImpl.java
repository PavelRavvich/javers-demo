package com.pravvich.demo.service;

import org.javers.core.diff.changetype.ValueChange;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.Map;

import static java.util.Objects.nonNull;

@Service
// TODO: 2/2/2021 get rid from a hardcoded messages and switch to the message.properties file
@PropertySource("classpath:message.properties")
public class MessageProviderServiceImpl implements MessageProviderService {

    private final Map<String, String> messages = Map.of(
            "volume", "Transfer volume is: %s",
            "number","Account number is: %s",
            "balance","Account balance is: %s"
    );

    @Override
    public String convertToMessage(ValueChange valueChange) {
        String message = messages.get(valueChange.getPropertyName());
        return nonNull(message)
                ? String.format(message, valueChange.getRight().toString())
                : "";
    }

    @Override
    public Boolean hasMessage(ValueChange valueChange) {
        return messages.containsKey(valueChange.getPropertyName());
    }

}
