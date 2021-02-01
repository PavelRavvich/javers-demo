package com.pravvich.demo.model;

import lombok.*;
import org.javers.core.metamodel.annotation.DiffIgnore;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "money_transfer")
@ToString(exclude = {
        "sender",
        "recipient"
})
public class MoneyTransfer {

    @Transient
    private AuditMetadata auditMetadata = new AuditMetadata();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "volume")
    private BigDecimal volume;

    @Column(name = "datetime")
    private Timestamp datetime;

    @DiffIgnore
    @ManyToOne
    @JoinColumn(name = "sender_id")
    private BankAccount sender;

    @DiffIgnore
    @ManyToOne
    @JoinColumn(name = "recipient_id")
    private BankAccount recipient;

}
