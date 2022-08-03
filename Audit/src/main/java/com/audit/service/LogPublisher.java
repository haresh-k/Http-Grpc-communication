package com.audit.service;

import com.audit.model.Event;

/**
 * The LogPublisher class to publish logs into a storage type
 * @author hareshk
 * @created 24/07/22
 */
public interface LogPublisher {
    String publishLog(Event event);
}
