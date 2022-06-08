package com.example.quiz;

import org.springframework.web.filter.AbstractRequestLoggingFilter;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.stream.Collectors;

import static java.lang.System.lineSeparator;

public class BeforeOnlyRequestLoggingFilter extends AbstractRequestLoggingFilter {

    @Override
    protected void beforeRequest(HttpServletRequest request, String message) {
        // don't show the before (it doesn't have the POST body)
    }

    @Override
    protected void afterRequest(HttpServletRequest request, String message) {
        logger.debug(message);
    }

    @Override
    protected String createMessage(HttpServletRequest request, String prefix, String suffix) {
        StringBuilder msg = new StringBuilder();
        msg.append(request.getMethod()).append(' ');
        msg.append(request.getRequestURI());

        String queryString = request.getQueryString();
        if (queryString != null) {
            msg.append('?').append(queryString);
        }
        String messagePayload = getMessagePayload(request);
        if (messagePayload != null) {
            messagePayload = URLDecoder.decode(messagePayload, StandardCharsets.UTF_8);
            String bodyAsKeyValues = Arrays.stream(messagePayload.split("&")).collect(Collectors.joining(lineSeparator()));
            msg.append(", body:").append(lineSeparator()).append(bodyAsKeyValues);
        }

        return msg.toString();
    }

}
