package com.egt.demo.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

/**
 * Represents currency with the exchange rate based on base currency - EUR
 */
@Entity
@Data
public class Currency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Setter
    String name;
    @Setter
    Double exchangeRate;

    @ManyToOne
    @EqualsAndHashCode.Exclude @ToString.Exclude
    @JoinColumn(name="currencyDate_id", nullable=false)
    @JsonIgnore
    CurrencyDate currencyDate;

    public Currency() {
    }

    public Currency(String name, Double exchangeRate, CurrencyDate currencyDate) {
        this.name = name;
        this.exchangeRate = exchangeRate;
        this.currencyDate = currencyDate;
    }

    @Override
    public String toString() {
        return "Currency{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", exchangeRate=" + exchangeRate +
                ", currencyDate=" + currencyDate +
                '}';
    }
}
