package com.pravvich.demo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BankAccountDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("number")
    private Long number;

    @JsonProperty("balance")
    private BigDecimal balance;

    @JsonProperty("auditGroupId")
    private UUID auditGroupId;

}
