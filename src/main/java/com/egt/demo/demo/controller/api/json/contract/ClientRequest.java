package com.egt.demo.demo.controller.api.json.contract;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ClientRequest {
    @JsonProperty("requestId")
    String uniqueRequestId;

    @JsonProperty("client")
    Long clientId;

    String timestamp;
    String currency;

    int period;
}