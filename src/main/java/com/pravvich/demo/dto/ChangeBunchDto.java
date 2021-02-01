package com.pravvich.demo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChangeBunchDto {

    @JsonProperty("auditGroupId")
    private String auditGroupId;

    @JsonProperty("date")
    private LocalDateTime date;

    @JsonProperty("comment")
    private String comment;

    @JsonProperty("changes")
    private List<String> changes = new ArrayList<>();

    public void addChange(String change) {
        changes.add(change);
    }
}
