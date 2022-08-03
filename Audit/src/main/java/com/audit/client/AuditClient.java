package com.audit.client;

import com.audit.stubs.log.LogRequest;
import com.audit.stubs.log.LogServiceGrpc;
import io.grpc.Channel;

import java.util.logging.Logger;

/**
 * LogClient to manually test the microservice
 * @author hareshk
 * @created 24/07/22
 */
public class AuditClient {
    private static final Logger logger = Logger.getLogger(AuditClient.class.getName());
    private LogServiceGrpc.LogServiceBlockingStub logServiceBlockingStub;

    /**
     * Creates a grpc channel to access the audit microservice
     * @param channel
     */
    public AuditClient(Channel channel) {
        logServiceBlockingStub = LogServiceGrpc.newBlockingStub(channel);
    }

    /**
     *Calls the grpc service for saving the event info values.
     * @param timestamp
     * @param userId
     * @param event
     * @return the type of storage in which the data is saved in the backend. e.g, FILE_STORAGE, DATABASE_STORAGE
     */
    public String publishLog(Long timestamp, Long userId, String event) {
        LogRequest logRequest = LogRequest.newBuilder()
                .setTimestamp(timestamp)
                .setUserId(userId)
                .setEvent(event)
                .build();
        logger.info("Starting here");
        logServiceBlockingStub.logEvent(logRequest);
        logger.info("Reached end");
        return "";
    }
}
