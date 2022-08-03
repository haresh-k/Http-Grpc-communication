package com.audit.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Holds the Event info payload request(POJO) for the application
 * @author hareshk
 * @created 24/07/22
 */
@Getter
@Setter
public class Event implements Serializable {

    private static final long serialVersionUID = 1L;

    public final Long timestamp;

    public final Long userId;

    public final String event;

    public Event(EventBuilder eventBuilder) {
        this.timestamp = eventBuilder.timestamp;
        this.userId = eventBuilder.userId;
        this.event = eventBuilder.event;
    }

    @Override
    public String toString() {
        return "Event{" +
                "timestamp=" + timestamp +
                ", userId=" + userId +
                ", event='" + event + '\'' +
                '}';
    }

    /**
     * EventBuilder class for building event object - All fields are kept optional
     */
    public static class EventBuilder {
        private Long timestamp;
        private Long userId;
        private String event;
        public EventBuilder () { }

        public EventBuilder timestamp(Long timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public EventBuilder userId(Long userId) {
            this.userId = userId;
            return this;
        }

        public EventBuilder event(String event) {
            this.event = event;
            return this;
        }

        public Event build() {
            Event event =  new Event(this);
            validateEventObject(event);
            return event;
        }

        private void validateEventObject(Event event) {
            //validate the events here
            if (event.timestamp == 0 || event.userId == 0) {
                throw new IllegalStateException("Invalid timestamp/userid provided");
            }
        }
    }

}
