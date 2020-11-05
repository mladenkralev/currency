package com.egt.demo.demo.model;

import com.egt.demo.demo.util.DateTransformator;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
import java.time.*;
import java.util.List;

@Entity
@ToString(exclude = "rates")
@EqualsAndHashCode(exclude = "rates")
@Getter
public class CurrencyDate {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(columnDefinition = "TIMESTAMP")
    LocalDateTime date;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, mappedBy = "currencyDate")
    List<Currency> rates;

    public CurrencyDate() {
    }

    public void setDate(long longValue) {
        this.date = DateTransformator.transform(longValue);
    }

    public void setRates(List<Currency> rates) {
        this.rates = rates;
    }


}
