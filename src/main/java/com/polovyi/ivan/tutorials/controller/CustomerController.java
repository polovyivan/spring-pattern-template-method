package com.polovyi.ivan.tutorials.controller;

import com.polovyi.ivan.tutorials.dto.CreateReportRequest;
import com.polovyi.ivan.tutorials.dto.CustomerResponse;
import com.polovyi.ivan.tutorials.service.CustomerService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RequestMapping(path = "/v1/customers")
@RestController
public record CustomerController(CustomerService customerService) {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CustomerResponse> getAllCustomersWithFilters() {
        return customerService.getAllCustomersWithFilters();
    }

    @PostMapping(path = "/reports")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void createReport(@Valid @RequestBody CreateReportRequest createReportRequest, HttpServletResponse response)
            throws Exception {
        String reportUrl = customerService.createReport(createReportRequest);
        response.addHeader(HttpHeaders.LOCATION, reportUrl);
    }

}
