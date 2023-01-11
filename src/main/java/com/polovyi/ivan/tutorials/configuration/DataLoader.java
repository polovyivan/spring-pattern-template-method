package com.polovyi.ivan.tutorials.configuration;

import com.github.javafaker.Faker;
import com.polovyi.ivan.tutorials.entity.CustomerEntity;
import com.polovyi.ivan.tutorials.repository.CustomerRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

@Component
public record DataLoader(CustomerRepository customerRepository) {

    @Bean
    private InitializingBean sendDatabase() {
        Faker faker = new Faker();
        return () -> {
            customerRepository.saveAll(generateCustomerList(faker));
        };
    }

    private List<CustomerEntity> generateCustomerList(Faker faker) {
        return IntStream.range(0, 100)
                .mapToObj(i -> CustomerEntity.builder().createdAt(
                                LocalDate.now().minus(Period.ofDays((new Random().nextInt(365 * 10)))))
                        .fullName(faker.name().fullName())
                        .phoneNumber(faker.phoneNumber().cellPhone())
                        .address(faker.address().fullAddress())
                        .build())
                .collect(toList());
    }

}
