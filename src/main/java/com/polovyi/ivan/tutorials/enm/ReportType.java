package com.polovyi.ivan.tutorials.enm;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReportType {
    JSON("application/json"),
    CSV("text/plain"),
    HTML("text/html");

    private String contentType;
}
