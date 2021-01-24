package com.pravvich.demo.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableCaching
@EnableJpaRepositories(basePackages="com.pravvich.demo")
@EnableTransactionManagement
public class PersistenceConfig {
}