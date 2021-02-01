package com.pravvich.demo.model;

import lombok.*;
import org.javers.core.metamodel.annotation.DiffIgnore;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "bank_account")
@ToString(exclude = {"company"})
@EqualsAndHashCode(exclude = {
        "company",
        "senders",
})
public class BankAccount {

    @Transient
    private AuditMetadata auditMetadata = new AuditMetadata();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "number")
    private Long number;

    @Column(name = "balance")
    private BigDecimal balance;

    @DiffIgnore
    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @DiffIgnore
    @OneToMany(mappedBy = "sender", fetch = FetchType.LAZY)
    private List<MoneyTransfer> senders;

    @DiffIgnore
    @OneToMany(mappedBy = "recipient", fetch = FetchType.LAZY)
    private List<MoneyTransfer> recipients;

}
