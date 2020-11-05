package com.egt.demo.demo.controller.api.json.contract;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ClientRequest {
    @JsonProperty("requestId")
    String uniqueRequestId;

    @JsonProperty("client")
    Long clientId;

    String timestamp;
    String currency;

    int period;
}