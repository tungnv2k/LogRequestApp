package com.logrequest.logrequest.backend.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Arrays;
import java.util.List;

@Document("log_format")
public class LogFormat {

    @Id
    private String id;

    private String name;

    @Indexed(unique = true)
    private String format;

    private List<String> variables;

    public LogFormat(String format) {
        format = format.replaceAll("\\s+", " ");
        format = format.replaceAll("'", "");
        this.format = format;
        this.variables = toVariables(format);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public List<String> getVariables() {
        return variables;
    }

    public void setVariables(List<String> variables) {
        this.variables = variables;
    }

    public static List<String> toVariables(String logFormat) {
        String[] varArr = logFormat.split("\\s+");

        for (int i = 0; i < varArr.length; i++) {
            varArr[i] = varArr[i].contains("=") ? varArr[i].substring(varArr[i].indexOf("=") + 1) : varArr[i];
        }

        return Arrays.asList(varArr);
    }
}
