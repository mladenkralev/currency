package com.egt.demo.demo.controller.api.xml.contract.parts;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "get")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso(CurrencyTag.class)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommandTag {
    @XmlAttribute(name = "consumer")
    private Long customerId;

    @XmlElement(name = "currency")
    private CurrencyTag currency;
}
