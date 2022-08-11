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
public class CustomerHTMLReportService extends ReportTemplateMethodService<CreateReportRequest, CustomerEntity> {

    private final CustomerRepository customerRepository;

    @Override
    ReportType getReportType() {
        return ReportType.HTML;
    }

    @Override
    String getBucketName() {
        return "reports-in-html";
    }

    @Override
    String getFileName() {
        return "html-report-" + UUID.randomUUID() + ".html";
    }

    @Override
    String getErrorMessage() {
        return "Error occurred while generating html report...";
    }

    @Override
    List<CustomerEntity> fetchData(CreateReportRequest request) {
        return customerRepository.findAllByCreatedAtBetween(request.getStartDate(), request.getEndDate());
    }

    @Override
    String generateFileContent(List<CustomerEntity> dataFromSource) {

        String tableContent = dataFromSource.stream()
                .map(entity -> Stream.of(entity.getId(),
                                entity.getFullName(),
                                entity.getAddress(),
                                entity.getPhoneNumber(),
                                entity.getCreatedAt().toString())
                        .collect(Collectors.joining("</td><td>", "<tr><td>", "</td></tr>")))
                .collect(Collectors.joining());

        return " <!DOCTYPE html>\n"
                + "                        <html>\n"
                + "                        <style>\n"
                + "                            table, th, td {\n"
                + "                                border:1px solid black;\n"
                + "                            }\n"
                + "                        </style>\n"
                + "                        <body>        \n"
                + "                        <h2>Customers</h2>\n"
                + "                        <table style=\"width:100%\">\n"
                + "                          <tr>\n"
                + "                            <th>Id</th>\n"
                + "                            <th>Full Name</th>\n"
                + "                            <th>Address</th>\n"
                + "                            <th>Phone number</th>\n"
                + "                            <th>Created at</th>\n"
                + "                          </tr>\n"
                + "                         " + tableContent + "\n"
                + "                        </table>\n"
                + "                        </body>\n"
                + "                        </html>";
    }
}
