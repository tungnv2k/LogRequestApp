package com.logrequest.logrequest.backend.services.utils;

import com.logrequest.logrequest.backend.models.Request;
import com.logrequest.logrequest.backend.repositories.RequestRepository;
import org.apache.commons.lang3.RegExUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ParseRequest {

    private static final Logger LOG = LoggerFactory.getLogger(ParseRequest.class);

    private final RequestRepository requestRepository;

    public ParseRequest(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    private static Request parseLog(String log) {
        log = RegExUtils.removeAll(log, "[\"\\[\\]]");
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

    public void parser(String logList) {
//        List<Request> requestList = new ArrayList<>();
//
//        try (BufferedReader in = new BufferedReader(new StringReader(logList))) {
//            String log;
//            while ((log = in.readLine()) != null) {
//                requestList.add(parseLog(log));
//            }
//        } catch (IOException e) {
//            LOG.error("Exception: " + e);
//        } finally {
//            requestRepository.saveAll(requestList);
//        }
        requestRepository.save(parseLog(logList));
    }
}