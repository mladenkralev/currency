package com.egt.demo.demo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.text.ParseException;
import java.time.LocalDate;
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
    LocalDate date;


    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, mappedBy = "currencyDate")
    List<Currency> rates;

    public CurrencyDate() {
    }

    public void setDate(String dateAsString) throws ParseException {
        date = LocalDate.parse(dateAsString);
    }

    public void setRates(List<Currency> rates) {
        this.rates = rates;
    }


}
