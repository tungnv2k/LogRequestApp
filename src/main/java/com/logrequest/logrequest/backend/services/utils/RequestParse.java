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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RequestParse {

    private static final Logger LOG = LoggerFactory.getLogger(RequestParse.class);

    public RequestParse() {
    }

    private static Request parseOne(String log, List<String> vars) {

        List<String> logList = new ArrayList<>();

        Pattern pattern = Pattern.compile("\\[.*?]|\".*?\"|[\\S]+");
        Matcher m = pattern.matcher(log);

        while (m.find()) {
            logList.add(m.group());
        }

        String remoteIp = logList.get(vars.indexOf("$remote_addr"));

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss Z");
        LocalDateTime timestamp = LocalDateTime.parse(
                logList.get(vars.indexOf("[$time_local]"))
                        .replace("[", "")
                        .replace("]", ""),
                dateTimeFormatter);

        String[] request = logList.get(vars.indexOf("\"$request\"")).replaceAll("\"", "").split("\\s");
        String method = request[0];
        String rawPath = request[1];

        if (rawPath.contains("?")) {
            String path = rawPath.substring(0, rawPath.indexOf("?"));
            String queryStr = rawPath.substring(rawPath.indexOf("?") + 1);

            return new Request(remoteIp, timestamp, method, path, queryStr);
        }

        return new Request(remoteIp, timestamp, method, rawPath, "");
    }

    public static List<Request> parser(String logList, List<String> vars) {
        List<Request> requestList = new ArrayList<>();

        try (BufferedReader in = new BufferedReader(new StringReader(logList))) {
            String log;
            while ((log = in.readLine()) != null) {
                try {
                    requestList.add(parseOne(log, vars));
                } catch (Exception ignored) {
                    throw new IllegalArgumentException(log);
                }
            }
        } catch (IOException e) {
            LOG.error("Exception: " + e);
        }
        return requestList;
    }
}