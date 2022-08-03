package com.eventfetcher.controller;

import com.eventfetcher.ApiTestBase;
import com.eventfetcher.configuration.EventInfoServiceTestConfiguration;
import com.eventfetcher.model.EventInfo;
import com.eventfetcher.service.EventInfoService;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.Assert.assertEquals;

@ActiveProfiles("test")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@ContextConfiguration(classes = EventInfoServiceTestConfiguration.class)
public class EventInfoControllerIntegrationTest extends ApiTestBase {
    String uri = "/v1/event/";

    @Autowired
    private EventInfoService eventInfoService;

    @Before
    public void setUp() {
        //Add anything to be done before each of the test
    }

    @Test
    public void test01_createEventLogWithNoUserId() throws Exception {
        EventInfo eventInfo = new EventInfo();
        eventInfo.timestamp = Long.valueOf(1658644133);
        eventInfo.event = "Hello";
        String inputJson = super.mapToJson(eventInfo);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(inputJson)).andReturn();
        assertEquals(400, mvcResult.getResponse().getStatus());
    }

    @Test
    public void test02_createEventLogWithNoTimestamp() throws Exception {
        EventInfo eventInfo = new EventInfo();
        eventInfo.userId = Long.valueOf(102800);
        eventInfo.event = "Hello";
        String inputJson = super.mapToJson(eventInfo);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(inputJson)).andReturn();
        assertEquals(400, mvcResult.getResponse().getStatus());
    }

    @Test
    public void test03_createEventLogWithNoEvent() throws Exception {
        EventInfo eventInfo = new EventInfo();
        eventInfo.timestamp = Long.valueOf(1658644133);
        eventInfo.userId = Long.valueOf(102800);
        String inputJson = super.mapToJson(eventInfo);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(inputJson)).andReturn();
        assertEquals(400, mvcResult.getResponse().getStatus());
    }

    @Test
    public void test04_createEventLogWithValidData() throws Exception {
        EventInfo eventInfo = new EventInfo();
        eventInfo.timestamp = Long.valueOf(1658644133);
        eventInfo.userId = Long.valueOf(102800);
        eventInfo.event = "Hello";
        String inputJson = super.mapToJson(eventInfo);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(inputJson)).andReturn();
        assertEquals(201, mvcResult.getResponse().getStatus());
    }
}
