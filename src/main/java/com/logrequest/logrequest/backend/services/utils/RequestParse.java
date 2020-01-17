package com.logrequest.logrequest.backend.services.utils;

import com.logrequest.logrequest.backend.models.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class RequestParse {

    private static final Logger LOG = LoggerFactory.getLogger(RequestParse.class);

    public RequestParse() {
    }

    private static Request parseLog(String log) {
        log = log.replaceAll("[\"\\[\\]]", "");
        String[] strings = log.split("\\s");

        String ipResponse = strings[0].substring(strings[0].indexOf(":") + 1);
        String ipRequest = strings[13];
        String method = strings[5];

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss");
        LocalDateTime timestamp = LocalDateTime.parse(strings[3], dateTimeFormatter);

        String rawPath = strings[6];
        String path = rawPath.substring(0, rawPath.indexOf("?"));
        String queryStr = rawPath.substring(rawPath.indexOf("?") + 1);

        return new Request(ipResponse, ipRequest, timestamp, method, path, queryStr);
    }

    public static List<Request> parser(String logList) {
        List<Request> requestList = new ArrayList<>();

        try (BufferedReader in = new BufferedReader(new StringReader(logList))) {
            String log;
            while ((log = in.readLine()) != null) {
                requestList.add(parseLog(log));
            }
        } catch (IOException e) {
            LOG.error("Exception: " + e);
        }
        return requestList;
    }
}