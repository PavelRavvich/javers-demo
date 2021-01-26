package com.pravvich.demo.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "account")
@ToString(exclude = {"company"})
@EqualsAndHashCode(exclude = {
        "company",
        "senders",
        "recipients",
})
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "number")
    private Long number;

    @Column(name = "balance")
    private BigDecimal balance;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @OneToMany(mappedBy = "sender", fetch = FetchType.LAZY)
    private List<Transfer> senders;

    @OneToMany(mappedBy = "recipient", fetch = FetchType.LAZY)
    private List<Transfer> recipients;
}
