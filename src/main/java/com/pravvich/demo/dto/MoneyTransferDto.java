package com.pravvich.demo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MoneyTransferDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("volume")
    private BigDecimal volume;

    @JsonProperty("datetime")
    private Timestamp datetime;

    @JsonProperty("senderId")
    private Long senderId;

    @JsonProperty("recipientId")
    private Long recipientId;

    @JsonProperty("auditGroupId")
    private UUID auditGroupId;

}
