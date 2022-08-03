package com.audit.service;

import com.audit.common.StorageType;
import com.audit.stubs.log.LogRequest;
import com.audit.stubs.log.LogResponse;
import com.audit.stubs.log.LogServiceGrpc;
import io.grpc.StatusRuntimeException;
import io.grpc.inprocess.InProcessChannelBuilder;
import io.grpc.inprocess.InProcessServerBuilder;
import io.grpc.testing.GrpcCleanupRule;
import org.junit.Rule;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LogServiceImplUnitTest {
    @Rule
    public final GrpcCleanupRule grpcCleanupRule = new GrpcCleanupRule();

    @Test
    public void test01_validStorageReply() throws IOException {
        String serverName = InProcessServerBuilder.generateName();
        grpcCleanupRule.register(InProcessServerBuilder
                .forName(serverName).directExecutor().addService(new LogServiceImpl(StorageType.FILE_STORAGE))
                .build().start());
        LogServiceGrpc.LogServiceBlockingStub logServiceBlockingStub = LogServiceGrpc
                .newBlockingStub(grpcCleanupRule.register(InProcessChannelBuilder.forName(serverName).directExecutor()
                .build()));
        LogResponse logResponse = logServiceBlockingStub.logEvent(LogRequest.newBuilder()
                .setTimestamp(Long.valueOf(1658665433))
                .setUserId(Long.valueOf(834900))
                .setEvent("User registered")
                .build());
        assertEquals(StorageType.FILE_STORAGE.toString(), logResponse.getStorageType());
    }

    @Test
    public void test02_invalidUserIdDataReply() throws IOException {
        String serverName = InProcessServerBuilder.generateName();
        grpcCleanupRule.register(InProcessServerBuilder
                .forName(serverName).directExecutor().addService(new LogServiceImpl(StorageType.FILE_STORAGE))
                .build().start());
        LogServiceGrpc.LogServiceBlockingStub logServiceBlockingStub = LogServiceGrpc
                .newBlockingStub(grpcCleanupRule.register(InProcessChannelBuilder.forName(serverName).directExecutor()
                        .build()));
        assertThrows(StatusRuntimeException.class, () -> {
            logServiceBlockingStub.logEvent(LogRequest.newBuilder()
                    .setTimestamp(Long.valueOf(1658665433))
                    .setEvent("User registered")
                    .build());
        });
    }

    @Test
    public void test03_invalidTimestampDataReply() throws IOException {
        String serverName = InProcessServerBuilder.generateName();
        grpcCleanupRule.register(InProcessServerBuilder
                .forName(serverName).directExecutor().addService(new LogServiceImpl(StorageType.FILE_STORAGE))
                .build().start());
        LogServiceGrpc.LogServiceBlockingStub logServiceBlockingStub = LogServiceGrpc
                .newBlockingStub(grpcCleanupRule.register(InProcessChannelBuilder.forName(serverName).directExecutor()
                        .build()));
        assertThrows(StatusRuntimeException.class, () -> {
            logServiceBlockingStub.logEvent(LogRequest.newBuilder()
                    .setTimestamp(0)
                    .setUserId(Long.valueOf(834900))
                    .setEvent("User registered")
                    .build());
        });
    }
}
