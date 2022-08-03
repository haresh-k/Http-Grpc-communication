package com.eventfetcher.service;

import com.eventfetcher.model.EventInfo;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@Import(PayloadValidatorService.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PayloadValidatorServiceUnitTest {
    @Autowired
    PayloadValidatorService payloadValidatorService;

    @Test
    public void test01_validEvent() {
        EventInfo eventInfo = new EventInfo();
        eventInfo.timestamp = Long.valueOf(1658644133);
        eventInfo.userId = Long.valueOf(102800);
        eventInfo.event = "Hello";
        assertTrue(payloadValidatorService.isValidPayload(eventInfo).getLeft());
    }

    @Test
    public void test02_NoTimestamp() {
        EventInfo eventInfo = new EventInfo();
        eventInfo.userId = Long.valueOf(102800);
        eventInfo.event = "Hello";
        assertFalse(payloadValidatorService.isValidPayload(eventInfo).getLeft());
    }

    @Test
    public void test03_NoUserId() {
        EventInfo eventInfo = new EventInfo();
        eventInfo.timestamp = Long.valueOf(1658644133);
        eventInfo.event = "Hello";
        assertFalse(payloadValidatorService.isValidPayload(eventInfo).getLeft());
    }

    @Test
    public void test04_NoEvent() {
        EventInfo eventInfo = new EventInfo();
        eventInfo.timestamp = Long.valueOf(1658644133);
        eventInfo.userId = Long.valueOf(102800);
        assertFalse(payloadValidatorService.isValidPayload(eventInfo).getLeft());
    }
}
