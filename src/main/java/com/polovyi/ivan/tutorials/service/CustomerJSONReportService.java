package com.polovyi.ivan.tutorials.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.polovyi.ivan.tutorials.dto.CreateReportRequest;
import com.polovyi.ivan.tutorials.enm.ReportType;
import com.polovyi.ivan.tutorials.entity.CustomerEntity;
import com.polovyi.ivan.tutorials.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerJSONReportService extends ReportTemplateMethodService<CreateReportRequest, CustomerEntity> {

    private final CustomerRepository customerRepository;
    private static final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Override
    ReportType getReportType() {
        return ReportType.JSON;
    }

    @Override
    String getBucketName() {
        return "reports-in-json";
    }

    @Override
    String getFileName() {
        return "json-report-" + UUID.randomUUID() + ".json";
    }

    @Override
    String getErrorMessage() {
        return "Error occurred while generating json report...";
    }

    @Override
    List<CustomerEntity> fetchData(CreateReportRequest request) {
        return customerRepository.findAllByCreatedAtBetween(request.getStartDate(), request.getEndDate());
    }

    @Override
    String generateFileContent(List<CustomerEntity> dataFromSource) {
        try {
            return mapper.writeValueAsString(dataFromSource);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
