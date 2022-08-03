package com.eventfetcher.configuration;

import com.eventfetcher.service.EventInfoService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class EventInfoServiceTestConfiguration {
    @Bean
    @Primary
    public EventInfoService eventInfoService() {
        return Mockito.mock(EventInfoService.class);
    }
}
