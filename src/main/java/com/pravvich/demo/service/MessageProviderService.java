package com.pravvich.demo.service;

import org.javers.core.diff.changetype.ValueChange;

public interface MessageProviderService {
    String convertToMessage(ValueChange valueChange);
    Boolean hasMessage(ValueChange valueChange);
}
