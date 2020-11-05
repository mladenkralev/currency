package com.egt.demo.demo.controller.api.xml.contract;

import com.egt.demo.demo.controller.api.xml.contract.parts.CommandTag;
import lombok.*;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "command")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso(CommandTag.class)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class GetCurrentExchangeRateRequest {
    @XmlAttribute(name = "id")
    private long id;

    @XmlElement(name = "get")
    private CommandTag command;
}
