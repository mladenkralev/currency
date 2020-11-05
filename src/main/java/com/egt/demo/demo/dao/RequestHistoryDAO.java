package com.egt.demo.demo.dao;

import com.egt.demo.demo.model.RequestHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RequestHistoryDAO extends JpaRepository<RequestHistory, Long > {
    RequestHistory findByUniqueRequestId(String id);
    List<RequestHistory> findAllByServiceName(String serviceName);
}
