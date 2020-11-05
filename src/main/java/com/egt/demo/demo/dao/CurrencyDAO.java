package com.egt.demo.demo.dao;

import com.egt.demo.demo.model.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyDAO extends JpaRepository<Currency, Long > {
    Currency findByNameAndCurrencyDate_Id(String name, long id);
}
