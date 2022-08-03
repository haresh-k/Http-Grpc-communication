package com.eventfetcher.service;

import com.eventfetcher.model.EventInfo;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

/**
 * Payload validator - to check and verify the payload passed as application request
 * @author hareshk
 * @created 24/07/22
 */
@Service
public class PayloadValidatorService {

    private static final Logger logger = Logger.getLogger(PayloadValidatorService.class.getName());

    /**
     * Used for checking if the event payload is valid
     * @param eventInfo
     * @return true if payload has valid data
     */
    public Pair<Boolean, String> isValidPayload(EventInfo eventInfo) {
        logger.info("Validating eventInfo object: " + eventInfo.toString());
        Long timestamp = eventInfo.timestamp;
        Long userId = eventInfo.userId;
        String event = eventInfo.event;
        if (timestamp == null || userId == null || event == null) {
            String message = "Bad data provided for eventInfo object: " + eventInfo.toString();
            logger.warning(message);
            return Pair.of(false, message);
        }
        return Pair.of(true, "");
    }
}
