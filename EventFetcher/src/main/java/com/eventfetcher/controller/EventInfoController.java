package com.eventfetcher.controller;

import com.eventfetcher.model.EventInfo;
import com.eventfetcher.model.StorageInfo;
import com.eventfetcher.service.EventInfoService;
import com.eventfetcher.service.PayloadValidatorService;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

/**
 * The controller for any event requests
 * @author hareshk
 * @created 24/07/22
 */
@RestController
@RequestMapping("/v1/event")
public class EventInfoController {

    private static final Logger logger = Logger.getLogger(EventInfoController.class.getName());

    @Autowired
    EventInfoService eventInfoService;

    @Autowired
    PayloadValidatorService payloadValidatorService;

    /**
     * Creates a persistent record of the event payload in the backend
     * @param eventInfo
     * @return the type of storage in which the data is saved in the backend. e.g, FILE_STORAGE, DATABASE_STORAGE
     * and the httpStatus
     */
    @PostMapping("/")
    public ResponseEntity<StorageInfo> publishEventLog(@RequestBody EventInfo eventInfo) {
        logger.info("Received request to add event log with data: " + eventInfo.toString());
        Pair<Boolean, String> checks = payloadValidatorService.isValidPayload(eventInfo);
        if (!checks.getLeft()) {
            return new ResponseEntity(checks.getRight(), HttpStatus.BAD_REQUEST);
        }
        try {
            String storage = eventInfoService.publishEventLog(eventInfo);
            logger.info("Event added with storage as: " + storage);
            StorageInfo storageInfo = new StorageInfo();
            storageInfo.storageType = storage;
            return new ResponseEntity(storageInfo, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
