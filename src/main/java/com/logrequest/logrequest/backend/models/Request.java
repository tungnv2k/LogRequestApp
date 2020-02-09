package com.logrequest.logrequest.backend.models;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document("request")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Request {

    @Id
    private String id;

    private String ipUser;

    private String ipRemote;

    private LocalDateTime timestamp;

    private String method;

    private String path;

    private String queryStr;

    public Request() {
    }

    public Request(String ipRemote, LocalDateTime timestamp,
                   String method, String path, String queryStr) {
        this.ipRemote = ipRemote;
        this.timestamp = timestamp;
        this.method = method;
        this.path = path;
        this.queryStr = queryStr;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getIpRemote() {
        return ipRemote;
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getQueryStr() {
        return queryStr;
    }

    public String getRequest(String host) {
        return host + getPath() + "?" + getQueryStr();
    }

    public String getCURL(String host) {
        return "curl -X " + getMethod() + " '" + getRequest(host) + "'";
    }

    public List<Param> getParams() {
        List<Param> list = new ArrayList<>();
        String[] params = getQueryStr().split("[?=&]");
        for (int i = 0; i < params.length; i += 2) {
            list.add(new Param(params[i], URLDecoder.decode(params[i + 1], StandardCharsets.UTF_8)));
        }
        return list;
    }
}
