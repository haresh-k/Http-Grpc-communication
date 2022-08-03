package com.eventfetcher.service;

import com.eventfetcher.model.EventInfo;

/**
 * Service class for any executions related to events
 * @author hareshk
 * @created 24/07/22
 */
public interface EventInfoService {
    public String publishEventLog(EventInfo eventInfo);
}
