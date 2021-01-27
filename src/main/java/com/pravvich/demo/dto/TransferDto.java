package com.pravvich.demo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransferDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("value")
    private BigDecimal value;

    @JsonProperty("datetime")
    private Timestamp datetime;

    @JsonProperty("senderId")
    private Long senderId;

    @JsonProperty("recipientId")
    private Long recipientId;
}
