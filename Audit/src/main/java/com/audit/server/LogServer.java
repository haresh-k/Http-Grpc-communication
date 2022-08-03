package com.audit.server;

import com.audit.common.StorageType;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import com.audit.service.LogServiceImpl;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The grpc server hosting the microservice
 * @author hareshk
 * @created 24/07/22
 */
public class LogServer {
    private static final Logger logger = Logger.getLogger(LogServer.class.getName());

    public static void main(String[] args) {
        Server server = ServerBuilder.forPort(8081)
                .addService(new LogServiceImpl(StorageType.FILE_STORAGE))
                .build();

        try {
            server.start();
            logger.log(Level.INFO, "RESULT SERVER STARTED ON PORT 8081");
            server.awaitTermination();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "RESULT SERVER DID NOT START");
        } catch (InterruptedException e) {
            logger.log(Level.SEVERE, "SERVER SHUT DOWN ON INTERRUPTED");
        }

    }
}
