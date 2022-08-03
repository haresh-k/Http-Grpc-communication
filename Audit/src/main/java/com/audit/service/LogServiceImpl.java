package com.audit.service;

import com.audit.stubs.log.ErrorResponse;
import com.audit.stubs.log.LogRequest;
import com.audit.stubs.log.LogResponse;
import com.audit.stubs.log.LogServiceGrpc;
import com.audit.common.StorageType;
import io.grpc.Metadata;
import io.grpc.protobuf.ProtoUtils;
import io.grpc.stub.StreamObserver;
import com.audit.model.Event;

import java.util.logging.Logger;

/**
 * The audit service to log the event object
 * @created 24/07/22
 */
public class LogServiceImpl extends LogServiceGrpc.LogServiceImplBase {

    private static final Logger logger = Logger.getLogger(LogServiceImpl.class.getName());

    LogPublisher logPublisher;

    /**
     * Creates publisher object based on the storage type passed
     * @param storageType
     */
    public LogServiceImpl(StorageType storageType) {
        if (storageType.equals(StorageType.FILE_STORAGE)) {
            logger.info("Log type received: " + storageType);
            logPublisher = new FileLogPublisher();
        }
        //Other Loggers of LogType are to be implemented.
        // For now only File Storage exists for case study.
    }

    /**
     * Calls the publisher to log the event object
     * @param logRequest
     * @param responseObserver
     */
    @Override
    public void logEvent(LogRequest logRequest, StreamObserver<LogResponse> responseObserver) {
        logger.info("Publishing logs for logRequest : " + logRequest.toString());
        try {
            String storageType = logPublisher.publishLog(new Event.EventBuilder()
                    .userId(logRequest.getUserId())
                    .timestamp(logRequest.getTimestamp())
                    .event(logRequest.getEvent())
                    .build());
            LogResponse logResponse = LogResponse.newBuilder()
                    .setStorageType(storageType)
                    .build();
            logger.info("Received response for logRequest : " + logRequest.toString());
            responseObserver.onNext(logResponse);
            logger.info("Response : " + logResponse.toString());
            responseObserver.onCompleted();
        } catch (IllegalStateException e) {
            logger.info("Got the illegal exception error: " + e.toString());
            Metadata.Key<ErrorResponse> errorResponseKey
                    = ProtoUtils.keyForProto(ErrorResponse.getDefaultInstance());
            ErrorResponse errorResponse = ErrorResponse.newBuilder()
                            .setErrorMessage(e.toString())
                            .build();
            Metadata metadata = new Metadata();
            metadata.put(errorResponseKey, errorResponse);
            responseObserver.onError(io.grpc.Status.INVALID_ARGUMENT.withDescription(e.toString())
                    .asRuntimeException(metadata));
        }
    }
}
