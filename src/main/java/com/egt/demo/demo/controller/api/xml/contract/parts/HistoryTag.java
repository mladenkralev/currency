package com.egt.demo.demo.controller.api.xml.contract.parts;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "history")
@XmlAccessorType(XmlAccessType.FIELD)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class HistoryTag {
    @XmlAttribute(name = "consumer")
    private Long customerId;

    @XmlAttribute(name = "currency")
    private String currency;

    @XmlAttribute(name = "period")
    private int period;
}
