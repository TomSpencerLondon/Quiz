package com.tomspencerlondon.quiz;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.AbstractRequestLoggingFilter;

@Configuration
public class RequestLoggingFilterConfig {

    @Bean
    public AbstractRequestLoggingFilter logFilter() {
        var filter = new BeforeOnlyRequestLoggingFilter();
        filter.setIncludeQueryString(true);
        filter.setIncludePayload(true);
        filter.setMaxPayloadLength(1000);
        filter.setIncludeClientInfo(false);
        filter.setIncludeHeaders(false);
        filter.setBeforeMessagePrefix("");
        filter.setBeforeMessageSuffix("");
        return filter;
    }
}
