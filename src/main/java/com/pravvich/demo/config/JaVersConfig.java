package com.pravvich.demo.config;

import com.pravvich.demo.model.BankAccount;
import com.pravvich.demo.model.MoneyTransfer;
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
public class JaVersConfig {

    // TODO: 2/1/2021 refactor instanceof to strategy
    @Bean
    public CommitPropertiesProvider commitPropertiesProvider() {
        return new CommitPropertiesProvider() {
            @Override
            public Map<String, String> provideForCommittedObject(Object domainObject) {
                if (domainObject instanceof MoneyTransfer) {
                    final MoneyTransfer moneyTransfer = (MoneyTransfer) domainObject;
                    final Long senderId = moneyTransfer.getSender().getId();
                    final Long recipientId = moneyTransfer.getRecipient().getId();
                    return Map.of(
                            "senderId", senderId.toString(),
                            "recipientId", recipientId.toString(),
                            "auditGroupId", moneyTransfer.getAuditMetadata().getAuditGroupId().toString()
                    );
                }

                if (domainObject instanceof BankAccount) {
                    final BankAccount transfer = (BankAccount) domainObject;
                    return Map.of("auditGroupId", transfer.getAuditMetadata().getAuditGroupId().toString());
                }
                return Collections.emptyMap();
            }
        };
    }
}
