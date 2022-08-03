package com.audit.service;

import com.audit.common.StorageType;
import com.audit.model.Event;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.logging.Logger;

/**
 * File system implementation to save logs in directories
 * @author hareshk
 * @created 24/07/22
 */
public class FileLogPublisher implements LogPublisher {
    private static final Logger logger = Logger.getLogger(FileLogPublisher.class.getName());

    private static final String filedir = System.getProperty("user.dir") + "\\storage";

    /**
     * Creates a file in the $rootDir/storage for the event object
     * @param event
     * @return string value of FILE_STORAGE
     */
    @Override
    public String publishLog(Event event) {
        String filepath = filedir + "/" + event.timestamp + "-" + event.userId;
        logger.info("Saving logs: " + event.toString() + " at " + filepath);
        try {
            FileOutputStream fileOut = new FileOutputStream(filepath);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(event);
            objectOut.close();
        } catch (IOException e) {
            logger.severe("Logs couldn't be saved in file for event: " + event.toString());
            throw new RuntimeException(e);
        }
        return StorageType.FILE_STORAGE.toString();
    }
}
