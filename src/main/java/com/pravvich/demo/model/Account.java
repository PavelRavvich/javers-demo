package com.pravvich.demo.model;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "account")
@ToString(exclude = {"company"})
@EqualsAndHashCode(exclude = {"company"})
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "number")
    private Long number;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

}
