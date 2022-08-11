package com.polovyi.ivan.tutorials.service;

import com.polovyi.ivan.tutorials.dto.CreateReportRequest;
import com.polovyi.ivan.tutorials.dto.CustomerResponse;
import com.polovyi.ivan.tutorials.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public record CustomerService(CustomerRepository customerRepository,
                              ReportTemplateMethodServiceFactory reportTemplateMethodServiceFactory) {

    public List<CustomerResponse> getAllCustomersWithFilters() {
        log.info("Getting all customers with filters fullName {}...");
        return customerRepository.findAll()
                .stream()
                .map(CustomerResponse::valueOf)
                .collect(Collectors.toList());
    }

    public String createReport(CreateReportRequest createReportRequest) throws Exception {
        return reportTemplateMethodServiceFactory.findReportTemplateMethodService(createReportRequest.getReportType())
                .generateReport(createReportRequest);
    }

}
