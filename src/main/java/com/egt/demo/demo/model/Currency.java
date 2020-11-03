package com.egt.demo.demo.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

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
