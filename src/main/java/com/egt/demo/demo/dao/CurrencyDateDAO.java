package com.egt.demo.demo.dao;

import com.egt.demo.demo.model.CurrencyDate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CurrencyDateDAO extends JpaRepository<CurrencyDate, Long > {
    List<CurrencyDate> findAllByOrderByDateAsc();
}
