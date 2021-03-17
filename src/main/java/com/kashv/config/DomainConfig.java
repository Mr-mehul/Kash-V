package com.kashv.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EntityScan(basePackages = "com.kashv.domain")
@EnableJpaRepositories(basePackages = "com.kashv.repos")
@EnableTransactionManagement
public class DomainConfig {
}
