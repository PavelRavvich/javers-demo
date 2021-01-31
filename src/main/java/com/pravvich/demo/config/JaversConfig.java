package com.pravvich.demo.config;

import com.pravvich.demo.model.Transfer;
import org.javers.spring.auditable.CommitPropertiesProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Collections;
import java.util.Map;

@Configuration
@ComponentScan(basePackages = "org.javers.spring.repository")
@EnableTransactionManagement
@EnableAspectJAutoProxy
@EnableJpaRepositories({"org.javers.spring.repository"})
public class JaversConfig {

    @Bean
    public CommitPropertiesProvider commitPropertiesProvider() {
        return new CommitPropertiesProvider() {
            @Override
            public Map<String, String> provideForCommittedObject(Object domainObject) {
                if (domainObject instanceof Transfer) {
                    final Transfer transfer = (Transfer) domainObject;
                    final Long senderId = transfer.getSender().getId();
                    final Long recipientId = transfer.getRecipient().getId();
                    return Map.of(
                            "senderId", senderId.toString(),
                            "recipientId", recipientId.toString()
                    );
                }
                return Collections.emptyMap();
            }
        };
    }
}
