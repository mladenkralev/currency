package com.egt.demo.demo.controller.api.xml.contract;

import com.egt.demo.demo.controller.api.xml.contract.parts.HistoryTag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "command")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso(HistoryTag.class)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GetHistoryExchangeRateRequest {
    @XmlAttribute(name = "id")
    private long id;

    @XmlElement(name = "history")
    private HistoryTag historyTag;
}
