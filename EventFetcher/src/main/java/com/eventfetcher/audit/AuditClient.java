package com.eventfetcher.audit;

import com.audit.stubs.log.LogRequest;
import com.audit.stubs.log.LogResponse;
import com.audit.stubs.log.LogServiceGrpc;
import com.eventfetcher.model.EventInfo;
import io.grpc.Channel;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

/**
 * Client to the audit service
 * @author hareshk
 * @created 24/07/22
 */
@Service
public class AuditClient {
    private static final Logger logger = Logger.getLogger(AuditClient.class.getName());
    private LogServiceGrpc.LogServiceBlockingStub logServiceBlockingStub;

    /**
     * Setting the logService stub using the grpc channel
     * @param channel
     */
    public void setLogServiceBlockingStub(Channel channel) {
        logger.info("Setting log service stub: " + channel.toString());
        this.logServiceBlockingStub = LogServiceGrpc.newBlockingStub(channel);
    }

    /**
     * Calls the grpc service for saving the event info values.
     * @param eventInfo
     * @return the type of storage in which the data is saved in the backend. e.g, FILE_STORAGE, DATABASE_STORAGE
     */
    public String publishLog(EventInfo eventInfo) {
        LogRequest logRequest = LogRequest.newBuilder()
                .setTimestamp(eventInfo.timestamp)
                .setUserId(eventInfo.userId)
                .setEvent(eventInfo.event)
                .build();
        logger.info("Publishing log for request: " + logRequest.toString());
        LogResponse logResponse = logServiceBlockingStub.logEvent(logRequest);
        logger.info("Received log response: " + logResponse.toString());
        return logResponse.getStorageType();
    }
}
