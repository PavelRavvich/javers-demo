package com.pravvich.demo.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transfer")
@ToString(exclude = {
        "sender",
        "recipient"
})
public class Transfer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "value")
    private BigDecimal value;

    @Column(name = "datetime")
    private Timestamp datetime;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private Account sender;

    @ManyToOne
    @JoinColumn(name = "recipient_id")
    private Account recipient;

}
