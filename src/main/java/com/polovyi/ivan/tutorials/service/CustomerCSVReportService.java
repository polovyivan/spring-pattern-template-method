package com.polovyi.ivan.tutorials.service;

import com.polovyi.ivan.tutorials.dto.CreateReportRequest;
import com.polovyi.ivan.tutorials.enm.ReportType;
import com.polovyi.ivan.tutorials.entity.CustomerEntity;
import com.polovyi.ivan.tutorials.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class CustomerCSVReportService extends ReportTemplateMethodService<CreateReportRequest, CustomerEntity> {

    private final CustomerRepository customerRepository;

    @Override
    ReportType getReportType() {
        return ReportType.CSV;
    }

    @Override
    String getBucketName() {
        return "reports-in-csv";
    }

    @Override
    String getFileName() {
        return "csv-report-" + UUID.randomUUID() + ".csv";
    }

    @Override
    String getErrorMessage() {
        return "Error occurred while generating csv report...";
    }

    @Override
    List<CustomerEntity> fetchData(CreateReportRequest request) {
        return customerRepository.findAllByCreatedAtBetween(request.getStartDate(), request.getEndDate());
    }

    @Override
    String generateFileContent(List<CustomerEntity> dataFromSource) {
        return dataFromSource.stream()
                .map(entity -> Stream.of(entity.getId(),
                                entity.getId(),
                                entity.getFullName(),
                                entity.getAddress(),
                                entity.getPhoneNumber(),
                                entity.getCreatedAt().toString())
                        .collect(Collectors.joining(";", "", "\r\n")))
                .collect(Collectors.joining());
    }
}
