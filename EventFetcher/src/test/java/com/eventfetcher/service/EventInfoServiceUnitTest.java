package com.eventfetcher.service;

import com.eventfetcher.audit.AuditClient;
import com.eventfetcher.model.EventInfo;
import io.grpc.Channel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.env.Environment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
public class EventInfoServiceUnitTest {
    @Mock
    AuditClient auditClient;

    @Mock
    Environment env;

    @InjectMocks
    EventInfoServiceImpl eventInfoService;

    @Test
    void test01_validFileSystemLogging() {
        EventInfo eventInfo = new EventInfo();
        eventInfo.timestamp = Long.valueOf(1658644133);
        eventInfo.userId = Long.valueOf(102800);
        eventInfo.event = "Hello";

        when(env.getProperty("audit.server.url")).thenReturn("localhost");
        when(env.getProperty("audit.server.port")).thenReturn("8083");
        doNothing().when(auditClient).setLogServiceBlockingStub(any(Channel.class));
        when(auditClient.publishLog(any(EventInfo.class))).thenReturn("FILE_SYSTEM");

        assertEquals("FILE_SYSTEM", eventInfoService.publishEventLog(eventInfo));
    }
}
