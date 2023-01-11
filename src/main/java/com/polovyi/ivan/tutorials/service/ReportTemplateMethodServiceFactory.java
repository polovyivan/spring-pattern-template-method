package com.polovyi.ivan.tutorials.service;

import com.polovyi.ivan.tutorials.enm.ReportType;
import com.polovyi.ivan.tutorials.exception.UnprocessableEntityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;

@Slf4j
@Service
public record ReportTemplateMethodServiceFactory(Set<ReportTemplateMethodService> reportTemplateMethodServices) {

    public ReportTemplateMethodService findReportTemplateMethodService(ReportType reportType) {
        return reportTemplateMethodServices.stream()
                .filter(service -> service.getReportType().equals(reportType))
                .findFirst()
                .orElseThrow(() -> {
                    log.error("No implementation found for Report type {}", reportType);
                    return new UnprocessableEntityException("No implementation found!");
                });
    }
}
