package com.eventfetcher.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Holds the Event info payload request(POJO) for the application
 * @author hareshk
 * @created 24/07/22
 */
@Getter
@Setter
public class EventInfo {
    public Long timestamp;
    public Long userId;
    public String event;

    @Override
    public String toString() {
        return "EventInfo{" +
                "timestamp=" + timestamp +
                ", userId=" + userId +
                ", event='" + event + '\'' +
                '}';
    }
}
