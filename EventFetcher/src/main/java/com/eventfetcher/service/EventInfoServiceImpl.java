package com.eventfetcher.service;

import com.eventfetcher.audit.AuditClient;
import com.eventfetcher.model.EventInfo;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

/**
 * Service class implementation for any executions related to events
 * @author hareshk
 * @created 24/07/22
 */
@Service
public class EventInfoServiceImpl implements EventInfoService {
    private static final Logger logger = Logger.getLogger(EventInfoServiceImpl.class.getName());

    @Autowired
    AuditClient auditClient;
    @Autowired
    Environment env;

    private String getAuditUrl() {
        logger.info("Calling service under url:" + env.getProperty("audit.server.url") + ":" + env.getProperty("audit.server.port"));
        return env.getProperty("audit.server.url") + ":" + env.getProperty("audit.server.port");
    }

    protected AuditClient setAuditClient(ManagedChannel channel) {
        auditClient.setLogServiceBlockingStub(channel);
        return auditClient;
    }

    public String publishEventLog(EventInfo eventInfo) {
        logger.info("Creating channel for calling publish service");
        ManagedChannel channel = ManagedChannelBuilder.forTarget(getAuditUrl())
                .usePlaintext()
                .build();
        setAuditClient(channel);
        return auditClient.publishLog(eventInfo);
    }
}
