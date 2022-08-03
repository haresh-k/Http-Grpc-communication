package com.audit.service;

import com.audit.model.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class FileLogPublisherUnitTest {

    FileLogPublisher fileLogPublisher;

    String filePath;

    @BeforeEach
    void setup() {
        fileLogPublisher = new FileLogPublisher();
        filePath = System.getProperty("user.dir") + "\\storage\\";
    }

    @Test
    void test01_createValidFile() {
        Event event = new Event.EventBuilder()
                .userId(Long.valueOf(102800))
                .timestamp(Long.valueOf(1658665433))
                .event("User registered")
                .build();
        fileLogPublisher.publishLog(event);
        filePath += event.timestamp + "-" + event.userId;
        Path path = Paths.get(filePath);
        System.out.println(filePath);
        assertTrue(Files.exists(path));
    }
}
