package org.artem.novapost.entity.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class WarehouseSchedule {

    @JsonProperty("Monday")
    private String monday;

    @JsonProperty("Tuesday")
    private String tuesday;

    @JsonProperty("Wednesday")
    private String wednesday;

    @JsonProperty("Thursday")
    private String thursday;

    @JsonProperty("Friday")
    private String friday;

    @JsonProperty("Saturday")
    private String saturday;

    @JsonProperty("Sunday")
    private String sunday;
}
