package com.egt.demo.demo.controller.api.xml.contract;

import com.egt.demo.demo.controller.api.xml.contract.parts.CommandTag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "command")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso(CommandTag.class)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GetCurrentExchangeRateRequest {
    @XmlAttribute(name = "id")
    private long id;

    private CommandTag get;
}
